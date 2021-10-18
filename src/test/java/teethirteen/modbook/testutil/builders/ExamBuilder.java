package teethirteen.modbook.testutil.builders;

import java.util.Optional;

import teethirteen.modbook.model.module.Link;
import teethirteen.modbook.model.module.ModBookDate;
import teethirteen.modbook.model.module.Timeslot;
import teethirteen.modbook.model.module.Venue;
import teethirteen.modbook.model.module.exam.Exam;
import teethirteen.modbook.model.module.exam.ExamName;

/**
 * A utility class to help with building Exam objects.
 */
public class ExamBuilder {

    public static final String DEFAULT_NAME = "Finals";
    public static final String DEFAULT_DATE = "09/12/2021";
    public static final Timeslot DEFAULT_TIMESLOT = new TimeslotBuilder().build();
    public static final Optional<String> DEFAULT_VENUE = Optional.of("LT17");
    public static final Optional<String> DEFAULT_LINK = Optional.of("google.com");

    private ExamName name;
    private ModBookDate date;
    private Timeslot timeslot;
    private Optional<Venue> venue;
    private Optional<Link> link;

    /**
     * Creates an {@code ExamBuilder} with the default details.
     * Note that the default ExamBuilder will not have a Venue nor a Link.
     */
    public ExamBuilder() {
        name = new ExamName(DEFAULT_NAME);
        date = new ModBookDate(DEFAULT_DATE);
        timeslot = DEFAULT_TIMESLOT;
        venue = Optional.empty();
        link = Optional.empty();
    }

    /**
     * Initialises the ExamBuilder with the data of {@code examToCopy}.
     */
    public ExamBuilder(Exam examToCopy) {
        name = examToCopy.getName();
        date = examToCopy.getDate();
        timeslot = examToCopy.getTimeslot();
        venue = examToCopy.getVenue();
        link = examToCopy.getLink();
    }

    /**
     * Adds the default {@code Venue} to the {@code Exam} that we are building
     */
    public ExamBuilder withDefaultVenue() {
        this.venue = DEFAULT_VENUE.map(Venue::new);
        return this;
    }

    /**
     * Adds the default {@code Link} to the {@code Exam} that we are building
     */
    public ExamBuilder withDefaultLink() {
        this.link = DEFAULT_LINK.map(Link::new);
        return this;
    }

    /**
     * Sets the {@code ExamName} to the {@code Exam} that we are building.
     */
    public ExamBuilder withName(String name) {
        this.name = new ExamName(name);
        return this;
    }

    /**
     * Sets the {@code ModBookDate} of the {@code Exam} that we are building.
     */
    public ExamBuilder withDate(String date) {
        this.date = new ModBookDate(date);
        return this;
    }

    /**
     * Sets the {@code Timeslot} of the {@code Exam} that we are building.
     */
    public ExamBuilder withTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code Exam} that we are building.
     */
    public ExamBuilder withVenue(String venue) {
        this.venue = Optional.of(new Venue(venue));
        return this;
    }

    /**
     * Sets the {@code Link} of the {@code Exam} that we are building.
     */
    public ExamBuilder withLink(String link) {
        this.link = Optional.of(new Link(link));
        return this;
    }

    public Exam build() {
        return new Exam(name, date, timeslot, venue, link);
    }
}
