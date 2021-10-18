package teethirteen.modbook.model.module.lesson;

import static java.util.Objects.requireNonNull;
import static teethirteen.modbook.commons.util.AppUtil.checkArgument;

/**
 * Represents the name of a Lesson in the ModBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidLessonName(String)}
 */
public class LessonName {
    public static final String MESSAGE_CONSTRAINTS =
            "Lesson name should not be blank";
    /*
     * The first character of the exam must not be a whitespace.
     */
    public static final String VALIDATION_REGEX = "^\\S.*$";
    public final String fullLessonName;

    /**
     * Constructs an {@code ExamName}.
     *
     * @param lessonName A valid lesson name.
     */
    public LessonName(String lessonName) {
        requireNonNull(lessonName);
        checkArgument(isValidLessonName(lessonName), MESSAGE_CONSTRAINTS);
        fullLessonName = lessonName;
    }

    public String getLessonName() {
        return fullLessonName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidLessonName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullLessonName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LessonName // instanceof handles nulls
                && fullLessonName.equals(((LessonName) other).fullLessonName)); // state check
    }

    @Override
    public int hashCode() {
        return fullLessonName.hashCode();
    }
}
