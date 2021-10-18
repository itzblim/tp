package teethirteen.modbook.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static teethirteen.modbook.testutil.TypicalExams.FUTURE_EXAM;
import static teethirteen.modbook.testutil.TypicalExams.PAST_EXAM;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import teethirteen.modbook.model.module.exam.Exam;
import teethirteen.modbook.model.module.exam.exceptions.ExamNotFoundException;

public class ModuleTest {
    private static final ModuleCode MODULE_CODE = new ModuleCode("CS2103T");
    private static final Optional<ModuleName> MODULE_NAME = Optional.of(new ModuleName("Software Engineering"));
    private static final Module MODULE = new Module(MODULE_CODE, MODULE_NAME);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Module(null, null));
        assertThrows(NullPointerException.class, () -> new Module(MODULE_CODE, null));
        assertThrows(NullPointerException.class, () -> new Module(null, MODULE_NAME));
        assertThrows(NullPointerException.class, () -> new Module(MODULE_CODE, null,
                new ArrayList<>(), new ArrayList<>()));
        assertThrows(NullPointerException.class, () -> new Module(MODULE_CODE, null,
                new ArrayList<>(), new ArrayList<>()));
        assertThrows(NullPointerException.class, () -> new Module(MODULE_CODE, MODULE_NAME,
                new ArrayList<>(), null));
        assertThrows(NullPointerException.class, () -> new Module(MODULE_CODE, MODULE_NAME,
                null, new ArrayList<>()));
    }

    @Test
    void isSameModule() {
        // Same object
        assertTrue(MODULE.isSameModule(MODULE));

        // Different object, same code
        assertTrue(MODULE.isSameModule(new Module(MODULE_CODE, MODULE_NAME)));

        // Different code
        assertFalse(MODULE.isSameModule(new Module(new ModuleCode("CS2103"), MODULE_NAME)));
    }

    @Test
    void getNextExam() {
        // Empty exam list
        ArrayList<Exam> exams = new ArrayList<>();
        assertThrows(ExamNotFoundException.class, () -> new Module(MODULE_CODE, MODULE_NAME,
                new ArrayList<>(), exams).getNextExam());

        // Exam list only has an exam that already occurred
        exams.add(PAST_EXAM);
        assertThrows(ExamNotFoundException.class, () -> new Module(MODULE_CODE, MODULE_NAME,
                new ArrayList<>(), exams).getNextExam());

        // Exam list has a future exam
        exams.add(FUTURE_EXAM);
        assertEquals(new Module(MODULE_CODE, MODULE_NAME, new ArrayList<>(), exams).getNextExam(), FUTURE_EXAM);
    }
}
