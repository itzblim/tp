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
import t13.modbook.model.module.exam.Exam;
import t13.modbook.logic.commands.CommandTestUtil;
import t13.modbook.testutil.TypicalIndexes;
import t13.modbook.testutil.TypicalModules;
import t13.modbook.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteExamCommand}.
 */
public class DeleteExamCommandTest {
    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), TypicalModules.getTypicalModBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // using a CS2103T clone for tests to prevent double deletions (both by command and tests)
        Module targetModule = TypicalModules.CS2103T.deepCopy();
        Exam examToDelete = targetModule.getExams().get(TypicalIndexes.INDEX_FIRST_EXAM.getZeroBased());
        DeleteCommand deleteExamCommand = new DeleteExamCommand(TypicalIndexes.INDEX_FIRST_EXAM, targetModule.getCode());
        String expectedMessage = String.format(DeleteExamCommand.MESSAGE_DELETE_EXAM_SUCCESS,
                examToDelete.getName(), targetModule.getCode());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getModBook(), new UserPrefs());
        expectedModel.deleteExam(targetModule, examToDelete);
        assertCommandSuccess(deleteExamCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Module targetModule = model.getFilteredModuleList().get(TypicalIndexes.INDEX_FIRST_MODULE.getZeroBased());
        Index outOfBoundIndex = Index.fromZeroBased(targetModule.getExams().size() + 1);
        DeleteCommand deleteExamCommand = new DeleteExamCommand(outOfBoundIndex, targetModule.getCode());
        CommandTestUtil.assertCommandFailure(deleteExamCommand, model, Messages.MESSAGE_INVALID_EXAM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showModuleAtIndex(model, TypicalIndexes.INDEX_FIRST_MODULE);
        Module targetModule = TypicalModules.CS2103T.deepCopy();
        Exam examToDelete = targetModule.getExams().get(TypicalIndexes.INDEX_FIRST_EXAM.getZeroBased());
        DeleteCommand deleteExamCommand = new DeleteExamCommand(TypicalIndexes.INDEX_FIRST_EXAM, targetModule.getCode());
        String expectedMessage = String.format(DeleteExamCommand.MESSAGE_DELETE_EXAM_SUCCESS, examToDelete.getName(),
                targetModule.getCode());
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getModBook(), new UserPrefs());
        expectedModel.deleteExam(targetModule, examToDelete);
        showNoModule(expectedModel);
        assertCommandSuccess(deleteExamCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showModuleAtIndex(model, TypicalIndexes.INDEX_FIRST_MODULE);
        Module targetModule = model.getFilteredModuleList().get(TypicalIndexes.INDEX_FIRST_MODULE.getZeroBased());
        Index outOfBoundIndex = Index.fromZeroBased(targetModule.getExams().size() + 1);
        DeleteCommand deleteExamCommand = new DeleteExamCommand(outOfBoundIndex, targetModule.getCode());
        CommandTestUtil.assertCommandFailure(deleteExamCommand, model, Messages.MESSAGE_INVALID_EXAM_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteExamFirstCommand = new DeleteExamCommand(TypicalIndexes.INDEX_FIRST_EXAM, TypicalModules.CS2103T.getCode());
        DeleteCommand deleteExamSecondCommand = new DeleteExamCommand(TypicalIndexes.INDEX_SECOND_EXAM, TypicalModules.CS2103T.getCode());

        // same object -> returns true
        assertEquals(deleteExamFirstCommand, deleteExamFirstCommand);

        // same values -> returns true
        DeleteCommand deleteExamFirstCommandCopy = new DeleteExamCommand(TypicalIndexes.INDEX_FIRST_EXAM, TypicalModules.CS2103T.getCode());
        assertEquals(deleteExamFirstCommand, deleteExamFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(deleteExamFirstCommand, 1);

        // null -> returns false
        assertNotEquals(deleteExamFirstCommand, null);

        // different module command -> returns false
        assertNotEquals(deleteExamFirstCommand, deleteExamSecondCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoModule(Model model) {
        model.updateFilteredModuleList(m -> false);
        assertTrue(model.getFilteredModuleList().isEmpty());
    }
}
