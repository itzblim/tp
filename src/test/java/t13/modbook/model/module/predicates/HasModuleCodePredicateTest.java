package t13.modbook.model.module.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import t13.modbook.model.module.ModuleCode;
import t13.modbook.testutil.builders.ModuleBuilder;

public class HasModuleCodePredicateTest {

    private static ModuleCode modCode = new ModuleCode(ModuleBuilder.DEFAULT_CODE);

    @Test
    public void equals() {
        ModuleCode secondModuleCode = new ModuleCode("CS2101");

        HasModuleCodePredicate firstPredicate = new HasModuleCodePredicate(modCode);
        HasModuleCodePredicate secondPredicate = new HasModuleCodePredicate(secondModuleCode);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        HasModuleCodePredicate firstPredicateCopy = new HasModuleCodePredicate(modCode);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different module code -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_moduleContainsModuleCode_returnsTrue() {
        HasModuleCodePredicate predicate = new HasModuleCodePredicate(modCode);
        assertTrue(predicate.test(new ModuleBuilder().build()));
    }

    @Test
    public void test_moduleDoesNotContainModuleCode_returnsFalse() {
        HasModuleCodePredicate predicate = new HasModuleCodePredicate(modCode);
        assertFalse(predicate.test(new ModuleBuilder().withCode("CS1101S").build()));
    }
}
