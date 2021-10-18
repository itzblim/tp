package t13.modbook.model.module.exam;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static t13.modbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import t13.modbook.testutil.Assert;

public class ExamNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExamName(null));
    }

    @Test
    public void constructor_invalidExamName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ExamName(invalidName));
    }

    @Test
    public void isValidExamName() {
        // null exam name
        Assert.assertThrows(NullPointerException.class, () -> ExamName.isValidExamName(null));

        // invalid exam name
        assertFalse(ExamName.isValidExamName("")); // empty string
        assertFalse(ExamName.isValidExamName(" ")); // spaces only

        // valid exam name
        assertTrue(ExamName.isValidExamName("finals")); // lowercase alphabets only
        assertTrue(ExamName.isValidExamName("reading assessment 2")); // alphanumeric characters
        assertTrue(ExamName.isValidExamName("Mastery Check 1")); // with capital letters
        assertTrue(ExamName.isValidExamName("Practical Examination 1 for Circuits and Systems")); // long names
    }
}
