package pw.material.module

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamImplicit
import pw.material.MaterialTarget
import pw.material.internal.ICopy
import pw.material.internal.MaterialProjectState

@XStreamAlias('module-manifest')
public class ModuleManifest implements Serializable, ICopy<ModuleManifest> {
    def MaterialTarget target
    @XStreamImplicit(keyFieldName = 'dependency')
    def List<String> dependencies = new ArrayList<>()
    @XStreamImplicit(keyFieldName = 'implicit')
    def List<String> implicitDependencies = new ArrayList<>()

    @Override
    ModuleManifest copy(MaterialProjectState state) {
        new ModuleManifest(target: target.copy(state),
                dependencies: new ArrayList<>(dependencies),
                implicitDependencies: new ArrayList<String>(implicitDependencies))
    }
}
