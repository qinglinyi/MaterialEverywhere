package pw.material.internal

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.SelfResolvingDependency
import org.gradle.api.internal.artifacts.dependencies.AbstractDependency
import org.gradle.api.internal.tasks.DefaultTaskDependency
import org.gradle.api.internal.tasks.TaskResolver
import org.gradle.api.tasks.TaskDependency
import org.gradle.api.tasks.bundling.AbstractArchiveTask

public class ProjectArtifactDependency extends AbstractDependency implements SelfResolvingDependency {
    def String group, name, version

    private final Project project
    private final AbstractArchiveTask task

    public ProjectArtifactDependency(AbstractArchiveTask task) {
        this.task = task
        this.project = task.project
    }

    @Override
    Set<File> resolve() { return resolve(true) }

    @Override
    Set<File> resolve(boolean transitive) { return [task.archivePath] }

    @Override
    TaskDependency getBuildDependencies() {
        TaskDependency dependency = new DefaultTaskDependency(project.tasks as TaskResolver)
        dependency.add(task)
        return dependency
    }

    @Override
    String getGroup() { group ?: project.group }

    @Override
    String getName() { name ?: project.name }

    @Override
    String getVersion() { version ?: project.version }

    @Override
    boolean contentEquals(Dependency dependency) {
        if (!dependency || !(dependency instanceof ProjectArtifactDependency)) {
            return false;
        }
        return dependency.task.is(task)
    }

    @Override
    ProjectArtifactDependency copy() {
        ProjectArtifactDependency dependency = new ProjectArtifactDependency(task);
        dependency.group = group
        dependency.name = name
        dependency.name = name
        return dependency
    }
}
