package teethirteen.modbook.model.module.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static teethirteen.modbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LessonNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LessonName(null));
    }

    @Test
    public void constructor_invalidLessonName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new LessonName(invalidName));
    }

    @Test
    public void isValidLessonName() {
        // null Lesson name
        assertThrows(NullPointerException.class, () -> LessonName.isValidLessonName(null));

        // invalid Lesson name
        assertFalse(LessonName.isValidLessonName("")); // empty string
        assertFalse(LessonName.isValidLessonName(" ")); // spaces only

        // valid Lesson name
        assertTrue(LessonName.isValidLessonName("tutorial")); // lowercase alphabets only
        assertTrue(LessonName.isValidLessonName("lecture 2")); // alphanumeric characters
        assertTrue(LessonName.isValidLessonName("Recitation 9")); // with capital letters
        assertTrue(LessonName.isValidLessonName(
                "Exclusive consultation only for the brightest minds in SOC")); // long names
    }
}
