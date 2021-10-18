package t13.modbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static t13.modbook.storage.JsonAdaptedExam.MISSING_FIELD_MESSAGE_FORMAT;
import static t13.modbook.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import t13.modbook.commons.exceptions.IllegalValueException;
import t13.modbook.model.module.Link;
import t13.modbook.model.module.ModBookDate;
import t13.modbook.model.module.Timeslot;
import t13.modbook.model.module.Venue;
import t13.modbook.model.module.exam.Exam;
import t13.modbook.model.module.exam.ExamName;
import t13.modbook.testutil.Assert;
import t13.modbook.testutil.TypicalExams;

class JsonAdaptedExamTest {
    private static final String INVALID_NAME = " ";
    private static final Optional<String> INVALID_VENUE = Optional.of("  ");
    private static final Optional<String> INVALID_LINK = Optional.of("    ");

    private static final String VALID_NAME = TypicalExams.CS2103T_FINALS.getName().toString();
    private static final JsonAdaptedModBookDate VALID_DATE = new JsonAdaptedModBookDate(TypicalExams.CS2103T_FINALS.getDate());
    private static final JsonAdaptedTimeslot VALID_TIMESLOT = new JsonAdaptedTimeslot(TypicalExams.CS2103T_FINALS.getTimeslot());
    private static final Optional<String> VALID_VENUE = Optional.of("MPSH 1");
    private static final Optional<String> VALID_LINK = TypicalExams.CS2103T_FINALS.getLink().map(Link::toString);
    private static final Optional<String> EMPTY_VENUE = Optional.empty();
    private static final Optional<String> EMPTY_LINK = Optional.empty();

    @Test
    public void toModelType_validExamDetails_returnsExam() throws Exception {
        JsonAdaptedExam exam = new JsonAdaptedExam(TypicalExams.CS2103T_FINALS);
        assertEquals(TypicalExams.CS2103T_FINALS, exam.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedExam exam =
                new JsonAdaptedExam(INVALID_NAME, VALID_DATE, VALID_TIMESLOT, VALID_VENUE, VALID_LINK);
        String expectedMessage = ExamName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, exam::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedExam exam =
                new JsonAdaptedExam(null, VALID_DATE, VALID_TIMESLOT, VALID_VENUE, VALID_LINK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ExamName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, exam::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedExam exam =
                new JsonAdaptedExam(VALID_NAME, null, VALID_TIMESLOT, VALID_VENUE, VALID_LINK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModBookDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, exam::toModelType);
    }

    @Test
    public void toModelType_nullTimeslot_throwsIllegalValueException() {
        JsonAdaptedExam exam =
                new JsonAdaptedExam(VALID_NAME, VALID_DATE, null, VALID_VENUE, VALID_LINK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Timeslot.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, exam::toModelType);
    }

    @Test
    public void toModelType_invalidVenue_throwsIllegalValueException() {
        JsonAdaptedExam exam =
                new JsonAdaptedExam(VALID_NAME, VALID_DATE, VALID_TIMESLOT, INVALID_VENUE, VALID_LINK);
        String expectedMessage = Venue.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, exam::toModelType);
    }

    @Test
    public void toModelType_nullVenue_success() {
        try {
            JsonAdaptedExam exam =
                    new JsonAdaptedExam(VALID_NAME, VALID_DATE, VALID_TIMESLOT, null, VALID_LINK);
            Exam readExam = exam.toModelType();
            assertEquals(Optional.empty(), readExam.getVenue());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void toModelType_emptyVenue_success() {
        try {
            JsonAdaptedExam exam =
                    new JsonAdaptedExam(VALID_NAME, VALID_DATE, VALID_TIMESLOT, EMPTY_VENUE, VALID_LINK);
            Exam readExam = exam.toModelType();
            assertEquals(Optional.empty(), readExam.getVenue());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void toModelType_invalidLink_throwsIllegalValueException() {
        JsonAdaptedExam exam =
                new JsonAdaptedExam(VALID_NAME, VALID_DATE, VALID_TIMESLOT, VALID_VENUE, INVALID_LINK);
        String expectedMessage = Link.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, exam::toModelType);
    }

    @Test
    public void toModelType_nullLink_success() {
        try {
            JsonAdaptedExam exam =
                    new JsonAdaptedExam(VALID_NAME, VALID_DATE, VALID_TIMESLOT, VALID_VENUE, null);
            Exam readExam = exam.toModelType();
            assertEquals(Optional.empty(), readExam.getLink());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void toModelType_emptyLink_success() {
        try {
            JsonAdaptedExam exam =
                    new JsonAdaptedExam(VALID_NAME, VALID_DATE, VALID_TIMESLOT, VALID_VENUE, EMPTY_LINK);
            Exam readExam = exam.toModelType();
            assertEquals(Optional.empty(), readExam.getLink());
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}
