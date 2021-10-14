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
import seedu.address.model.module.lesson.Lesson;

public class AddLessonCommand extends AddCommand {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the Mod book. "
            + "\nParameters: "
            + PREFIX_CODE + "MOD_CODE "
            + PREFIX_NAME + "LESSON_NAME "
            + PREFIX_DAY + "DAY "
            + PREFIX_START + "START_TIME "
            + PREFIX_END + "END_TIME "
            + PREFIX_LINK + "LINK "
            + PREFIX_VENUE + "VENUE "
            + "\nExample: " + COMMAND_WORD + " lesson "
            + PREFIX_CODE + "CS2103 "
            + PREFIX_NAME + "Tutorial "
            + PREFIX_DAY + "Monday "
            + PREFIX_START + "10:00 "
            + PREFIX_END + "11:00 "
            + PREFIX_LINK + "https://www.youtube.com/watch?v=8mL3L9hN2l4 "
            + PREFIX_VENUE + "COM1 ";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the mod book";

    private final Lesson toAdd;
    private final ModuleCode modCode;

    /**
     * Creates an AddCommand to add the specified {@code Lesson}
     */
    public AddLessonCommand(ModuleCode modCode, Lesson lesson) {
        requireNonNull(lesson);
        this.toAdd = lesson;
        this.modCode = modCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Module module = model.getModule(modCode);

        if (model.moduleHasLesson(module, toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }
        model.addLessonToModule(module, toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLessonCommand // instanceof handles nulls
                && toAdd.equals(((AddLessonCommand) other).toAdd));
    }
}
