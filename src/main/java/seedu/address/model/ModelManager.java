package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exam.Exam;
import seedu.address.model.module.lesson.Lesson;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    public static final String MESSAGE_MODULE_DOESNT_EXIST = "The module you chose does not exist";
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final ModBook modBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Module> filteredModules;

    /**
     * Initializes a ModelManager with the given addressBook, modBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyModBook modBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, modBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and modBook " + modBook
                + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.modBook = new ModBook(modBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.filteredModules = new FilteredList<>(this.modBook.getModuleList());
    }

    public ModelManager() {
        this(new AddressBook(), new ModBook(), new UserPrefs());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
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

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
        return addressBook.equals(other.addressBook)
                && modBook.equals(other.modBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
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
        requireAllNonNull(target, editedModule);
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
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
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
