package t13.modbook.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static t13.modbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleName(null));
    }

    @Test
    public void constructor_invalidModuleName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new ModuleName(invalidName));
    }

    @Test
    public void isValidModuleName() {
        // null module name
        assertThrows(NullPointerException.class, () -> ModuleName.isValidModuleName(null));

        // invalid module name
        assertFalse(ModuleName.isValidModuleName("")); // empty string
        assertFalse(ModuleName.isValidModuleName(" ")); // spaces only

        // valid module name
        assertTrue(ModuleName.isValidModuleName("software engineering")); // lowercase alphabets only
        assertTrue(ModuleName.isValidModuleName("machine learning 2")); // alphanumeric characters
        assertTrue(ModuleName.isValidModuleName("Machine Learning II")); // with capital letters
        assertTrue(ModuleName.isValidModuleName("Software Engineering on Modern Application Platforms")); // long names
    }
}
