package pw.material.internal

import org.gradle.api.Action
import org.gradle.api.internal.ClosureBackedAction

public abstract class AutoConfigureExtension {
    def methodMissing(String name, argsRaw) {
        Object o = this."${name}"
        Object[] args = argsRaw as Object[]
        if (o != null && args.length == 1) {
            Action configureAction
            if (args[0] instanceof Action) {
                configureAction = args[0] as Action
            } else if (args[0] instanceof Closure) {
                configureAction = new ClosureBackedAction(args[0] as Closure)
            } else {
                this."${name}" = args[0]
                return null
            }
            if (configureAction != null) {
                configureAction.execute(o)
                return o
            }
        }
        throw new MissingMethodException(name, getClass(), args)
    }
}
