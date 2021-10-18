package t13.modbook.testutil;

import static t13.modbook.logic.parser.CliSyntax.PREFIX_CODE;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_NAME;

import t13.modbook.model.module.Module;

/**
 * A utility class for Person.
 */
public class ModuleUtil {

    /**
     * Returns the part of command string for the given {@code module}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CODE + module.getCode().toString() + " ");
        if (module.getName().isPresent()) {
            sb.append(PREFIX_NAME + module.getName().toString() + " ");
        }
        return sb.toString();
    }

}
