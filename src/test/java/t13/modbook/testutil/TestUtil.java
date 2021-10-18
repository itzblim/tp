package t13.modbook.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import t13.modbook.commons.core.index.Index;
import t13.modbook.model.Model;
import t13.modbook.model.module.Module;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the  module in the {@code model}'s  module list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredModuleList().size() / 2);
    }

    /**
     * Returns the last index of the  module in the {@code model}'s  module list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredModuleList().size());
    }

    /**
     * Returns the  module in the {@code model}'s module list at {@code index}.
     */
    public static Module getModule(Model model, Index index) {
        return model.getFilteredModuleList().get(index.getZeroBased());
    }

    /**
     * Returns true if the first {@code Comparable} given is before the second {@code Comparable} given
     * Used for testing {@code compareTo()}
     */
    public static <T> boolean isBefore(Comparable<T> before, T after) {
        return before.compareTo(after) < 0;
    }
}
