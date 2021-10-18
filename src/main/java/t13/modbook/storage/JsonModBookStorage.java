package t13.modbook.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import t13.modbook.commons.core.LogsCenter;
import t13.modbook.commons.exceptions.DataConversionException;
import t13.modbook.commons.exceptions.IllegalValueException;
import t13.modbook.commons.util.FileUtil;
import t13.modbook.commons.util.JsonUtil;
import t13.modbook.model.ReadOnlyModBook;

/**
 * A class to access ModBook data stored as a json file on the hard disk.
 */
public class JsonModBookStorage implements ModBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonModBookStorage.class);

    private Path filePath;

    public JsonModBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getModBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyModBook> readModBook() throws DataConversionException {
        return readModBook(filePath);
    }

    /**
     * Similar to {@link #readModBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyModBook> readModBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableModBook> jsonModBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableModBook.class);
        if (!jsonModBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonModBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveModBook(ReadOnlyModBook modBook) throws IOException {
        saveModBook(modBook, filePath);
    }

    /**
     * Similar to {@link #saveModBook(ReadOnlyModBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveModBook(ReadOnlyModBook modBook, Path filePath) throws IOException {
        requireNonNull(modBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableModBook(modBook), filePath);
    }

}
