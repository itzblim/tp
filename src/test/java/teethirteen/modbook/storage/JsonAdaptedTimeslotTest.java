package teethirteen.modbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static teethirteen.modbook.storage.JsonAdaptedTimeslot.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.jupiter.api.Test;

import teethirteen.modbook.commons.exceptions.IllegalValueException;
import teethirteen.modbook.model.module.ModBookTime;
import teethirteen.modbook.model.module.Timeslot;
import teethirteen.modbook.testutil.builders.TimeslotBuilder;
import teethirteen.modbook.testutil.Assert;

class JsonAdaptedTimeslotTest {
    private static final JsonAdaptedModBookTime START_TIME = new JsonAdaptedModBookTime("10:00");
    private static final JsonAdaptedModBookTime END_TIME = new JsonAdaptedModBookTime("11:30");
    private static final Timeslot VALID_TIMESLOT = new TimeslotBuilder().build();

    @Test
    public void toModelType_validTime_returnsTimeslot() throws Exception {
        JsonAdaptedTimeslot timeslot = new JsonAdaptedTimeslot(VALID_TIMESLOT);
        assertEquals(VALID_TIMESLOT, timeslot.toModelType());
    }

    @Test
    public void toModelType_invalidStartEndTime_throwsIllegalValueException() {
        JsonAdaptedTimeslot timeslot = new JsonAdaptedTimeslot(END_TIME, START_TIME);
        String expectedMessage = Timeslot.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, timeslot::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedTimeslot timeslot = new JsonAdaptedTimeslot(null, END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModBookTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, timeslot::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        JsonAdaptedTimeslot timeslot = new JsonAdaptedTimeslot(START_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModBookTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, timeslot::toModelType);
    }
}
