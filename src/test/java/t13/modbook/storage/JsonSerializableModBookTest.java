package t13.modbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static t13.modbook.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import t13.modbook.commons.exceptions.IllegalValueException;
import t13.modbook.commons.util.JsonUtil;
import t13.modbook.model.ModBook;
import t13.modbook.testutil.TypicalModules;
import t13.modbook.testutil.Assert;

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
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateModules_throwsIllegalValueException() throws Exception {
        JsonSerializableModBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MODULE_FILE,
                JsonSerializableModBook.class).get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableModBook.MESSAGE_DUPLICATE_MODULE,
                dataFromFile::toModelType);
    }

}
