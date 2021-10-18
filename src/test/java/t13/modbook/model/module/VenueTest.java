package t13.modbook.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static t13.modbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import t13.modbook.testutil.Assert;

public class VenueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Venue(null));
    }

    @Test
    public void constructor_invalidVenue_throwsIllegalArgumentException() {
        String invalidVenue = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Venue(invalidVenue));
    }

    @Test
    public void isValidVenue() {
        // null venue
        Assert.assertThrows(NullPointerException.class, () -> Venue.isValidVenue(null));

        // invalid venue
        assertFalse(Venue.isValidVenue("")); // empty string
        assertFalse(Venue.isValidVenue(" ")); // spaces only

        // valid venue
        assertTrue(Venue.isValidVenue("peter jack hall")); // lowercase alphabets only
        assertTrue(Venue.isValidVenue("298103")); // numbers only
        assertTrue(Venue.isValidVenue("mph hall 2")); // alphanumeric characters
        assertTrue(Venue.isValidVenue("University Sports Centre")); // with capital letters
        assertTrue(Venue.isValidVenue("National University of Singapore, School of Computing")); // long names
    }
}
