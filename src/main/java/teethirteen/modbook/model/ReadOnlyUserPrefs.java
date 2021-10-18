package teethirteen.modbook.model;

import java.nio.file.Path;

import teethirteen.modbook.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getModBookFilePath();

}
