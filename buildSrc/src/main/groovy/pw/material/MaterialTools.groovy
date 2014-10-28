package pw.material

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.tasks.bundling.AbstractArchiveTask
import pw.material.internal.ProjectArtifactDependency

public class MaterialTools {
    public static MaterialTools from(Project project) {
        return project.plugins.apply(MaterialBasePlugin).tools
    }

    public Dependency asDependency(AbstractArchiveTask task) {
        return new ProjectArtifactDependency(task)
    }
}
