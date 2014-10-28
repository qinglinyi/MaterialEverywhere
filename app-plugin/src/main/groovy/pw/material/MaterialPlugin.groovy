package pw.material

import com.android.build.gradle.BasePlugin
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

public class MaterialPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        if (project.plugins.find { BasePlugin.isAssignableFrom it.class }) {
            throw new GradleException("Material plugin should be applied before android plugin")
        }

        project.plugins.apply MaterialBasePlugin
    }
}
