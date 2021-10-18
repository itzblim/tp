package teethirteen.modbook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import teethirteen.modbook.commons.exceptions.DataConversionException;
import teethirteen.modbook.model.ReadOnlyModBook;
import teethirteen.modbook.model.ReadOnlyUserPrefs;
import teethirteen.modbook.model.UserPrefs;

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
