package teethirteen.modbook.logic.commands.delete;

import teethirteen.modbook.logic.commands.Command;

public abstract class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a mod/lesson/exam from the mod book. "
            + "Please specify what you would like to delete\n"
            + "Example:\n" + COMMAND_WORD + " mod <parameters>\n"
            + COMMAND_WORD + " lesson <parameters>\n"
            + COMMAND_WORD + " exam <parameters>\n";
}
