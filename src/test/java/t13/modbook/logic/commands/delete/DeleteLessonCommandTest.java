package t13.modbook.logic.commands.delete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static t13.modbook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import t13.modbook.commons.core.Messages;
import t13.modbook.commons.core.index.Index;
import t13.modbook.model.Model;
import t13.modbook.model.ModelManager;
import t13.modbook.model.UserPrefs;
import t13.modbook.model.module.Module;
import t13.modbook.model.module.lesson.Lesson;
import t13.modbook.logic.commands.CommandTestUtil;
import t13.modbook.testutil.TypicalIndexes;
import t13.modbook.testutil.TypicalModules;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteLessonCommand}.
 */
public class DeleteLessonCommandTest {
    private Model model = new ModelManager(TypicalModules.getTypicalModBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Module targetModule = TypicalModules.CS2103T.deepCopy();
        Lesson lessonToDelete = targetModule.getLessons().get(TypicalIndexes.INDEX_FIRST_LESSON.getZeroBased());
        DeleteCommand deleteLessonCommand = new DeleteLessonCommand(TypicalIndexes.INDEX_FIRST_LESSON, targetModule.getCode());
        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                lessonToDelete.getName(), targetModule.getCode());
        ModelManager expectedModel = new ModelManager(model.getModBook(), new UserPrefs());
        expectedModel.deleteLesson(targetModule, lessonToDelete);
        assertCommandSuccess(deleteLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Module targetModule = model.getFilteredModuleList().get(TypicalIndexes.INDEX_FIRST_MODULE.getZeroBased());
        Index outOfBoundIndex = Index.fromZeroBased(targetModule.getLessons().size() + 1);
        DeleteCommand deleteLessonCommand = new DeleteLessonCommand(outOfBoundIndex, targetModule.getCode());
        CommandTestUtil.assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showModuleAtIndex(model, TypicalIndexes.INDEX_FIRST_MODULE);
        Module targetModule = TypicalModules.CS2103T.deepCopy();
        Lesson lessonToDelete = targetModule.getLessons().get(TypicalIndexes.INDEX_FIRST_LESSON.getZeroBased());
        DeleteCommand deleteLessonCommand = new DeleteLessonCommand(TypicalIndexes.INDEX_FIRST_LESSON, targetModule.getCode());
        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                lessonToDelete.getName(), targetModule.getCode());
        Model expectedModel = new ModelManager(model.getModBook(), new UserPrefs());
        expectedModel.deleteLesson(targetModule, lessonToDelete);
        assertCommandSuccess(deleteLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showModuleAtIndex(model, TypicalIndexes.INDEX_FIRST_MODULE);
        Module targetModule = model.getFilteredModuleList().get(TypicalIndexes.INDEX_FIRST_MODULE.getZeroBased());
        Index outOfBoundIndex = Index.fromZeroBased(targetModule.getLessons().size() + 1);
        DeleteCommand deleteLessonCommand = new DeleteLessonCommand(outOfBoundIndex, targetModule.getCode());
        CommandTestUtil.assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteLessonFirstCommand = new DeleteLessonCommand(TypicalIndexes.INDEX_FIRST_LESSON, TypicalModules.CS2103T.getCode());
        DeleteCommand deleteLessonSecondCommand = new DeleteLessonCommand(TypicalIndexes.INDEX_SECOND_LESSON, TypicalModules.CS2103T.getCode());

        // same object -> returns true
        assertEquals(deleteLessonFirstCommand, deleteLessonFirstCommand);

        // same values -> returns true
        DeleteCommand deleteLessonFirstCommandCopy = new DeleteLessonCommand(TypicalIndexes.INDEX_FIRST_LESSON, TypicalModules.CS2103T.getCode());
        assertEquals(deleteLessonFirstCommand, deleteLessonFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(deleteLessonFirstCommand, 1);

        // null -> returns false
        assertNotEquals(deleteLessonFirstCommand, null);

        // different module command -> returns false
        assertNotEquals(deleteLessonFirstCommand, deleteLessonSecondCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoModule(Model model) {
        model.updateFilteredModuleList(m -> false);
        assertTrue(model.getFilteredModuleList().isEmpty());
    }
}
