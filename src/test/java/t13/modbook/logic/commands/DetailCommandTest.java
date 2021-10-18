package t13.modbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static t13.modbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static t13.modbook.testutil.TypicalModules.CS2103T;
import static t13.modbook.testutil.TypicalModules.MA1521;
import static t13.modbook.testutil.TypicalModules.getTypicalModBook;
import static t13.modbook.testutil.TypicalModules.getTypicalModules;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import t13.modbook.model.Model;
import t13.modbook.model.ModelManager;
import t13.modbook.model.UserPrefs;
import t13.modbook.model.module.ModuleCode;
import t13.modbook.model.module.predicates.HasModuleCodePredicate;
import t13.modbook.commons.core.Messages;

/**
 * Contains integration tests (interaction with the Model) for {@code DetailCommand}
 */
public class DetailCommandTest {
    private Model model = new ModelManager(getTypicalModBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalModBook(), new UserPrefs());

    @Test
    public void equals() {
        HasModuleCodePredicate firstPredicate =
                new HasModuleCodePredicate(new ModuleCode("CS2103T"));
        HasModuleCodePredicate secondPredicate =
                new HasModuleCodePredicate(new ModuleCode("CS2101"));

        DetailCommand detailFirstCommand = new DetailCommand(firstPredicate);
        DetailCommand detailSecondCommand = new DetailCommand(secondPredicate);

        // same object -> returns true
        assertTrue(detailFirstCommand.equals(detailFirstCommand));

        // same values -> returns true
        DetailCommand detailFirstCommandCopy = new DetailCommand(firstPredicate);
        assertTrue(detailFirstCommand.equals(detailFirstCommandCopy));

        // different types -> returns false
        assertFalse(detailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(detailFirstCommand.equals(null));

        // different code -> returns false
        assertFalse(detailFirstCommand.equals(detailSecondCommand));
    }

    @Test
    public void execute_validKeyword_moduleFound() {
        String expectedMessage = String.format(Messages.MESSAGE_MODULE_DETAILS_LISTED, CS2103T.getCode());
        CommandResult expectedResult = new CommandResult(expectedMessage, false, GuiState.DETAILS);
        HasModuleCodePredicate predicate = preparePredicate(CS2103T.getCode().toString());
        DetailCommand command = new DetailCommand(predicate);
        expectedModel.updateFilteredModuleList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Arrays.asList(CS2103T), model.getFilteredModuleList());
    }

    @Test
    public void execute_moduleNotFound_listAllModules() {
        String expectedMessage = String.format(Messages.MESSAGE_MODULE_NOT_FOUND, MA1521.getCode());
        CommandResult expectedResult = new CommandResult(expectedMessage, false, GuiState.SUMMARY);
        HasModuleCodePredicate predicate = preparePredicate(MA1521.getCode().toString());
        DetailCommand command = new DetailCommand(predicate);
        expectedModel.updateFilteredModuleList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(getTypicalModules(), model.getFilteredModuleList());
    }

    /**
     * Parses {@code userInput} into a {@code ModuleHasModuleCodePredicate}.
     */
    private HasModuleCodePredicate preparePredicate(String userInput) {
        return new HasModuleCodePredicate(new ModuleCode(userInput));
    }
}
