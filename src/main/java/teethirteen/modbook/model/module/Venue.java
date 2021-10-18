package teethirteen.modbook.model.module;

import static java.util.Objects.requireNonNull;

import teethirteen.modbook.commons.util.AppUtil;

/**
 * Represents a Venue in the ModBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidVenue(String)}
 */
public class Venue {

    public static final String MESSAGE_CONSTRAINTS =
            "Venues should not be blank";

    /*
     * The first character of the venue must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^\\S.*$";

    public final String fullVenue;

    /**
     * Constructs a {@code Venue}.
     *
     * @param venue A valid venue.
     */
    public Venue(String venue) {
        requireNonNull(venue);
        AppUtil.checkArgument(isValidVenue(venue), MESSAGE_CONSTRAINTS);
        fullVenue = venue;
    }

    public String getFullVenue() {
        return fullVenue;
    }

    /**
     * Returns true if a given string is a valid venue.
     */
    public static boolean isValidVenue(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullVenue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Venue // instanceof handles nulls
                && fullVenue.equals(((Venue) other).fullVenue)); // state check
    }

    @Override
    public int hashCode() {
        return fullVenue.hashCode();
    }

}
