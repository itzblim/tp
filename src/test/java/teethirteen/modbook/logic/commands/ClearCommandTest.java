package teethirteen.modbook.logic.commands;

import static teethirteen.modbook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import teethirteen.modbook.model.ModBook;
import teethirteen.modbook.model.Model;
import teethirteen.modbook.model.ModelManager;
import teethirteen.modbook.model.UserPrefs;
import teethirteen.modbook.testutil.TypicalModules;

public class ClearCommandTest {

    @Test
    public void execute_emptyModBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyModBook_success() {
        Model model = new ModelManager(TypicalModules.getTypicalModBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalModules.getTypicalModBook(), new UserPrefs());
        expectedModel.setModBook(new ModBook());

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
