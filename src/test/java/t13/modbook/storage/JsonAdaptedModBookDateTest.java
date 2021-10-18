package t13.modbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static t13.modbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import t13.modbook.commons.exceptions.IllegalValueException;
import t13.modbook.model.module.ModBookDate;
import t13.modbook.testutil.Assert;

public class JsonAdaptedModBookDateTest {
    private static final String INVALID_DATE = "12112021";
    private static final ModBookDate VALID_MODBOOKDATE = new ModBookDate("09/09/2021");

    @Test
    public void toModelType_validDate_returnsModBookDate() throws Exception {
        JsonAdaptedModBookDate date = new JsonAdaptedModBookDate(VALID_MODBOOKDATE);
        assertEquals(VALID_MODBOOKDATE, date.toModelType());
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedModBookDate date = new JsonAdaptedModBookDate(INVALID_DATE);
        String expectedMessage = ModBookDate.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }
}
