package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ModBook;
import seedu.address.testutil.TypicalModules;

public class JsonSerializableModBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableModBookTest");
    private static final Path TYPICAL_MODULES_FILE = TEST_DATA_FOLDER.resolve("typicalModulesModBook.json");
    private static final Path INVALID_MODULE_FILE = TEST_DATA_FOLDER.resolve("invalidModuleModBook.json");
    private static final Path DUPLICATE_MODULE_FILE = TEST_DATA_FOLDER.resolve("duplicateModuleModBook.json");

    @Test
    public void toModelType_typicalModulesFile_success() throws Exception {
        JsonSerializableModBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_MODULES_FILE,
                JsonSerializableModBook.class).get();
        ModBook modBookFromFile = dataFromFile.toModelType();
        ModBook typicalModulesModBook = TypicalModules.getTypicalModBook();
        assertEquals(modBookFromFile, typicalModulesModBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableModBook dataFromFile = JsonUtil.readJsonFile(INVALID_MODULE_FILE,
                JsonSerializableModBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateModules_throwsIllegalValueException() throws Exception {
        JsonSerializableModBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MODULE_FILE,
                JsonSerializableModBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableModBook.MESSAGE_DUPLICATE_MODULE,
                dataFromFile::toModelType);
    }

}
