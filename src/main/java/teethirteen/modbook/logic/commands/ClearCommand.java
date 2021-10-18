package teethirteen.modbook.logic.commands;

import static java.util.Objects.requireNonNull;

import teethirteen.modbook.model.ModBook;
import teethirteen.modbook.model.Model;

/**
 * Clears the ModBook.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "ModBook has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setModBook(new ModBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
