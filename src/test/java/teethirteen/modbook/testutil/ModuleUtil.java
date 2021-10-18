package teethirteen.modbook.testutil;

import teethirteen.modbook.logic.commands.add.AddCommand;
import teethirteen.modbook.model.module.Module;
import teethirteen.modbook.logic.parser.CliSyntax;

/**
 * A utility class for Person.
 */
public class ModuleUtil {

    /**
     * Returns an add command string for adding the {@code module}.
     */
    public static String getAddCommand(Module module) {
        return AddCommand.COMMAND_WORD + " mod " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code module}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_CODE + module.getCode().toString() + " ");
        if (module.getName().isPresent()) {
            sb.append(CliSyntax.PREFIX_NAME + module.getName().toString() + " ");
        }
        return sb.toString();
    }

}
