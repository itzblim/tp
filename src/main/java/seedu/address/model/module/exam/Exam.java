package seedu.address.model.module.exam;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.module.Link;
import seedu.address.model.module.ModBookDate;
import seedu.address.model.module.Timeslot;
import seedu.address.model.module.Venue;

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
     * @param name Name of exam
     * @param date Date of exam
     * @param timeslot Timeslot of the exam
     * @param venue Optional venue for the exam
     * @param link Optional link for the exam
     */
    public Exam(ExamName name, ModBookDate date, Timeslot timeslot, Optional<Venue> venue, Optional<Link> link) {
        requireAllNonNull(name, date, timeslot, venue, link);
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
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Exam)) {
            return false;
        }

        Exam otherPerson = (Exam) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getDate().equals(getDate())
                && otherPerson.getTimeslot().equals(getTimeslot())
                && otherPerson.getVenue().equals(getVenue())
                && otherPerson.getLink().equals(getLink());
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
                    .append(getVenue());
        }
        if (link.isPresent()) {
            builder.append("; Link: ")
                    .append(getLink());
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
     *         or after the given Exam respectively.
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