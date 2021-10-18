package teethirteen.modbook.testutil.builders;

import java.util.Optional;

import teethirteen.modbook.model.module.Day;
import teethirteen.modbook.model.module.Link;
import teethirteen.modbook.model.module.Timeslot;
import teethirteen.modbook.model.module.Venue;
import teethirteen.modbook.model.module.lesson.Lesson;
import teethirteen.modbook.model.module.lesson.LessonName;

/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_NAME = "Lecture";
    public static final String DEFAULT_DAY = "MONDAY";
    public static final Timeslot DEFAULT_TIMESLOT = new TimeslotBuilder().build();
    public static final Optional<String> DEFAULT_VENUE = Optional.of("LT17");
    public static final Optional<String> DEFAULT_LINK = Optional.of("google.com");

    private LessonName name;
    private Day day;
    private Timeslot timeslot;
    private Optional<Venue> venue;
    private Optional<Link> link;

    /**
     * Creates a {@code LessonBuilder} with the default details.
     * Note that the default LessonBuilder will not have a Venue nor a Link.
     */
    public LessonBuilder() {
        name = new LessonName(DEFAULT_NAME);
        day = Day.valueOf(DEFAULT_DAY);
        timeslot = DEFAULT_TIMESLOT;
        venue = Optional.empty();
        link = Optional.empty();
    }

    /**
     * Initialises the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        name = lessonToCopy.getName();
        day = lessonToCopy.getDay();
        timeslot = lessonToCopy.getTimeslot();
        venue = lessonToCopy.getVenue();
        link = lessonToCopy.getLink();
    }

    /**
     * Adds the default {@code Venue} to the {@code Lesson} that we are building
     */
    public LessonBuilder withDefaultVenue() {
        this.venue = DEFAULT_VENUE.map(Venue::new);
        return this;
    }

    /**
     * Adds the default {@code Link} to the {@code Lesson} that we are building
     */
    public LessonBuilder withDefaultLink() {
        this.link = DEFAULT_LINK.map(Link::new);
        return this;
    }

    /**
     * Sets the {@code LessonName} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withName(String name) {
        this.name = new LessonName(name);
        return this;
    }

    /**
     * Sets the {@code Day} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withDay(String day) {
        this.day = Day.valueOf(day.toUpperCase());
        return this;
    }

    /**
     * Sets the {@code Timeslot} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withVenue(String venue) {
        this.venue = Optional.of(new Venue(venue));
        return this;
    }

    /**
     * Sets the {@code Link} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withLink(String link) {
        this.link = Optional.of(new Link(link));
        return this;
    }

    public Lesson build() {
        return new Lesson(name, day, timeslot, venue, link);
    }
}
