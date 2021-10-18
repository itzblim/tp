package t13.modbook.logic.commands.delete;

import static java.util.Objects.requireNonNull;

import java.util.List;

import t13.modbook.commons.core.Messages;
import t13.modbook.commons.core.index.Index;
import t13.modbook.logic.commands.CommandResult;
import t13.modbook.logic.commands.exceptions.CommandException;
import t13.modbook.model.Model;
import t13.modbook.model.module.Module;

public class DeleteModCommand extends DeleteCommand {
    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a module from the Mod book. "
            + "\nParameters: Index"
            + "\nExample: " + COMMAND_WORD + " mod 1";
    private final Index targetIndex;

    /**
     * Creates an DeleteModCommand to delete the module at specified {@code Index}
     */
    public DeleteModCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }
        Module moduleToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteModule(moduleToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeleteModCommand)) {
            return false;
        }
        return targetIndex.equals(((DeleteModCommand) other).targetIndex);
    }
}
