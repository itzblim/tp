package t13.modbook.model.module;

import static java.util.Objects.requireNonNull;
import static t13.modbook.commons.util.AppUtil.checkArgument;

/**
 * Represents a link in the ModBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidLink(String)}
 */
public class Link {

    public static final String MESSAGE_CONSTRAINTS = "Link can take any values, and it should not be blank";

    /*
     * The first character of the link must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String link;

    /**
     * Constructs a {@code Link}.
     *
     * @param link A link.
     */
    public Link(String link) {
        requireNonNull(link);
        checkArgument(isValidLink(link), MESSAGE_CONSTRAINTS);
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    /**
     * Returns true if a given string is a valid link.
     */
    public static boolean isValidLink(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.link;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Link // instanceof handles nulls
                && link.equals(((Link) other).link)); // state check
    }

    @Override
    public int hashCode() {
        return link.hashCode();
    }
}
