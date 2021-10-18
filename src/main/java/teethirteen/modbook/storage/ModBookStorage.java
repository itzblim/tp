package teethirteen.modbook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import teethirteen.modbook.commons.exceptions.DataConversionException;
import teethirteen.modbook.model.ModBook;
import teethirteen.modbook.model.ReadOnlyModBook;

/**
 * Represents a storage for {@link ModBook}.
 */
public interface ModBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getModBookFilePath();

    /**
     * Returns ModBook data as a {@link ReadOnlyModBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyModBook> readModBook() throws DataConversionException, IOException;

    /**
     * @see #getModBookFilePath()
     */
    Optional<ReadOnlyModBook> readModBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyModBook} to the storage.
     * @param modBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveModBook(ReadOnlyModBook modBook) throws IOException;

    /**
     * @see #saveModBook(ReadOnlyModBook)
     */
    void saveModBook(ReadOnlyModBook modBook, Path filePath) throws IOException;

}
