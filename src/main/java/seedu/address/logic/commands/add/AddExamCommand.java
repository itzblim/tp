package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exam.Exam;

public class AddExamCommand extends AddCommand {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an exam to the Mod book. "
            + "\nParameters: "
            + PREFIX_CODE + "MOD_CODE "
            + PREFIX_NAME + "EXAM_NAME "
            + PREFIX_DAY + "DAY "
            + PREFIX_START + "START_TIME "
            + PREFIX_END + "END_TIME "
            + PREFIX_LINK + "LINK "
            + PREFIX_VENUE + "VENUE "
            + "\nExample: " + COMMAND_WORD + " exam "
            + PREFIX_CODE + "CS2103 "
            + PREFIX_NAME + "Final "
            + PREFIX_DAY + "02/02/1999 "
            + PREFIX_START + "10:00 "
            + PREFIX_END + "11:00 "
            + PREFIX_LINK + "https://www.youtube.com/watch?v=8mL3L9hN2l4 "
            + PREFIX_VENUE + "Field";

    public static final String MESSAGE_SUCCESS = "New exam added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXAM = "This exam already exists in the mod book";

    private final Exam toAdd;
    private final ModuleCode modCode;

    /**
     * Creates an AddCommand to add the specified {@code Exam}
     */
    public AddExamCommand(ModuleCode modCode, Exam exam) {
        requireNonNull(exam);
        this.toAdd = exam;
        this.modCode = modCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Module module = model.getModule(modCode);

        if (model.moduleHasExam(module, toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXAM);
        }
        model.addExamToModule(module, toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddExamCommand // instanceof handles nulls
                && toAdd.equals(((AddExamCommand) other).toAdd));
    }
}

