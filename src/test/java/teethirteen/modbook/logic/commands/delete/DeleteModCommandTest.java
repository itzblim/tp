package teethirteen.modbook.logic.commands.delete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static teethirteen.modbook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import teethirteen.modbook.commons.core.Messages;
import teethirteen.modbook.commons.core.index.Index;
import teethirteen.modbook.logic.commands.CommandTestUtil;
import teethirteen.modbook.model.Model;
import teethirteen.modbook.model.ModelManager;
import teethirteen.modbook.model.UserPrefs;
import teethirteen.modbook.model.module.Module;
import teethirteen.modbook.testutil.TypicalIndexes;
import teethirteen.modbook.testutil.TypicalModules;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteModCommand}.
 */
public class DeleteModCommandTest {
    private Model model = new ModelManager(TypicalModules.getTypicalModBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Module moduleToDelete = model.getFilteredModuleList().get(TypicalIndexes.INDEX_FIRST_MODULE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteModCommand(TypicalIndexes.INDEX_FIRST_MODULE);
        String expectedMessage = String.format(DeleteModCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);
        ModelManager expectedModel = new ModelManager(model.getModBook(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        DeleteCommand deleteCommand = new DeleteModCommand(outOfBoundIndex);
        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showModuleAtIndex(model, TypicalIndexes.INDEX_FIRST_MODULE);
        Module moduleToDelete = model.getFilteredModuleList().get(TypicalIndexes.INDEX_FIRST_MODULE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteModCommand(TypicalIndexes.INDEX_FIRST_MODULE);
        String expectedMessage = String.format(DeleteModCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);
        Model expectedModel = new ModelManager(model.getModBook(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);
        showNoModule(expectedModel);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showModuleAtIndex(model, TypicalIndexes.INDEX_FIRST_MODULE);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_MODULE;
        // ensures that outOfBoundIndex is still in bounds of ModBook module list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getModBook().getModuleList().size());
        DeleteCommand deleteCommand = new DeleteModCommand(outOfBoundIndex);
        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteModFirstCommand = new DeleteModCommand(TypicalIndexes.INDEX_FIRST_MODULE);
        DeleteCommand deleteModSecondCommand = new DeleteModCommand(TypicalIndexes.INDEX_SECOND_MODULE);

        // same object -> returns true
        assertEquals(deleteModFirstCommand, deleteModFirstCommand);

        // same values -> returns true
        DeleteCommand deleteModFirstCommandCopy = new DeleteModCommand(TypicalIndexes.INDEX_FIRST_MODULE);
        assertEquals(deleteModFirstCommand, deleteModFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(deleteModFirstCommand, 1);

        // null -> returns false
        assertNotEquals(deleteModFirstCommand, null);

        // different module command -> returns false
        assertNotEquals(deleteModFirstCommand, deleteModSecondCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoModule(Model model) {
        model.updateFilteredModuleList(m -> false);
        assertTrue(model.getFilteredModuleList().isEmpty());
    }
}
