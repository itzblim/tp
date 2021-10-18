package teethirteen.modbook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import teethirteen.modbook.commons.core.LogsCenter;
import teethirteen.modbook.commons.exceptions.DataConversionException;
import teethirteen.modbook.model.ReadOnlyModBook;
import teethirteen.modbook.model.ReadOnlyUserPrefs;
import teethirteen.modbook.model.UserPrefs;

/**
 * Manages storage of ModBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ModBookStorage modBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ModBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(ModBookStorage modBookStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.modBookStorage = modBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ ModBook methods ==============================

    @Override
    public Path getModBookFilePath() {
        return modBookStorage.getModBookFilePath();
    }

    @Override
    public Optional<ReadOnlyModBook> readModBook() throws DataConversionException, IOException {
        return readModBook(modBookStorage.getModBookFilePath());
    }

    @Override
    public Optional<ReadOnlyModBook> readModBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return modBookStorage.readModBook(filePath);
    }

    @Override
    public void saveModBook(ReadOnlyModBook modBook) throws IOException {
        saveModBook(modBook, modBookStorage.getModBookFilePath());
    }

    @Override
    public void saveModBook(ReadOnlyModBook modBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        modBookStorage.saveModBook(modBook, filePath);
    }

}
