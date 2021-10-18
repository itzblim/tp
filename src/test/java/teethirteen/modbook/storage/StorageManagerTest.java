package teethirteen.modbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import teethirteen.modbook.commons.core.GuiSettings;
import teethirteen.modbook.model.ModBook;
import teethirteen.modbook.model.ReadOnlyModBook;
import teethirteen.modbook.model.UserPrefs;
import teethirteen.modbook.testutil.TypicalModules;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonModBookStorage modBookStorage = new JsonModBookStorage(getTempFilePath("mb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(modBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void modBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonModBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonModBookStorageTest} class.
         */
        ModBook original = TypicalModules.getTypicalModBook();
        storageManager.saveModBook(original);
        ReadOnlyModBook retrieved = storageManager.readModBook().get();
        assertEquals(original, new ModBook(retrieved));
    }

    @Test
    public void getModBookFilePath() {
        assertNotNull(storageManager.getModBookFilePath());
    }

}
