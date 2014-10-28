package pw.material

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.reflect.Instantiator
import pw.material.internal.MaterialProjectState

import javax.inject.Inject

public class MaterialBasePlugin implements Plugin<Project> {
    private static final MaterialTools TOOLS = new MaterialTools()

    def MaterialExtension extension
    def MaterialTools tools = TOOLS
    def MaterialProjectState state

    private final Instantiator instantiator

    @Inject
    public MaterialBasePlugin(Instantiator instantiator) {
        this.instantiator = instantiator
    }

    @Override
    void apply(Project project) {
        state = new MaterialProjectState(project)

        extension = project.extensions.create 'material', MaterialExtension
        addExtension 'tools', tools
        createExtension 'target', MaterialTarget
    }

    public <T> T addExtension(String name, T object) {
        extension.add(name, object)
        return object
    }

    public <T> T createExtension(String name, Class<T> clazz, Object... args) {
        return addExtension(name, instantiator.newInstance(clazz, args))
    }
}
