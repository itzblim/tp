package t13.modbook.model.module.lesson;

import static t13.modbook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import t13.modbook.model.module.Day;
import t13.modbook.model.module.Link;
import t13.modbook.model.module.Timeslot;
import t13.modbook.model.module.Venue;

public class Lesson implements Comparable<Lesson> {
    private final LessonName name;
    private final Day day;
    private final Timeslot timeslot;
    private final Optional<Venue> venue;
    private final Optional<Link> link;

    /**
     * Constructs a {@code Lesson}
     *
     * @param name     Name of lesson
     * @param day      Date of lesson
     * @param timeslot Timeslot of the lesson
     * @param venue    Optional venue for the lesson
     * @param link     Optional link for the lesson
     */
    public Lesson(LessonName name, Day day, Timeslot timeslot, Optional<Venue> venue, Optional<Link> link) {
        requireAllNonNull(name, day, timeslot, venue, link);
        this.name = name;
        this.day = day;
        this.timeslot = timeslot;
        this.venue = venue;
        this.link = link;
    }

    public LessonName getName() {
        return name;
    }

    public Day getDay() {
        return day;
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
     * Returns true if both Lessons have the same name.
     * This defines a weaker notion of equality between two Lessons.
     */
    public boolean isSameLesson(Lesson otherLesson) {
        if (otherLesson == this) {
            return true;
        }

        return otherLesson != null
                && otherLesson.getName().equals(getName());
    }

    /**
     * Returns a deepCopy of the Lesson object.
     */
    public Lesson deepCopy() {
        LessonName newLessonName = new LessonName(name.getLessonName());
        Timeslot newTimeslot = timeslot.deepCopy();
        Optional<Venue> newVenue = venue.map(venue -> new Venue(venue.getFullVenue()));
        Optional<Link> newLink = link.map(link -> new Link(link.getLink()));
        // day is enum, no clone method
        return new Lesson(newLessonName, day, newTimeslot, newVenue, newLink);
    }

    /**
     * Returns true if both Lessons have the same identity and data fields.
     * This defines a stronger notion of equality between two Lessons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return otherLesson.getName().equals(getName())
                && otherLesson.getDay().equals(getDay())
                && otherLesson.getTimeslot().equals(getTimeslot())
                && otherLesson.getVenue().equals(getVenue())
                && otherLesson.getLink().equals(getLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, day, timeslot, venue, link);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Day: ")
                .append(getDay())
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
     * Compares between two Lesson objects.
     * Will compare based on date - i.e. earlier date will be ordered first.
     * If dates are the same, will compare based on timeslot.
     *
     * @param otherLesson the Lesson to compare with
     * @return a negative integer, zero or a positive integer as this Lesson is before, at the same starting time
     * or after the given Lesson respectively.
     */
    @Override
    public int compareTo(Lesson otherLesson) {
        Day.DayComparator dayComp = new Day.DayComparator();
        int dateOrder = dayComp.compare(day, otherLesson.day);

        if (dateOrder == 0) {
            // Both lessons today
            if (day.isToday()) {
                // Current lesson finished, other lesson hasn't so this lesson is after other lesson
                if (timeslot.hasFinished() && !otherLesson.timeslot.hasFinished()) {
                    return 1;
                }
                // Current lesson hasn't finished, other lesson has so this lesson is before other lesson
                if (!timeslot.hasFinished() && otherLesson.timeslot.hasFinished()) {
                    return -1;
                }
                // Either both lessons have passed or not passed  compare the timeslots
                // optional, but left it here for clarity
                return timeslot.compareTo(otherLesson.timeslot);
            }
            return timeslot.compareTo(otherLesson.timeslot);
        }

        // this lesson today, other lesson another day
        // don't have to check if other lesson is not today because above case takes care of that already
        if (day.isToday()) {
            // if this lesson has finished, then it is now ordered after the other lesson
            if (timeslot.hasFinished()) {
                return 1;
            }
        }

        // other lesson today, this lesson another day
        if (otherLesson.day.isToday()) {
            // if other lesson has finished, then this lesson is now ordered before other lesson
            if (otherLesson.timeslot.hasFinished()) {
                return -1;
            }
        }
        return dateOrder;
    }
}
