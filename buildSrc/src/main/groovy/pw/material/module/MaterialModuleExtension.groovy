package pw.material.module

import org.gradle.api.Project
import pw.material.internal.AutoConfigureExtension

public class MaterialModuleExtension extends AutoConfigureExtension {
    def String packageName
    def final ModuleManifest manifest = new ModuleManifest()
    def final Project project

    public MaterialModuleExtension(Project project) {
        this.project = project
    }

    String getPackageName() { packageName ?: "${project.group}.${project.name}" }

    def depends(Project... projects) {
        projects.each { dependency ->
            project.dependencies.add('compile', dependency)
        }
    }

    def depends(String... dependencies) {
        dependencies.each { dependency ->
            if (dependency.startsWith("#")) {
                project.dependencies.add 'compile', project.findProject("${project?.parent?.path ?: ''}:${dependency.substring(1)}")
            } else {
                project.dependencies.add('compile', dependency)
            }
        }
    }

    def String getFullName() {
        return "${project.group}:${project.name}:${project.version}"
    }
}
