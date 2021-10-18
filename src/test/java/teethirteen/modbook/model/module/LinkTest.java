package teethirteen.modbook.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import teethirteen.modbook.testutil.Assert;

public class LinkTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Link(null));
    }

    @Test
    public void constructor_invalidLink_throwsIllegalArgumentException() {
        String invalidLink = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Link(invalidLink));
    }

    @Test
    public void isValidLink() {
        // null Link
        Assert.assertThrows(NullPointerException.class, () -> Link.isValidLink(null));

        // invalid Link
        assertFalse(Link.isValidLink("")); // empty string
        assertFalse(Link.isValidLink(" ")); // spaces only

        // valid Link
        assertTrue(Link.isValidLink("https://www.youtube.com/watch?v=dE1P4zDhhqw&t=1s"));
        assertTrue(Link.isValidLink("-")); // one character
    }
}
