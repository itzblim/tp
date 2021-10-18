package t13.modbook.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static t13.modbook.commons.core.Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX;
import static t13.modbook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_CODE;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import t13.modbook.logic.commands.ClearCommand;
import t13.modbook.logic.commands.CommandResult;
import t13.modbook.logic.commands.GuiState;
import t13.modbook.logic.commands.add.AddCommand;
import t13.modbook.logic.commands.exceptions.CommandException;
import t13.modbook.logic.parser.exceptions.ParseException;
import t13.modbook.model.Model;
import t13.modbook.model.ModelManager;
import t13.modbook.model.ReadOnlyModBook;
import t13.modbook.model.UserPrefs;
import t13.modbook.model.module.Module;
import t13.modbook.storage.JsonModBookStorage;
import t13.modbook.storage.JsonUserPrefsStorage;
import t13.modbook.storage.StorageManager;
import t13.modbook.testutil.builders.ModuleBuilder;
import t13.modbook.logic.commands.CommandTestUtil;
import t13.modbook.testutil.Assert;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");
    private static final GuiState DEFAULT_STATE = GuiState.SUMMARY;

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonModBookStorage modBookStorage =
                new JsonModBookStorage(temporaryFolder.resolve("modBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(modBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete mod 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String clearCommand = ClearCommand.COMMAND_WORD;
        assertCommandSuccess(clearCommand, ClearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonModBookIoExceptionThrowingStub
        JsonModBookStorage modBookStorage =
                new JsonModBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionModBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(modBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + " mod " + PREFIX_CODE + CommandTestUtil.VALID_MODULE_CODE;
        Module expectedModule = new ModuleBuilder().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addModule(expectedModule);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredModuleList().remove(0));
    }

    /**
     * Executes the command in the given GuiState and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, GuiState, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, GuiState guiState, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand, guiState);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command in the default GuiState and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, GuiState, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        assertCommandSuccess(inputCommand, DEFAULT_STATE, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getModBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command in the given GuiState and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, GuiState, String, Model)
     */
    private void assertCommandFailure(String inputCommand, GuiState guiState,
            Class<? extends Throwable> expectedException, String expectedMessage, Model expectedModel) {
        Assert.assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand, guiState));
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command in the default GuiState and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, GuiState, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertCommandFailure(inputCommand, DEFAULT_STATE, expectedException, expectedMessage, expectedModel);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonModBookIoExceptionThrowingStub extends JsonModBookStorage {
        private JsonModBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveModBook(ReadOnlyModBook modBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
