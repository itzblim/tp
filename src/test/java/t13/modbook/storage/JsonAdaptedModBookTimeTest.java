package t13.modbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static t13.modbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import t13.modbook.commons.exceptions.IllegalValueException;
import t13.modbook.model.module.ModBookTime;
import t13.modbook.testutil.Assert;

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
