package t13.modbook.logic.commands.list;

import t13.modbook.logic.commands.Command;

/**
 * Lists all modules / lessons / exams in the mod book to the user.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all modules / lessons / exams.\n"
            + "Parameters: [mod / lesson / exam]\n"
            + "Example: " + COMMAND_WORD + " mod";

}
