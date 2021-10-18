package teethirteen.modbook.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import teethirteen.modbook.model.module.lesson.Lesson;
import teethirteen.modbook.testutil.builders.LessonBuilder;
import teethirteen.modbook.testutil.builders.TimeslotBuilder;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {

    public static final Lesson DEFAULT_LESSON = new LessonBuilder().build();
    public static final Lesson PHYSICAL_LECTURE = new LessonBuilder().withDefaultVenue().build();
    public static final Lesson ONLINE_LECTURE = new LessonBuilder().withLink("lecture.com").build();
    public static final Lesson PHYSICAL_TUTORIAL = new LessonBuilder().withName("Tutorial")
            .withDay("TUESDAY").withTimeslot(new TimeslotBuilder().withStartTime("14:00").withEndTime("15:00").build())
            .withVenue("COM1-0120").build();
    public static final Lesson ONLINE_TUTORIAL = new LessonBuilder().withName("Tutorial")
            .withDay("TUESDAY").withTimeslot(new TimeslotBuilder().withStartTime("14:00").withEndTime("15:00").build())
            .withLink("tutorial.com").build();

    // CS2103T lessons for some real life lessons
    public static final Lesson CS2103T_LECTURE_WITH_VENUE = new LessonBuilder().withName("CS2103T Lecture")
            .withDay("FRIDAY").withTimeslot(new TimeslotBuilder().withStartTime("16:00").withEndTime("18:00").build())
            .withLink("profDamith.com").withVenue("COM1").build();
    public static final Lesson CS2103T_LECTURE = new LessonBuilder().withName("CS2103T Lecture")
            .withDay("FRIDAY").withTimeslot(new TimeslotBuilder().withStartTime("16:00").withEndTime("18:00").build())
            .withLink("profDamith.com").build();
    public static final Lesson CS2103T_LECTURE_NO_LINK_NO_VENUE = new LessonBuilder().withName("CS2103T Lecture")
            .withDay("FRIDAY").withTimeslot(new TimeslotBuilder().withStartTime("16:00").withEndTime("18:00")
                    .build()).build();
    public static final Lesson CS2103T_TUTORIAL = new LessonBuilder().withName("CS2103T Tutorial")
            .withDay("THURSDAY").withTimeslot(new TimeslotBuilder().withStartTime("13:00").withEndTime("14:00").build())
            .withLink("danny.com").build();
    public static final Lesson CS2101_MON = new LessonBuilder().withName("CS2101 Monday")
            .withDay("MONDAY").withTimeslot(new TimeslotBuilder().withStartTime("08:00").withEndTime("10:00").build())
            .withLink("msSunita.com").build();
    public static final Lesson CS2101_THURS = new LessonBuilder().withName("CS2101 Thursday")
            .withDay("THURSDAY").withTimeslot(new TimeslotBuilder().withStartTime("08:00").withEndTime("10:00").build())
            .withLink("msSunita.com").build();

    private TypicalLessons() {} // prevents instantiation

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(CS2101_MON, CS2101_THURS, CS2103T_LECTURE, CS2103T_TUTORIAL));
    }
}
