package teethirteen.modbook.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import teethirteen.modbook.commons.core.GuiSettings;
import teethirteen.modbook.logic.commands.exceptions.CommandException;
import teethirteen.modbook.model.module.Module;
import teethirteen.modbook.model.module.ModuleCode;
import teethirteen.modbook.model.module.exam.Exam;
import teethirteen.modbook.model.module.lesson.Lesson;

/**
 * The API of the Model component.
 */
public interface Model {

    /** {@code Predicate} that always evaluate to true */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' mod book file path.
     */
    Path getModBookFilePath();

    /**
     * Sets the user prefs' mod book file path.
     */
    void setModBookFilePath(Path modBookFilePath);

    /**
     * Replaces mod book data with the data in {@code modBook}.
     */
    void setModBook(ReadOnlyModBook modBook);

    /** Returns the ModBook */
    ReadOnlyModBook getModBook();

    /**
     * Returns true if a module with the same identity as {@code module} exists in the mod book.
     */
    boolean hasModule(Module module);

    /**
     * Deletes the given module.
     * The module must exist in the mod book.
     */
    void deleteModule(Module module);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the mod book.
     */
    void addModule(Module module);

    /**
     * Gets the requested module based on given modCode.
     * @param modCode Used to find module.
     * @return Module.
     * @throws CommandException If module does not exist.
     */
    Module getModule(ModuleCode modCode) throws CommandException;

    /**
     * Deletes the Exam from the specified module's lessons list.
     */
    void deleteExam(Module module, Exam target);

    /**
     * Deletes the Lesson from the specified module's lessons list.
     */
    void deleteLesson(Module module, Lesson target);

    /**
     * Checks if a module has the lesson
     */
    boolean moduleHasLesson(Module module, Lesson lesson);

    /**
     * Adds a lesson to a module.
     */
    void addLessonToModule(Module module, Lesson lesson);

    /**
     * Checks if a module has the lesson
     */
    boolean moduleHasExam(Module module, Exam exam);

    /**
     * Adds a lesson to a module.
     */
    void addExamToModule(Module module, Exam exam);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the mod book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the mod book.
     */
    void setModule(Module target, Module editedModule);

    /** Returns an unmodifiable view of the filtered module list */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);
}
