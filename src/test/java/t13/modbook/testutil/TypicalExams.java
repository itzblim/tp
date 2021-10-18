package t13.modbook.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import t13.modbook.model.module.exam.Exam;
import t13.modbook.testutil.builders.ExamBuilder;
import t13.modbook.testutil.builders.TimeslotBuilder;

/**
 * A utility class containing a list of {@code Exam} objects to be used in tests.
 */
public class TypicalExams {

    public static final Exam DEFAULT_EXAM = new ExamBuilder().build();
    public static final Exam PHYSICAL_FINALS = new ExamBuilder().withName("Finals").withDate("24/11/2021")
            .withTimeslot(new TimeslotBuilder().withStartTime("16:00").withEndTime("18:00").build())
            .withLink("profDamith.com").withVenue("COM1").build();
    public static final Exam PHYSICAL_FINALS_NO_LINK_NO_VENUE = new ExamBuilder()
            .withName("Finals").withDate("24/11/2021")
            .withTimeslot(new TimeslotBuilder().withStartTime("16:00").withEndTime("18:00").build()).build();
    public static final Exam PHYSICAL_MIDTERMS = new ExamBuilder().withName("Midterms").withDate("04/10/2021")
            .withTimeslot(new TimeslotBuilder().withStartTime("16:00").withEndTime("17:30").build())
            .withVenue("MPSH 2").build();
    public static final Exam ONLINE_FINALS = new ExamBuilder().withName("Finals").withDate("25/11/2021")
            .withTimeslot(new TimeslotBuilder().withStartTime("15:00").withEndTime("17:00").build())
            .withLink("finals.com").build();
    public static final Exam ONLINE_MIDTERMS = new ExamBuilder().withName("Midterms").withDate("05/10/2021")
            .withTimeslot(new TimeslotBuilder().withStartTime("16:00").withEndTime("17:30").build())
            .withLink("midterms.com").build();
    public static final Exam FUTURE_EXAM = new ExamBuilder().withDate("24/11/3000").build();
    public static final Exam PAST_EXAM = new ExamBuilder().withDate("24/11/1999").build();

    // CS2103T Exams for real-life examples
    public static final Exam CS2103T_PRACTICAL = new ExamBuilder().withName("CS2103T Practical").withDate("12/11/2021")
            .withTimeslot(new TimeslotBuilder().withStartTime("16:00").withEndTime("18:00").build())
            .withLink("practical.com").build();
    public static final Exam CS2103T_FINALS = new ExamBuilder().withName("CS2103T Finals").withDate("23/11/2021")
            .withTimeslot(new TimeslotBuilder().withStartTime("17:00").withEndTime("19:00").build())
            .withLink("final.com").build();

    public static List<Exam> getTypicalExams() {
        return new ArrayList<>(Arrays.asList(CS2103T_FINALS, CS2103T_PRACTICAL));
    }
}
