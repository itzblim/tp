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
import t13.modbook.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListLessonCommand.
 */
public class ListLessonCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), getTypicalModBook(), new UserPrefs());
        expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), getTypicalModBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListLessonCommand(), model,
                new CommandResult(ListLessonCommand.MESSAGE_SUCCESS, false, GuiState.LESSONS),
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandTestUtil.showModuleAtIndex(model, TypicalIndexes.INDEX_FIRST_MODULE);
        assertCommandSuccess(new ListLessonCommand(), model,
                new CommandResult(ListLessonCommand.MESSAGE_SUCCESS, false, GuiState.LESSONS),
                expectedModel);
    }
}
