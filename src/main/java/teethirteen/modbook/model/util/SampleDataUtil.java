package teethirteen.modbook.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import teethirteen.modbook.model.ModBook;
import teethirteen.modbook.model.ReadOnlyModBook;
import teethirteen.modbook.model.module.Day;
import teethirteen.modbook.model.module.Link;
import teethirteen.modbook.model.module.ModBookDate;
import teethirteen.modbook.model.module.ModBookTime;
import teethirteen.modbook.model.module.Module;
import teethirteen.modbook.model.module.ModuleCode;
import teethirteen.modbook.model.module.ModuleName;
import teethirteen.modbook.model.module.Timeslot;
import teethirteen.modbook.model.module.exam.Exam;
import teethirteen.modbook.model.module.exam.ExamName;
import teethirteen.modbook.model.module.lesson.Lesson;
import teethirteen.modbook.model.module.lesson.LessonName;

/**
 * Contains utility methods for populating {@code ModBook} with sample data.
 */
public class SampleDataUtil {

    /**
     * Returns an array of sample modules to be displayed if no other data is found.
     */
    public static Module[] getSampleModules() {
        return new Module[] {
            new Module(new ModuleCode("CS1231"), Optional.of(new ModuleName("Discrete Structures"))),
            new Module(new ModuleCode("CS2040S"), Optional.of(new ModuleName("Data Structures and Algorithms"))),
            new Module(new ModuleCode("CS2030S"), Optional.of(new ModuleName("Programming Methodology II"))),
            new Module(new ModuleCode("CS2100"), Optional.of(new ModuleName("Computer Organization"))),
            new Module(new ModuleCode("CS2103T"), Optional.of(new ModuleName("Software Engineering")),
                    Arrays.asList(
                            new Lesson(new LessonName("CS2103T Lecture"), Day.FRIDAY,
                                    new Timeslot(new ModBookTime("16:00"), new ModBookTime("18:00")),
                                    Optional.empty(), Optional.of(new Link("profDamith.com"))),
                            new Lesson(new LessonName("CS2103T Tutorial"), Day.THURSDAY,
                                    new Timeslot(new ModBookTime("13:00"), new ModBookTime("14:00")),
                                    Optional.empty(), Optional.of(new Link("danny.com")))
                    ),
                    Arrays.asList(
                            new Exam(new ExamName("CS2103T Practical"), new ModBookDate("12/11/2021"),
                                    new Timeslot(new ModBookTime("16:00"), new ModBookTime("18:00")),
                                    Optional.empty(), Optional.of(new Link("practical.com"))),
                            new Exam(new ExamName("CS2103T Finals"), new ModBookDate("23/11/2021"),
                                    new Timeslot(new ModBookTime("17:00"), new ModBookTime("19:00")),
                                    Optional.empty(), Optional.of(new Link("final.com")))
                    ))
        };
    }

    public static ReadOnlyModBook getSampleModBook() {
        ModBook sampleMb = new ModBook();
        for (Module sampleModule : getSampleModules()) {
            sampleMb.addModule(sampleModule);
        }
        return sampleMb;
    }

    /**
     * Returns a lesson list containing the list of lessons given.
     */
    public static List<Lesson> getLessonList(Lesson... lessons) {
        return Arrays.stream(lessons)
                .collect(Collectors.toList());
    }

    /**
     * Returns an exam list containing the list of exams given.
     */
    public static List<Exam> getExamList(Exam... exams) {
        return Arrays.stream(exams)
                .collect(Collectors.toList());
    }

}
