package t13.modbook.logic.commands.add;

import static java.util.Objects.requireNonNull;

import t13.modbook.logic.commands.CommandResult;
import t13.modbook.logic.commands.exceptions.CommandException;
import t13.modbook.model.Model;
import t13.modbook.model.module.Module;
import t13.modbook.logic.parser.CliSyntax;

public class AddModCommand extends AddCommand {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to the Mod book. "
            + "\nParameters: "
            + CliSyntax.PREFIX_CODE + "CODE "
            + CliSyntax.PREFIX_NAME + "NAME "
            + "\nExample: " + COMMAND_WORD + " mod "
            + CliSyntax.PREFIX_CODE + "CS2103 "
            + CliSyntax.PREFIX_NAME + "Software Engineering ";

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the mod book";

    private final Module toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Mpdule}
     */
    public AddModCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }
        model.addModule(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModCommand // instanceof handles nulls
                && toAdd.equals(((AddModCommand) other).toAdd));
    }
}
