package t13.modbook.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import t13.modbook.model.module.Module;
import t13.modbook.model.module.UniqueModuleList;
import t13.modbook.model.module.exam.Exam;
import t13.modbook.model.module.lesson.Lesson;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameModule comparison)
 */
public class ModBook implements ReadOnlyModBook {
    private final UniqueModuleList modules;


    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        modules = new UniqueModuleList();
    }

    public ModBook() {
    }

    /**
     * Creates an ModBook using the Modules in the {@code toBeCopied}
     */
    public ModBook(ReadOnlyModBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the address book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the address book.
     */
    public void setModules(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Resets the existing data of this {@code ModBook} with {@code newData}.
     */
    public void resetData(ReadOnlyModBook newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
    }

    //// module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a module to the address book.
     * The module must not already exist in the mod book.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Removes {@code module} from this {@code ModBook}.
     * {@code module} must exist in the mod book.
     */
    public void removeModule(Module module) {
        modules.remove(module);
    }

    /**
     * Removes Exam {@code target} from the Module {@code module}.
     * {@code target} must exist in {@code module}'s exams list.
     */
    public void removeExam(Module module, Exam target) {
        module.getExams().remove(target);
    }

    /**
     * Removes Lesson {@code target} from the Module {@code module}.
     * {@code target} must exist in {@code module}'s lessons list.
     */
    public void removeLesson(Module module, Lesson target) {
        module.getLessons().remove(target);
    }

    //// util methods

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModBook // instanceof handles nulls
                && modules.equals(((ModBook) other).modules));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
