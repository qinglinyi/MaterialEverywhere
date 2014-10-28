package pw.material.module

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.android.sdklib.SdkManager
import com.android.utils.StdLogger
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import pw.material.MaterialBasePlugin
import pw.material.MaterialTarget
import pw.material.module.tasks.MetaTask

public class MaterialModulePlugin implements Plugin<Project>, Action<Project> {
    public MaterialModuleExtension moduleExtension
    private MetaTask metaTask
    private MaterialBasePlugin basePlugin

    @Override
    void apply(Project project) {
        basePlugin = project.plugins.apply MaterialBasePlugin

        project.afterEvaluate this

        def libraryPlugin = project.plugins.apply LibraryPlugin

        moduleExtension = basePlugin.createExtension 'module', MaterialModuleExtension, project

        moduleExtension.manifest.target = basePlugin.extension.target as MaterialTarget

        basePlugin.state.androidExtension = libraryPlugin.extension as LibraryExtension
        basePlugin.state.sdkManager = SdkManager.createManager(libraryPlugin.sdkFolder.absolutePath, new StdLogger(StdLogger.Level.WARNING))
        basePlugin.state.moduleManifest = moduleExtension.manifest

        //metaTask = project.tasks.create 'materialModule', MetaTask
        //metaTask.materialState = basePlugin.state
        //metaTask.outputDir = new File(project.buildDir, 'material/module')
    }

    @Override
    void execute(Project project) {
        (basePlugin.extension.target as MaterialTarget).apply basePlugin.state

        def manifest = basePlugin.state.androidExtension.sourceSets['main'].manifest
        if (!manifest.srcFile || !manifest.srcFile.exists()) {
            manifest.srcFile DefaultAndroidManifest.obtainDefaultManifest(project, moduleExtension.packageName)
        }
    }
}
