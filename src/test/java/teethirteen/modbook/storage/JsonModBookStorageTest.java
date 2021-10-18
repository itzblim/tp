package teethirteen.modbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static teethirteen.modbook.testutil.TypicalModules.CS2030S;
import static teethirteen.modbook.testutil.TypicalModules.CS2040S;
import static teethirteen.modbook.testutil.TypicalModules.MA1521;
import static teethirteen.modbook.testutil.TypicalModules.getTypicalModBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import teethirteen.modbook.commons.exceptions.DataConversionException;
import teethirteen.modbook.model.ModBook;
import teethirteen.modbook.model.ReadOnlyModBook;
import teethirteen.modbook.testutil.Assert;

public class JsonModBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonModBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readModBook_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readModBook(null));
    }

    private java.util.Optional<ReadOnlyModBook> readModBook(String filePath) throws Exception {
        return new JsonModBookStorage(Paths.get(filePath)).readModBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readModBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readModBook("notJsonFormatModBook.json"));
    }

    @Test
    public void readModBook_invalidModuleModBook_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readModBook("invalidModuleModBook.json"));
    }

    @Test
    public void readModBook_invalidAndValidModuleModBook_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class,
                () -> readModBook("invalidAndValidModuleModBook.json"));
    }

    @Test
    public void readAndSaveModBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempModBook.json");
        ModBook original = getTypicalModBook();
        JsonModBookStorage jsonModBookStorage = new JsonModBookStorage(filePath);

        // Save in new file and read back
        jsonModBookStorage.saveModBook(original, filePath);
        ReadOnlyModBook readBack = jsonModBookStorage.readModBook(filePath).get();
        assertEquals(original, new ModBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addModule(MA1521);
        original.removeModule(CS2040S);
        jsonModBookStorage.saveModBook(original, filePath);
        readBack = jsonModBookStorage.readModBook(filePath).get();
        assertEquals(original, new ModBook(readBack));

        // Save and read without specifying file path
        original.addModule(CS2030S);
        jsonModBookStorage.saveModBook(original); // file path not specified
        readBack = jsonModBookStorage.readModBook().get(); // file path not specified
        assertEquals(original, new ModBook(readBack));

    }

    @Test
    public void saveModBook_nullModBook_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveModBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code modBook} at the specified {@code filePath}.
     */
    private void saveModBook(ReadOnlyModBook modBook, String filePath) {
        try {
            new JsonModBookStorage(Paths.get(filePath))
                    .saveModBook(modBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveModBook_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveModBook(new ModBook(), null));
    }
}
