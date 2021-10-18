package t13.modbook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import t13.modbook.commons.exceptions.DataConversionException;
import t13.modbook.model.ReadOnlyModBook;
import t13.modbook.model.ReadOnlyUserPrefs;
import t13.modbook.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage, ModBookStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getModBookFilePath();

    @Override
    Optional<ReadOnlyModBook> readModBook() throws DataConversionException, IOException;

    @Override
    void saveModBook(ReadOnlyModBook modBook) throws IOException;

}
