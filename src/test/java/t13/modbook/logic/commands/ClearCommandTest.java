package t13.modbook.logic.commands;

import static t13.modbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static t13.modbook.testutil.TypicalModules.getTypicalModBook;

import org.junit.jupiter.api.Test;

import t13.modbook.model.ModBook;
import t13.modbook.model.Model;
import t13.modbook.model.ModelManager;
import t13.modbook.model.UserPrefs;
import t13.modbook.testutil.TypicalPersons;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), getTypicalModBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), getTypicalModBook(), new UserPrefs());
        expectedModel.setModBook(new ModBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
