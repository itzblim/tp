package teethirteen.modbook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static teethirteen.modbook.storage.JsonAdaptedModule.MISSING_FIELD_MESSAGE_FORMAT;
import static teethirteen.modbook.testutil.TypicalModules.CS2103T;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import teethirteen.modbook.commons.exceptions.IllegalValueException;
import teethirteen.modbook.model.module.Module;
import teethirteen.modbook.model.module.ModuleCode;
import teethirteen.modbook.model.module.ModuleName;
import teethirteen.modbook.model.module.exam.Exam;
import teethirteen.modbook.model.module.lesson.Lesson;
import teethirteen.modbook.testutil.Assert;

public class JsonAdaptedModuleTest {
    private static final String INVALID_CODE = "CS421S";
    private static final Optional<String> INVALID_NAME = Optional.of(" ");

    private static final String VALID_CODE = CS2103T.getCode().toString();
    private static final Optional<String> VALID_NAME = CS2103T.getName().map(ModuleName::toString);
    private static final List<JsonAdaptedLesson> VALID_LESSONS = CS2103T.getLessons().stream()
            .map(JsonAdaptedLesson::new).collect(Collectors.toList());
    private static final List<JsonAdaptedExam> VALID_EXAMS = CS2103T.getExams().stream()
            .map(JsonAdaptedExam::new).collect(Collectors.toList());
    private static final Optional<String> EMPTY_NAME = Optional.empty();
    private static final List<JsonAdaptedLesson> EMPTY_LESSONS = new ArrayList<>();
    private static final List<JsonAdaptedExam> EMPTY_EXAMS = new ArrayList<>();

    @Test
    public void toModelType_validModuleDetails_returnsModule() throws Exception {
        JsonAdaptedModule module = new JsonAdaptedModule(CS2103T);
        assertEquals(CS2103T, module.toModelType());
    }

    @Test
    public void toModelType_invalidCode_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(INVALID_CODE, VALID_NAME, VALID_LESSONS, VALID_EXAMS);
        String expectedMessage = ModuleCode.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullCode_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(null, VALID_NAME, VALID_LESSONS, VALID_EXAMS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ModuleCode.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_CODE, INVALID_NAME, VALID_LESSONS, VALID_EXAMS);
        String expectedMessage = ModuleName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_emptyName_success() {
        try {
            JsonAdaptedModule module =
                    new JsonAdaptedModule(VALID_CODE, EMPTY_NAME, VALID_LESSONS, VALID_EXAMS);
            Module readModule = module.toModelType();
            assertEquals(Optional.empty(), readModule.getName());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void toModelType_nullName_success() {
        try {
            JsonAdaptedModule module =
                    new JsonAdaptedModule(VALID_CODE, null, VALID_LESSONS, VALID_EXAMS);
            Module readModule = module.toModelType();
            assertEquals(Optional.empty(), readModule.getName());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void toModelType_nullLessons_success() {
        try {
            JsonAdaptedModule module =
                    new JsonAdaptedModule(VALID_CODE, VALID_NAME, null, VALID_EXAMS);
            Module readModule = module.toModelType();
            assertEquals(new ArrayList<Lesson>(), readModule.getLessons());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void toModelType_emptyLessons_success() {
        try {
            JsonAdaptedModule module =
                    new JsonAdaptedModule(VALID_CODE, VALID_NAME, EMPTY_LESSONS, VALID_EXAMS);
            Module readModule = module.toModelType();
            assertEquals(new ArrayList<Lesson>(), readModule.getLessons());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void toModelType_nullExams_success() {
        try {
            JsonAdaptedModule module =
                    new JsonAdaptedModule(VALID_CODE, VALID_NAME, VALID_LESSONS, null);
            Module readModule = module.toModelType();
            assertEquals(new ArrayList<Exam>(), readModule.getExams());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void toModelType_emptyExams_success() {
        try {
            JsonAdaptedModule module =
                    new JsonAdaptedModule(VALID_CODE, VALID_NAME, VALID_LESSONS, EMPTY_EXAMS);
            Module readModule = module.toModelType();
            assertEquals(new ArrayList<Exam>(), readModule.getExams());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

}
