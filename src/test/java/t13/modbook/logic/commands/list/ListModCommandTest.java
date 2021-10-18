package t13.modbook.logic.commands.list;

import static t13.modbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static t13.modbook.testutil.TypicalModules.getTypicalModBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import t13.modbook.logic.commands.CommandResult;
import t13.modbook.logic.commands.GuiState;
import t13.modbook.model.Model;
import t13.modbook.model.ModelManager;
import t13.modbook.model.UserPrefs;
import t13.modbook.logic.commands.CommandTestUtil;
import t13.modbook.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListModCommand.
 */
public class ListModCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalModBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListModCommand(), model,
                new CommandResult(ListModCommand.MESSAGE_SUCCESS, false, GuiState.SUMMARY),
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandTestUtil.showModuleAtIndex(model, TypicalIndexes.INDEX_FIRST_MODULE);
        assertCommandSuccess(new ListModCommand(), model,
                new CommandResult(ListModCommand.MESSAGE_SUCCESS, false, GuiState.SUMMARY),
                expectedModel);
    }
}
