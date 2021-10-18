package teethirteen.modbook.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import teethirteen.modbook.commons.core.GuiSettings;
import teethirteen.modbook.commons.core.LogsCenter;
import teethirteen.modbook.commons.util.CollectionUtil;
import teethirteen.modbook.logic.commands.exceptions.CommandException;
import teethirteen.modbook.model.module.Module;
import teethirteen.modbook.model.module.ModuleCode;
import teethirteen.modbook.model.module.exam.Exam;
import teethirteen.modbook.model.module.lesson.Lesson;

/**
 * Represents the in-memory model of the mod book data.
 */
public class ModelManager implements Model {
    public static final String MESSAGE_MODULE_DOESNT_EXIST = "The module you chose does not exist";
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ModBook modBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Module> filteredModules;

    /**
     * Initializes a ModelManager with the given modBook and userPrefs.
     */
    public ModelManager(ReadOnlyModBook modBook, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(modBook, userPrefs);

        logger.fine("Initializing with modBook " + modBook + " and user prefs " + userPrefs);

        this.modBook = new ModBook(modBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredModules = new FilteredList<>(this.modBook.getModuleList());
    }

    public ModelManager() {
        this(new ModBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getModBookFilePath() {
        return userPrefs.getModBookFilePath();
    }

    @Override
    public void setModBookFilePath(Path modBookFilePath) {
        requireNonNull(modBookFilePath);
        userPrefs.setModBookFilePath(modBookFilePath);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return modBook.equals(other.modBook)
                && userPrefs.equals(other.userPrefs);
    }

    //=========== ModBook ================================================================================

    @Override
    public void setModBook(ReadOnlyModBook modBook) {
        this.modBook.resetData(modBook);
    }

    @Override
    public ReadOnlyModBook getModBook() {
        return modBook;
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modBook.hasModule(module);
    }

    @Override
    public void deleteModule(Module target) {
        modBook.removeModule(target);
    }

    @Override
    public void addModule(Module module) {
        modBook.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        CollectionUtil.requireAllNonNull(target, editedModule);
        modBook.setModules(target, editedModule);
    }

    @Override
    public Module getModule(ModuleCode modCode) throws CommandException {
        Optional<Module> module = this.filteredModules.stream().filter(mod ->
                mod.getCode().equals(modCode)).findAny();
        if (module.isEmpty()) {
            throw new CommandException(MESSAGE_MODULE_DOESNT_EXIST);
        }
        return module.get();
    }

    @Override
    public void deleteExam(Module module, Exam target) {
        modBook.removeExam(module, target);
    }

    @Override
    public void deleteLesson(Module module, Lesson target) {
        modBook.removeLesson(module, target);
    }

    @Override
    public boolean moduleHasLesson(Module module, Lesson lesson) {
        List<Lesson> lessons = module.getLessons();
        return lessons.contains(lesson);
    }

    @Override
    public void addLessonToModule(Module module, Lesson lesson) {
        module.getLessons().add(lesson);
    }

    @Override
    public boolean moduleHasExam(Module module, Exam exam) {
        List<Exam> exams = module.getExams();
        return exams.contains(exam);
    }

    @Override
    public void addExamToModule(Module module, Exam exam) {
        module.getExams().add(exam);
    }

    //=========== Filtered Module List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedModBook}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }
}
