package t13.modbook.logic.commands.delete;

import static java.util.Objects.requireNonNull;
import static t13.modbook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import t13.modbook.commons.core.Messages;
import t13.modbook.commons.core.index.Index;
import t13.modbook.logic.commands.CommandResult;
import t13.modbook.logic.commands.exceptions.CommandException;
import t13.modbook.model.Model;
import t13.modbook.model.module.Module;
import t13.modbook.model.module.ModuleCode;
import t13.modbook.model.module.lesson.Lesson;
import t13.modbook.commons.util.CollectionUtil;
import t13.modbook.logic.parser.CliSyntax;

public class DeleteLessonCommand extends DeleteCommand {
    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson %s from Module %s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a Lesson from a "
            + "specified Module from the Mod book."
            + "\nParameters: Index "
            + CliSyntax.PREFIX_CODE + "MOD_CODE"
            + "\nExample: " + COMMAND_WORD + " lesson 1 "
            + CliSyntax.PREFIX_CODE + "CS1231S";
    private final Index targetIndex;
    private final ModuleCode targetModuleCode;

    /**
     * Creates an DeleteLessonCommand to delete the Lesson at specified {@code Index} of the specified {@code Module}.
     */
    public DeleteLessonCommand(Index targetIndex, ModuleCode targetModuleCode) {
        CollectionUtil.requireAllNonNull(targetIndex, targetModuleCode);
        this.targetIndex = targetIndex;
        this.targetModuleCode = targetModuleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Module module = model.getModule(targetModuleCode);
        List<Lesson> lessons = module.getLessons();
        if (targetIndex.getZeroBased() >= lessons.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }
        Lesson lessonToDelete = lessons.get(targetIndex.getZeroBased());
        model.deleteLesson(module, lessonToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS,
                lessonToDelete.getName(), targetModuleCode));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeleteLessonCommand)) {
            return false;
        }
        return targetIndex.equals(((DeleteLessonCommand) other).targetIndex)
                && targetModuleCode.equals(((DeleteLessonCommand) other).targetModuleCode);
    }
}
