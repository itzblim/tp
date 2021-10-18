package teethirteen.modbook.logic.commands.list;

import static teethirteen.modbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static teethirteen.modbook.testutil.TypicalModules.getTypicalModBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import teethirteen.modbook.logic.commands.CommandResult;
import teethirteen.modbook.logic.commands.GuiState;
import teethirteen.modbook.model.Model;
import teethirteen.modbook.model.ModelManager;
import teethirteen.modbook.model.UserPrefs;
import teethirteen.modbook.logic.commands.CommandTestUtil;
import teethirteen.modbook.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListLessonCommand.
 */
public class ListLessonCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalModBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalModBook(), new UserPrefs());
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
