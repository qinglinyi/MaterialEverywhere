package pw.material.module.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import pw.material.MaterialXStream
import pw.material.internal.MaterialProjectState

public class MetaTask extends DefaultTask {
    @Input
    public MaterialProjectState materialState

    @OutputFile
    public File outputDir

    @TaskAction
    public void makeMeta() {
        outputDir.deleteDir()
        outputDir.mkdirs()

        def materialInf = new File(outputDir, 'MATERIAL-INF')
        materialInf.mkdirs()
        def manifestFile = new File(materialInf, 'module.xml')
        def realState = materialState.moduleManifest.copy(materialState)
        realState.implicitDependencies.addAll findImplicitDependencies(project)
        manifestFile.bytes = MaterialXStream.toXML(realState)
    }

    def static findImplicitDependencies(Project project) {
        return project.configurations.getByName('compile').dependencies.collect { "${it.group}:${it.name}:${it.version}" }
    }
}
