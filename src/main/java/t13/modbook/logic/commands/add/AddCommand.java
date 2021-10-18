package t13.modbook.logic.commands.add;

import t13.modbook.logic.commands.Command;

/**
 * Adds a module, lesson or exam to the mod book.
 */
public abstract class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a mod/lesson/exam to the mod book. "
            + "Please specify what you would like to add\n"
            + "Example:\n" + COMMAND_WORD + " mod <parameters>\n"
            + COMMAND_WORD + " lesson <parameters>\n"
            + COMMAND_WORD + " exam <parameters>\n";
}
