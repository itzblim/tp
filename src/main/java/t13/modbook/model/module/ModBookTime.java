package t13.modbook.model.module;

import static java.util.Objects.requireNonNull;
import static t13.modbook.commons.util.AppUtil.checkArgument;
import static t13.modbook.commons.util.DateTimeUtil.buildFormatter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a time in the ModBook.
 * Guarantees: immutable.
 */
public class ModBookTime implements Comparable<ModBookTime> {
    public static final String MESSAGE_CONSTRAINTS =
            "Invalid time format. Please refer to the User Guide for valid time formats.";
    public static final DateTimeFormatter[] PARSE_FORMATTERS = new DateTimeFormatter[] {
            buildFormatter("HH:mm"), // 09:30, 14:30
            buildFormatter("HH.mm"), // 09.30, 14.30
            buildFormatter("HHmm"), // 0930, 1430
            buildFormatter("ha"), // 9AM, 4PM
            buildFormatter("hha"), // 11AM, 04PM
            buildFormatter("h:mma"), // 9:30AM, 4:00PM
            buildFormatter("hh:mma"), // 11:30AM, 04:00PM
            buildFormatter("h.mma"), // 9.30AM, 4.00PM
            buildFormatter("hh.mma") // 11.30AM, 04.00PM
    };
    public static final DateTimeFormatter PRINT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public final LocalTime time;

    /**
     * Constructs a {@code ModBookTime}
     *
     * @param time the string representing time of the ModBookTime
     */
    public ModBookTime(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.time = parseTime(time);
    }

    private ModBookTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Checks if ModBookTime is in the future
     */
    public boolean isFuture() {
        return time.compareTo(LocalTime.now()) > 0;
    }

    public ModBookTime deepCopy() {
        return new ModBookTime(time);
    }

    /**
     * Returns true if a given string is a valid time
     */
    public static boolean isValidTime(String test) {
        requireNonNull(test);
        return parseTime(test) != null;
    }

    /**
     * Tries to parse a given string with various DateTimeFormatters.
     * Returns a LocalTime if parsing was successful,  null otherwise.
     */
    public static LocalTime parseTime(String test) {
        requireNonNull(test);
        LocalTime result = null;
        for (DateTimeFormatter f : PARSE_FORMATTERS) {
            try {
                result = LocalTime.parse(test, f);
                break; // short circuit once valid formatter is found
            } catch (DateTimeParseException e) {
                // do nothing
            }
        }
        return result;
    }

    /**
     * Returns true if the ModBookTime is before the current time.
     */
    public boolean beforeNow() {
        return time.compareTo((LocalTime.now())) < 0;
    }

    @Override
    public String toString() {
        return time.format(PRINT_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModBookTime // instanceof handles nulls
                && time.equals(((ModBookTime) other).time)); // state check
    }

    /**
     * Compares between two ModBookTime objects.
     *
     * @param other the ModBookTime to compare with
     * @return a negative integer, zero or a positive integer as this ModBookTime is before, at the same time
     * or after the given ModBookTime respectively.
     */
    @Override
    public int compareTo(ModBookTime other) {
        return time.compareTo(other.time);
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }
}
