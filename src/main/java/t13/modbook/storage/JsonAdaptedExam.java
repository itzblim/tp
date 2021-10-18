package t13.modbook.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import t13.modbook.commons.exceptions.IllegalValueException;
import t13.modbook.model.module.Link;
import t13.modbook.model.module.ModBookDate;
import t13.modbook.model.module.Timeslot;
import t13.modbook.model.module.Venue;
import t13.modbook.model.module.exam.Exam;
import t13.modbook.model.module.exam.ExamName;

/**
 * Jackson-friendly version of {@link Exam}
 */
public class JsonAdaptedExam {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Exam's %s field is missing!";

    private final String name;
    private final JsonAdaptedModBookDate date;
    private final JsonAdaptedTimeslot timeslot;
    private final Optional<String> venue;
    private final Optional<String> link;

    /**
     * Constructs a {@code JsonAdaptedExam} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedExam(@JsonProperty("name") String name, @JsonProperty("date") JsonAdaptedModBookDate date,
            @JsonProperty("timeslot") JsonAdaptedTimeslot timeslot, @JsonProperty("venue") Optional<String> venue,
            @JsonProperty("link") Optional<String> link) {
        this.name = name;
        this.date = date;
        this.timeslot = timeslot;
        this.venue = venue;
        this.link = link;
    }

    /**
     * Constructs a given {@code Exam} into this class for Jackson use.
     */
    public JsonAdaptedExam(Exam source) {
        name = source.getName().fullExamName;
        date = new JsonAdaptedModBookDate(source.getDate());
        timeslot = new JsonAdaptedTimeslot(source.getTimeslot());
        venue = source.getVenue().map(Venue::toString);
        link = source.getLink().map(Link::toString);
    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's {@code Exam} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Exam toModelType() throws IllegalValueException {
        Optional<Venue> modelVenue = Optional.empty();
        Optional<Link> modelLink = Optional.empty();

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExamName.class.getSimpleName()));
        }
        if (!ExamName.isValidExamName(name)) {
            throw new IllegalValueException(ExamName.MESSAGE_CONSTRAINTS);
        }
        final ExamName modelName = new ExamName(name);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModBookDate.class.getSimpleName()));
        }
        final ModBookDate modelDate = date.toModelType();

        if (timeslot == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Timeslot.class.getSimpleName()));
        }
        final Timeslot modelTimeslot = timeslot.toModelType();

        if (venue != null && venue.isPresent()) {
            if (!venue.map(Venue::isValidVenue).orElse(false)) {
                throw new IllegalValueException(Venue.MESSAGE_CONSTRAINTS);
            }
            modelVenue = venue.map(Venue::new);
        }

        if (link != null && link.isPresent()) {
            if (!link.map(Link::isValidLink).orElse(false)) {
                throw new IllegalValueException(Link.MESSAGE_CONSTRAINTS);
            }
            modelLink = link.map(Link::new);
        }

        return new Exam(modelName, modelDate, modelTimeslot, modelVenue, modelLink);
    }
}
