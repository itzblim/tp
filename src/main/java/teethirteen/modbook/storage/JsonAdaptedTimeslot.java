package teethirteen.modbook.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import teethirteen.modbook.commons.exceptions.IllegalValueException;
import teethirteen.modbook.model.module.ModBookTime;
import teethirteen.modbook.model.module.Timeslot;

/**
 * Jackson-friendly version of {@link Timeslot}
 */
public class JsonAdaptedTimeslot {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Timeslot's %s field is missing!";

    private final JsonAdaptedModBookTime startTime;
    private final JsonAdaptedModBookTime endTime;

    /**
     * Constructs a {@code JsonAdaptedTimeslot} with the given start and end times.
     */
    @JsonCreator
    public JsonAdaptedTimeslot(@JsonProperty("startTime") JsonAdaptedModBookTime startTime,
            @JsonProperty("endTime") JsonAdaptedModBookTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code Timeslot} into this class for Jackson use.
     */
    public JsonAdaptedTimeslot(Timeslot source) {
        startTime = new JsonAdaptedModBookTime(source.startTime);
        endTime = new JsonAdaptedModBookTime(source.endTime);
    }

    /**
     * Converts this Jackson-friendly adapted timeslot object into the model's {@code Timeslot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Timeslot.
     */
    public Timeslot toModelType() throws IllegalValueException {
        if (startTime == null || endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModBookTime.class.getSimpleName()));
        }
        ModBookTime start = startTime.toModelType();
        ModBookTime end = endTime.toModelType();

        if (!Timeslot.isValidTimeslot(start, end)) {
            throw new IllegalValueException(Timeslot.MESSAGE_CONSTRAINTS);
        }
        return new Timeslot(start, end);
    }
}
