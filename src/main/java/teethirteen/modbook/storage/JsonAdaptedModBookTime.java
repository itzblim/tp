package teethirteen.modbook.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import teethirteen.modbook.commons.exceptions.IllegalValueException;
import teethirteen.modbook.model.module.ModBookTime;

/**
 * Jackson-friendly version of {@link ModBookTime}.
 */
public class JsonAdaptedModBookTime {

    private final String time;

    /**
     * Constructs a {@code JsonAdaptedModBookTime} with the given {@code timeString}
     */
    @JsonCreator
    public JsonAdaptedModBookTime(String timeString) {
        time = timeString;
    }

    /**
     * Converts a given {@code ModBookTime} into this class for Jackson use.
     */
    public JsonAdaptedModBookTime(ModBookTime source) {
        time = source.time.format(ModBookTime.PARSE_FORMATTERS[0]); // so that it can be parsed correctly
    }

    @JsonValue
    public String getTime() {
        return time;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ModBookTime} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ModBookTime.
     */
    public ModBookTime toModelType() throws IllegalValueException {
        if (!ModBookTime.isValidTime(time)) {
            throw new IllegalValueException(ModBookTime.MESSAGE_CONSTRAINTS);
        }
        return new ModBookTime(time);
    }
}
