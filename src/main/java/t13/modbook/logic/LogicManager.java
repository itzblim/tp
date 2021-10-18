package t13.modbook.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import t13.modbook.commons.core.GuiSettings;
import t13.modbook.commons.core.LogsCenter;
import t13.modbook.logic.commands.Command;
import t13.modbook.logic.commands.CommandResult;
import t13.modbook.logic.commands.GuiState;
import t13.modbook.logic.commands.exceptions.CommandException;
import t13.modbook.logic.parser.ModBookParser;
import t13.modbook.logic.parser.exceptions.ParseException;
import t13.modbook.model.Model;
import t13.modbook.model.module.Module;
import t13.modbook.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ModBookParser modBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        modBookParser = new ModBookParser();
    }

    @Override
    public CommandResult execute(String commandText, GuiState guiState) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = modBookParser.parseCommand(commandText, guiState);
        commandResult = command.execute(model);

        try {
            storage.saveModBook(model.getModBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return model.getFilteredModuleList();
    }

    @Override
    public Path getModBookFilePath() {
        return model.getModBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
