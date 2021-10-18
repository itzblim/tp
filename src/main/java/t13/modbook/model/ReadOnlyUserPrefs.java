package t13.modbook.model;

import java.nio.file.Path;

import t13.modbook.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getModBookFilePath();

}
