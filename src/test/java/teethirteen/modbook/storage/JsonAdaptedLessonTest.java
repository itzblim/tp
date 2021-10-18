package teethirteen.modbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static teethirteen.modbook.storage.JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import teethirteen.modbook.commons.exceptions.IllegalValueException;
import teethirteen.modbook.model.module.Day;
import teethirteen.modbook.model.module.Link;
import teethirteen.modbook.model.module.Timeslot;
import teethirteen.modbook.model.module.Venue;
import teethirteen.modbook.model.module.lesson.Lesson;
import teethirteen.modbook.model.module.lesson.LessonName;
import teethirteen.modbook.testutil.Assert;
import teethirteen.modbook.testutil.TypicalLessons;

public class JsonAdaptedLessonTest {
    private static final String INVALID_NAME = " ";
    private static final String INVALID_DAY = "today";
    private static final Optional<String> INVALID_VENUE = Optional.of("  ");
    private static final Optional<String> INVALID_LINK = Optional.of("    ");

    private static final String VALID_NAME = TypicalLessons.CS2103T_LECTURE.getName().toString();
    private static final String VALID_DAY = TypicalLessons.CS2103T_LECTURE.getDay().toString();
    private static final JsonAdaptedTimeslot VALID_TIMESLOT =
            new JsonAdaptedTimeslot(TypicalLessons.CS2103T_LECTURE.getTimeslot());
    private static final Optional<String> VALID_VENUE = Optional.of("LT15");
    private static final Optional<String> VALID_LINK = TypicalLessons.CS2103T_LECTURE.getLink().map(Link::toString);
    private static final Optional<String> EMPTY_VENUE = Optional.empty();
    private static final Optional<String> EMPTY_LINK = Optional.empty();

    @Test
    public void toModelType_validLessonDetails_returnsLesson() throws Exception {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(TypicalLessons.CS2103T_LECTURE);
        assertEquals(TypicalLessons.CS2103T_LECTURE, lesson.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(INVALID_NAME, VALID_DAY, VALID_TIMESLOT, VALID_VENUE, VALID_LINK);
        String expectedMessage = LessonName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(null, VALID_DAY, VALID_TIMESLOT, VALID_VENUE, VALID_LINK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LessonName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidDay_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(VALID_NAME, INVALID_DAY, VALID_TIMESLOT, VALID_VENUE, VALID_LINK);
        String expectedMessage = Day.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullDay_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(VALID_NAME, null, VALID_TIMESLOT, VALID_VENUE, VALID_LINK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullTimeslot_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(VALID_NAME, VALID_DAY, null, VALID_VENUE, VALID_LINK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Timeslot.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }


    @Test
    public void toModelType_invalidVenue_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(VALID_NAME, VALID_DAY, VALID_TIMESLOT, INVALID_VENUE, VALID_LINK);
        String expectedMessage = Venue.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullVenue_success() {
        try {
            JsonAdaptedLesson lesson =
                    new JsonAdaptedLesson(VALID_NAME, VALID_DAY, VALID_TIMESLOT, null, VALID_LINK);
            Lesson readLesson = lesson.toModelType();
            assertEquals(Optional.empty(), readLesson.getVenue());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void toModelType_emptyVenue_success() {
        try {
            JsonAdaptedLesson lesson =
                    new JsonAdaptedLesson(VALID_NAME, VALID_DAY, VALID_TIMESLOT, EMPTY_VENUE, VALID_LINK);
            Lesson readLesson = lesson.toModelType();
            assertEquals(Optional.empty(), readLesson.getVenue());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void toModelType_invalidLink_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(VALID_NAME, VALID_DAY, VALID_TIMESLOT, VALID_VENUE, INVALID_LINK);
        String expectedMessage = Link.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullLink_success() {
        try {
            JsonAdaptedLesson lesson =
                    new JsonAdaptedLesson(VALID_NAME, VALID_DAY, VALID_TIMESLOT, VALID_VENUE, null);
            Lesson readLesson = lesson.toModelType();
            assertEquals(Optional.empty(), readLesson.getLink());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void toModelType_emptyLink_success() {
        try {
            JsonAdaptedLesson lesson =
                    new JsonAdaptedLesson(VALID_NAME, VALID_DAY, VALID_TIMESLOT, VALID_VENUE, EMPTY_LINK);
            Lesson readLesson = lesson.toModelType();
            assertEquals(Optional.empty(), readLesson.getLink());
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}
