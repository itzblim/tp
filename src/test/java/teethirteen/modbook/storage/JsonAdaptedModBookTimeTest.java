package teethirteen.modbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import teethirteen.modbook.commons.exceptions.IllegalValueException;
import teethirteen.modbook.model.module.ModBookTime;
import teethirteen.modbook.testutil.Assert;

class JsonAdaptedModBookTimeTest {
    private static final String INVALID_TIME = "2500";
    private static final ModBookTime VALID_MODBOOKTIME = new ModBookTime("12:00");

    @Test
    public void toModelType_validTime_returnsModBookTime() throws Exception {
        JsonAdaptedModBookTime time = new JsonAdaptedModBookTime(VALID_MODBOOKTIME);
        assertEquals(VALID_MODBOOKTIME, time.toModelType());
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedModBookTime time = new JsonAdaptedModBookTime(INVALID_TIME);
        String expectedMessage = ModBookTime.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, time::toModelType);
    }
}
