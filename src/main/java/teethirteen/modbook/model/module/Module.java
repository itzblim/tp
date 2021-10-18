package teethirteen.modbook.model.module;

import static teethirteen.modbook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import teethirteen.modbook.model.module.exam.Exam;
import teethirteen.modbook.model.module.exam.exceptions.ExamNotFoundException;
import teethirteen.modbook.model.module.lesson.Lesson;

/**
 * Represents a Module in the ModBook.
 * Guarantees: immutable.
 */
public class Module {
    private final ModuleCode moduleCode;
    private final Optional<ModuleName> moduleName;
    private final List<Lesson> lessons;
    private final List<Exam> exams;

    /**
     * Constructs a {@code Module}
     *
     * @param moduleCode Code of module
     * @param moduleName Optional name of Module
     */
    public Module(ModuleCode moduleCode, Optional<ModuleName> moduleName) {
        requireAllNonNull(moduleCode, moduleName);
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.lessons = new ArrayList<>();
        this.exams = new ArrayList<>();
    }

    /**
     * Constructs a {@code Module} with existing lessons and exams
     *
     * @param moduleCode Code of module
     * @param moduleName Optional name of Module
     * @param lessons List of lessons
     * @param exams List of exams
     */
    public Module(ModuleCode moduleCode, Optional<ModuleName> moduleName,
                  List<Lesson> lessons, List<Exam> exams) {
        requireAllNonNull(moduleCode, moduleName, lessons, exams);
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.lessons = lessons;
        this.exams = exams;
    }

    public ModuleCode getCode() {
        return moduleCode;
    }

    public Optional<ModuleName> getName() {
        return moduleName;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<Exam> getExams() {
        return exams;
    }

    /**
     * Returns true if both modules have the same code.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameModule(Module otherModule) {
        return otherModule != null && moduleCode.equals(otherModule.getCode());
    }

    /**
     * Returns the very next Lesson that occurs.
     */
    public Lesson getNextLesson() {
        return Collections.min(lessons);
    }

    /**
     * Returns the very next Exam that occurs.
     *
     * @return Next exam that starts in the future
     * @throws ExamNotFoundException if no upcoming exam found
     */
    public Exam getNextExam() throws ExamNotFoundException {
        Collections.sort(exams);
        for (Exam exam : exams) {
            if (exam.isFuture()) {
                return exam;
            }
        }
        // No upcoming exam
        throw new ExamNotFoundException();
    }

    /**
     * Returns a deepCopy of the Module object.
     */
    public Module deepCopy() {
        ModuleCode newModCode = new ModuleCode(moduleCode.moduleCode);
        Optional<ModuleName> newModName = moduleName.map(modName -> new ModuleName(modName.getModuleName()));
        List<Lesson> newLessons = lessons.stream().map(Lesson::deepCopy).collect(Collectors.toList());
        List<Exam> newExams = exams.stream().map(Exam::deepCopy).collect(Collectors.toList());
        return new Module(newModCode, newModName, newLessons, newExams);
    }

    /**
     * Returns true if both modules have the same identity and attributes.
     * This defines a stronger notion of equality between two modules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Module)) {
            return false;
        }
        Module otherModule = (Module) other;
        return isSameModule(otherModule)
                && moduleName.equals(otherModule.getName())
                && lessons.equals(otherModule.getLessons())
                && exams.equals(otherModule.getExams());
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleCode, moduleName, lessons, exams);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCode());
        moduleName.ifPresent(name -> builder.append(String.format("; Name: %s", name)));
        if (!lessons.isEmpty()) {
            builder.append("; Lessons: ");
            lessons.forEach(lesson -> builder.append(String.format("[%s]", lesson)));
        }
        if (!exams.isEmpty()) {
            builder.append("; Exams: ");
            exams.forEach(exam -> builder.append(String.format("[%s]", exam)));
        }
        return builder.toString();
    }
}
