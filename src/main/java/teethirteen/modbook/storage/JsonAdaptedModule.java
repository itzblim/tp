package teethirteen.modbook.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import teethirteen.modbook.commons.exceptions.IllegalValueException;
import teethirteen.modbook.model.module.Module;
import teethirteen.modbook.model.module.ModuleCode;
import teethirteen.modbook.model.module.ModuleName;
import teethirteen.modbook.model.module.exam.Exam;
import teethirteen.modbook.model.module.lesson.Lesson;

/**
 * Jackson-friendly version of {@link Module}
 */
public class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String code;
    private final Optional<String> name;
    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();
    private final List<JsonAdaptedExam> exams = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("code") String code, @JsonProperty("name") Optional<String> name,
                           @JsonProperty("lessons") List<JsonAdaptedLesson> lessons,
                           @JsonProperty("exams") List<JsonAdaptedExam> exams) {
        this.code = code;
        this.name = name;
        if (lessons != null) {
            this.lessons.addAll(lessons);
        }
        if (exams != null) {
            this.exams.addAll(exams);
        }
    }

    /**
     * Constructs a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        code = source.getCode().moduleCode;
        name = source.getName().map(ModuleName::toString);
        this.lessons.addAll(source.getLessons().stream()
                .map(JsonAdaptedLesson::new)
                .collect(Collectors.toList()));
        this.exams.addAll(source.getExams().stream()
                .map(JsonAdaptedExam::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {
        if (code == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidModuleCode(code)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        final ModuleCode modelCode = new ModuleCode(code);

        Optional<ModuleName> modelName = Optional.empty();
        if (name != null && name.isPresent()) {
            if (!name.map(ModuleName::isValidModuleName).orElse(false)) {
                throw new IllegalValueException(ModuleName.MESSAGE_CONSTRAINTS);
            }
            modelName = name.map(ModuleName::new);
        }

        final List<Lesson> modelLessons = new ArrayList<>();
        for (JsonAdaptedLesson lesson : lessons) {
            modelLessons.add(lesson.toModelType());
        }

        final List<Exam> modelExams = new ArrayList<>();
        for (JsonAdaptedExam exam : exams) {
            modelExams.add(exam.toModelType());
        }

        return new Module(modelCode, modelName, modelLessons, modelExams);
    }

}
