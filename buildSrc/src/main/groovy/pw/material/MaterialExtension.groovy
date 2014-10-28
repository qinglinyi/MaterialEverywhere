package pw.material

import org.gradle.api.Action
import org.gradle.api.internal.ClosureBackedAction
import org.gradle.api.internal.plugins.ExtensionsStorage

public class MaterialExtension extends ExtensionsStorage {
    def methodMissing(String name, argsRaw) {
        Object o = findByName(name)
        Object[] args = argsRaw as Object[]
        if (o != null && args.length == 1) {
            Action configureAction
            if (args[0] instanceof Action) {
                configureAction = args[0] as Action
            } else if (args[0] instanceof Closure) {
                configureAction = new ClosureBackedAction(args[0] as Closure)
            }
            if (configureAction != null) {
                configureAction.execute(o)
                return o
            }
        }
        throw new MissingMethodException(name, MaterialExtension, args)
    }

    def propertyMissing(String name) {
        Object o = findByName(name)
        if (o == null) {
            throw new MissingPropertyException(name, MaterialExtension)
        }
        return o
    }
}
