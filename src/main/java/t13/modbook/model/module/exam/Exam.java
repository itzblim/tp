package t13.modbook.model.module.exam;

import static t13.modbook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import t13.modbook.model.module.Link;
import t13.modbook.model.module.ModBookDate;
import t13.modbook.model.module.Timeslot;
import t13.modbook.model.module.Venue;
import t13.modbook.commons.util.CollectionUtil;

/**
 * Represents an exam in the ModBook.
 * Guarantees: immutable.
 */
public class Exam implements Comparable<Exam> {
    private final ExamName name;
    private final ModBookDate date;
    private final Timeslot timeslot;
    private final Optional<Venue> venue;
    private final Optional<Link> link;

    /**
     * Constructs a {@code Exam}
     *
     * @param name     Name of exam
     * @param date     Date of exam
     * @param timeslot Timeslot of the exam
     * @param venue    Optional venue for the exam
     * @param link     Optional link for the exam
     */
    public Exam(ExamName name, ModBookDate date, Timeslot timeslot, Optional<Venue> venue, Optional<Link> link) {
        CollectionUtil.requireAllNonNull(name, date, timeslot, venue, link);
        this.name = name;
        this.date = date;
        this.timeslot = timeslot;
        this.venue = venue;
        this.link = link;
    }

    public ExamName getName() {
        return name;
    }

    public ModBookDate getDate() {
        return date;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public Optional<Venue> getVenue() {
        return venue;
    }

    public Optional<Link> getLink() {
        return link;
    }

    /**
     * Checks if an exam is starting in the future.
     */
    public boolean isFuture() {
        return date.isFuture() || (date.isToday() && timeslot.isFuture());
    }

    /**
     * Returns a deepCopy of the Exam object.
     */
    public Exam deepCopy() {
        ExamName newExamName = new ExamName(name.getFullExamName());
        ModBookDate newModBookDate = date.deepCopy();
        Timeslot newTimeslot = timeslot.deepCopy();
        Optional<Venue> newVenue = venue.map(venue -> new Venue(venue.getFullVenue()));
        Optional<Link> newLink = link.map(link -> new Link(link.getLink()));
        return new Exam(newExamName, newModBookDate, newTimeslot, newVenue, newLink);
    }

    /**
     * Returns true if both exams have the same name.
     * This defines a weaker notion of equality between two exams.
     */
    public boolean isSameExam(Exam otherExam) {
        if (otherExam == this) {
            return true;
        }

        return otherExam != null
                && otherExam.getName().equals(getName());
    }

    /**
     * Returns true if both exams have the same identity and data fields.
     * This defines a stronger notion of equality between two exams.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Exam)) {
            return false;
        }

        Exam otherExam = (Exam) other;
        return otherExam.getName().equals(getName())
                && otherExam.getDate().equals(getDate())
                && otherExam.getTimeslot().equals(getTimeslot())
                && otherExam.getVenue().equals(getVenue())
                && otherExam.getLink().equals(getLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, timeslot, venue, link);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Date: ")
                .append(getDate())
                .append("; Timeslot: ")
                .append(getTimeslot());
        if (venue.isPresent()) {
            builder.append("; Venue: ")
                    .append(getVenue().get());
        }
        if (link.isPresent()) {
            builder.append("; Link: ")
                    .append(getLink().get());
        }
        return builder.toString();
    }

    /**
     * Compares between two Exam objects.
     * Will compare based on date - i.e. earlier date will be ordered first.
     * If dates are the same, will compare based on timeslot.
     *
     * @param otherExam the Exam to compare with
     * @return a negative integer, zero or a positive integer as this Exam is before, at the same starting time
     * or after the given Exam respectively.
     */
    @Override
    public int compareTo(Exam otherExam) {
        int dateOrder = date.compareTo(otherExam.date);
        if (dateOrder == 0) {
            return timeslot.compareTo(otherExam.timeslot);
        }
        return dateOrder;
    }
}
