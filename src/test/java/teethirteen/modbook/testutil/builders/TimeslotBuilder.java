package teethirteen.modbook.testutil.builders;

import teethirteen.modbook.model.module.ModBookTime;
import teethirteen.modbook.model.module.Timeslot;

/**
 * A utility class to help with building Timeslot objects.
 */
public class TimeslotBuilder {

    public static final String DEFAULT_START_TIME = "09:00";
    public static final String DEFAULT_END_TIME = "11:00";

    private ModBookTime startTime;
    private ModBookTime endTime;

    /**
     * Creates a {@code TimeslotBuilder} with the default details.
     */
    public TimeslotBuilder() {
        startTime = new ModBookTime(DEFAULT_START_TIME);
        endTime = new ModBookTime(DEFAULT_END_TIME);
    }

    /**
     * Initialises the TimeslotBuilder with the data of {@code timeslotToCopy}.
     */
    public TimeslotBuilder(Timeslot timeslotToCopy) {
        startTime = timeslotToCopy.startTime;
        endTime = timeslotToCopy.endTime;
    }

    /**
     * Sets the start time of the {@code Timeslot} that we are building.
     */
    public TimeslotBuilder withStartTime(String startTime) {
        this.startTime = new ModBookTime(startTime);
        return this;
    }

    /**
     * Sets the end time of the {@code Timeslot} that we are building.
     */
    public TimeslotBuilder withEndTime(String endTime) {
        this.endTime = new ModBookTime(endTime);
        return this;
    }

    public Timeslot build() {
        return new Timeslot(startTime, endTime);
    }
}
