package t13.modbook.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import t13.modbook.commons.core.GuiSettings;
import t13.modbook.logic.commands.CommandResult;
import t13.modbook.logic.commands.GuiState;
import t13.modbook.logic.commands.exceptions.CommandException;
import t13.modbook.logic.parser.exceptions.ParseException;
import t13.modbook.model.module.Module;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @param guiState The current state of the GUI.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText, GuiState guiState) throws CommandException, ParseException;


    /** Returns an unmodifiable view of the filtered list of modules */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Returns the user prefs' Module book file path.
     */
    Path getModBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
