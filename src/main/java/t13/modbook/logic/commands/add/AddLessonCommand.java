package t13.modbook.logic.commands.add;

import static java.util.Objects.requireNonNull;

import t13.modbook.logic.commands.CommandResult;
import t13.modbook.logic.commands.exceptions.CommandException;
import t13.modbook.model.Model;
import t13.modbook.model.module.Module;
import t13.modbook.model.module.ModuleCode;
import t13.modbook.model.module.lesson.Lesson;
import t13.modbook.logic.parser.CliSyntax;

public class AddLessonCommand extends AddCommand {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the Mod book. "
            + "\nParameters: "
            + CliSyntax.PREFIX_CODE + "MOD_CODE "
            + CliSyntax.PREFIX_NAME + "LESSON_NAME "
            + CliSyntax.PREFIX_DAY + "DAY "
            + CliSyntax.PREFIX_START + "START_TIME "
            + CliSyntax.PREFIX_END + "END_TIME "
            + CliSyntax.PREFIX_LINK + "LINK "
            + CliSyntax.PREFIX_VENUE + "VENUE "
            + "\nExample: " + COMMAND_WORD + " lesson "
            + CliSyntax.PREFIX_CODE + "CS2103 "
            + CliSyntax.PREFIX_NAME + "Tutorial "
            + CliSyntax.PREFIX_DAY + "Monday "
            + CliSyntax.PREFIX_START + "10:00 "
            + CliSyntax.PREFIX_END + "11:00 "
            + CliSyntax.PREFIX_LINK + "https://www.youtube.com/watch?v=8mL3L9hN2l4 "
            + CliSyntax.PREFIX_VENUE + "COM1 ";

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
