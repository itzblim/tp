package t13.modbook.logic.commands;

import static java.util.Objects.requireNonNull;

import t13.modbook.model.Model;
import t13.modbook.model.module.predicates.HasModuleCodePredicate;
import t13.modbook.commons.core.Messages;

/**
 * Lists all lessons and exams of a given Module.
 */
public class DetailCommand extends Command {

    public static final String COMMAND_WORD = "detail";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all the lessons and exams of a given "
            + "module and displays them with index numbers.\n"
            + "Parameters: c/MODULE_CODE\n"
            + "Example: " + COMMAND_WORD + " c/CS2103T";

    private final HasModuleCodePredicate predicate;

    public DetailCommand(HasModuleCodePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredModuleList(predicate);
        String code = predicate.getCode().toString();

        if (model.getFilteredModuleList().isEmpty()) {
            model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
            return new CommandResult(String.format(Messages.MESSAGE_MODULE_NOT_FOUND, code), false, GuiState.SUMMARY);
        }

        return new CommandResult(String.format(Messages.MESSAGE_MODULE_DETAILS_LISTED, code), false, GuiState.DETAILS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetailCommand // instanceof handles nulls
                && predicate.equals(((DetailCommand) other).predicate)); // state check
    }
}
