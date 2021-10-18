package teethirteen.modbook.logic.commands.add;

import static java.util.Objects.requireNonNull;

import teethirteen.modbook.logic.commands.CommandResult;
import teethirteen.modbook.logic.commands.exceptions.CommandException;
import teethirteen.modbook.logic.parser.CliSyntax;
import teethirteen.modbook.model.Model;
import teethirteen.modbook.model.module.Module;
import teethirteen.modbook.model.module.ModuleCode;
import teethirteen.modbook.model.module.exam.Exam;

public class AddExamCommand extends AddCommand {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an exam to the Mod book. "
            + "\nParameters: "
            + CliSyntax.PREFIX_CODE + "MOD_CODE "
            + CliSyntax.PREFIX_NAME + "EXAM_NAME "
            + CliSyntax.PREFIX_DAY + "DAY "
            + CliSyntax.PREFIX_START + "START_TIME "
            + CliSyntax.PREFIX_END + "END_TIME "
            + CliSyntax.PREFIX_LINK + "LINK "
            + CliSyntax.PREFIX_VENUE + "VENUE "
            + "\nExample: " + COMMAND_WORD + " exam "
            + CliSyntax.PREFIX_CODE + "CS2103 "
            + CliSyntax.PREFIX_NAME + "Final "
            + CliSyntax.PREFIX_DAY + "02/02/1999 "
            + CliSyntax.PREFIX_START + "10:00 "
            + CliSyntax.PREFIX_END + "11:00 "
            + CliSyntax.PREFIX_LINK + "https://www.youtube.com/watch?v=8mL3L9hN2l4 "
            + CliSyntax.PREFIX_VENUE + "Field";

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

