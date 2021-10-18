package t13.modbook.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import t13.modbook.commons.exceptions.IllegalValueException;
import t13.modbook.model.module.ModBookDate;

/**
 * Jackson-friendly version of {@link ModBookDate}.
 */
public class JsonAdaptedModBookDate {

    private final String date;

    /**
     * Constructs a {@code JsonAdaptedModBookDate} with the given {@code timeString}
     */
    @JsonCreator
    public JsonAdaptedModBookDate(String dateString) {
        date = dateString;
    }

    /**
     * Converts a given {@code ModBookDate} into this class for Jackson use.
     */
    public JsonAdaptedModBookDate(ModBookDate source) {
        date = source.date.format(ModBookDate.PARSE_FORMATTER); // in parsing format so that it can be parsed correctly
    }

    @JsonValue
    public String getTime() {
        return date;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ModBookDate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ModBookDate.
     */
    public ModBookDate toModelType() throws IllegalValueException {
        if (!ModBookDate.isValidDate(date)) {
            throw new IllegalValueException(ModBookDate.MESSAGE_CONSTRAINTS);
        }
        return new ModBookDate(date);
    }
}
