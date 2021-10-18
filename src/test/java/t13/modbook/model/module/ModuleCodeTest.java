package t13.modbook.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static t13.modbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleCodeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }

    @Test
    public void constructor_invalidModuleCode_throwsIllegalArgumentException() {
        String invalidCode = "";
        assertThrows(IllegalArgumentException.class, () -> new ModuleCode(invalidCode));
    }

    @Test
    public void isValidModuleCode() {
        // null module code
        assertThrows(NullPointerException.class, () -> ModuleCode.isValidModuleCode(null));

        // invalid module code
        assertFalse(ModuleCode.isValidModuleCode("")); // empty string
        assertFalse(ModuleCode.isValidModuleCode(" ")); // spaces only
        assertFalse(ModuleCode.isValidModuleCode("C2040S")); // single letter prefix
        assertFalse(ModuleCode.isValidModuleCode("CSCS2040S")); // four letter prefix
        assertFalse(ModuleCode.isValidModuleCode("CS204S")); // three numbers only
        assertFalse(ModuleCode.isValidModuleCode("CS20S")); // two numbers only
        assertFalse(ModuleCode.isValidModuleCode("CS2S")); // one numbers only
        assertFalse(ModuleCode.isValidModuleCode("CS")); // no numbers
        assertFalse(ModuleCode.isValidModuleCode("CS2040SS")); // two letter suffix

        // valid module code
        assertTrue(ModuleCode.isValidModuleCode("cs2040s")); // lowercase alphabets only
        assertTrue(ModuleCode.isValidModuleCode("CS2040S")); // capital alphabets only
        assertTrue(ModuleCode.isValidModuleCode("cS2040s")); // mix of lowercase and capital alphabets
        assertTrue(ModuleCode.isValidModuleCode("CS2103")); // two letter with no ending letter
        assertTrue(ModuleCode.isValidModuleCode("CS2103T")); // two letter with ending letter
        assertTrue(ModuleCode.isValidModuleCode("DAO1000")); // three letter with no ending letter
        assertTrue(ModuleCode.isValidModuleCode("DAO1000S")); // three letter with ending letter
    }
}
