package seedu.address.logic.commands.add;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModules.getTypicalModBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exam.Exam;
import seedu.address.model.module.lesson.Lesson;
import seedu.address.testutil.builders.ExamBuilder;
import seedu.address.testutil.builders.LessonBuilder;
import seedu.address.testutil.builders.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalModBook(), new UserPrefs());
    }

    @Test
    public void execute_newModule_success() {
        Module validModule = new ModuleBuilder().withCode("CS0202").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getModBook(), new UserPrefs());
        expectedModel.addModule(validModule);

        assertCommandSuccess(new AddModCommand(validModule), model,
                String.format(AddModCommand.MESSAGE_SUCCESS, validModule), expectedModel);
    }

    @Test
    public void execute_newLesson_success() {
        Lesson validLesson = new LessonBuilder().build();
        Module validModule = new ModuleBuilder().build();
        ModuleCode validModuleCode = validModule.getCode();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getModBook(), new UserPrefs());
        expectedModel.addLessonToModule(validModule, validLesson);

        assertCommandSuccess(new AddLessonCommand(validModuleCode, validLesson), model,
                String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson), expectedModel);
    }

    @Test
    public void execute_newExam_success() {
        Exam validExam = new ExamBuilder().build();
        Module validModule = new ModuleBuilder().build();
        ModuleCode validModuleCode = validModule.getCode();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getModBook(), new UserPrefs());
        expectedModel.addExamToModule(validModule, validExam);

        assertCommandSuccess(new AddExamCommand(validModuleCode, validExam), model,
                String.format(AddExamCommand.MESSAGE_SUCCESS, validExam), expectedModel);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module moduleInList = model.getModBook().getModuleList().get(0);
        assertCommandFailure(new AddModCommand(moduleInList), model, AddModCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_duplicateLesson_throwsCommandException() {
        Lesson lessonInList = model.getModBook().getModuleList().get(0).getLessons().get(0);
        ModuleCode moduleCode = model.getModBook().getModuleList().get(0).getCode();
        assertCommandFailure(new AddLessonCommand(moduleCode, lessonInList), model,
                AddLessonCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_duplicateExam_throwsCommandException() {
        Exam examInList = model.getModBook().getModuleList().get(0).getExams().get(0);
        ModuleCode moduleCode = model.getModBook().getModuleList().get(0).getCode();
        assertCommandFailure(new AddExamCommand(moduleCode, examInList), model, AddExamCommand.MESSAGE_DUPLICATE_EXAM);
    }
}
