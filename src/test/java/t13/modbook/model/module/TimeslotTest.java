package t13.modbook.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import t13.modbook.testutil.TestUtil;

class TimeslotTest {

    private static final ModBookTime START_TIME = new ModBookTime("09:00");
    private static final ModBookTime END_TIME = new ModBookTime("11:00");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Timeslot(START_TIME, null));
        assertThrows(NullPointerException.class, () -> new Timeslot(null, END_TIME));
        assertThrows(NullPointerException.class, () -> new Timeslot(null, null));
    }

    @Test
    public void constructor_invalidModBookTime_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Timeslot(END_TIME, START_TIME));
        assertThrows(IllegalArgumentException.class, () -> new Timeslot(START_TIME, START_TIME));
    }

    @Test
    void isValidTimeslot() {
        ModBookTime startTimeOneMinuteLater = new ModBookTime("09:01");

        // null ModBookTime
        assertThrows(NullPointerException.class, () -> Timeslot.isValidTimeslot(START_TIME, null));
        assertThrows(NullPointerException.class, () -> Timeslot.isValidTimeslot(null, END_TIME));
        assertThrows(NullPointerException.class, () -> Timeslot.isValidTimeslot(null, null));

        // invalid ModBookTime
        assertFalse(Timeslot.isValidTimeslot(START_TIME, START_TIME)); // same start and end time
        assertFalse(Timeslot.isValidTimeslot(END_TIME, START_TIME)); // end time before start time

        // valid ModBookTime
        assertTrue(Timeslot.isValidTimeslot(START_TIME, END_TIME));
        assertTrue(Timeslot.isValidTimeslot(START_TIME, startTimeOneMinuteLater));
    }

    @Test
    void compareTo() {
        ModBookTime startTimeTwo = new ModBookTime("09:30");
        ModBookTime endTimeTwo = new ModBookTime("11:30");

        // different startTime, same endTime
        assertTrue(TestUtil.isBefore(new Timeslot(START_TIME, END_TIME), new Timeslot(startTimeTwo, END_TIME)));
        assertFalse(TestUtil.isBefore(new Timeslot(startTimeTwo, endTimeTwo), new Timeslot(START_TIME, endTimeTwo)));

        // same startTime, different endTime
        assertTrue(TestUtil.isBefore(new Timeslot(START_TIME, END_TIME), new Timeslot(START_TIME, endTimeTwo)));
        assertFalse(TestUtil.isBefore(new Timeslot(startTimeTwo, endTimeTwo), new Timeslot(startTimeTwo, END_TIME)));

        // same start and end time
        assertFalse(TestUtil.isBefore(new Timeslot(START_TIME, END_TIME), new Timeslot(START_TIME, END_TIME)));
    }
}
