package t13.modbook.logic.commands.add;

import static t13.modbook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import t13.modbook.model.Model;
import t13.modbook.model.ModelManager;
import t13.modbook.model.UserPrefs;
import t13.modbook.model.module.Module;
import t13.modbook.model.module.ModuleCode;
import t13.modbook.model.module.exam.Exam;
import t13.modbook.model.module.lesson.Lesson;
import t13.modbook.testutil.builders.ExamBuilder;
import t13.modbook.testutil.builders.LessonBuilder;
import t13.modbook.testutil.builders.ModuleBuilder;
import t13.modbook.logic.commands.CommandTestUtil;
import t13.modbook.testutil.TypicalModules;
import t13.modbook.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), TypicalModules.getTypicalModBook(), new UserPrefs());
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

        CommandTestUtil.assertCommandSuccess(new AddLessonCommand(validModuleCode, validLesson), model,
                String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson), expectedModel);
    }

    @Test
    public void execute_newExam_success() {
        Exam validExam = new ExamBuilder().build();
        Module validModule = new ModuleBuilder().build();
        ModuleCode validModuleCode = validModule.getCode();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getModBook(), new UserPrefs());
        expectedModel.addExamToModule(validModule, validExam);

        CommandTestUtil.assertCommandSuccess(new AddExamCommand(validModuleCode, validExam), model,
                String.format(AddExamCommand.MESSAGE_SUCCESS, validExam), expectedModel);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module moduleInList = model.getModBook().getModuleList().get(0);
        CommandTestUtil.assertCommandFailure(new AddModCommand(moduleInList), model, AddModCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_duplicateLesson_throwsCommandException() {
        Lesson lessonInList = model.getModBook().getModuleList().get(0).getLessons().get(0);
        ModuleCode moduleCode = model.getModBook().getModuleList().get(0).getCode();
        CommandTestUtil.assertCommandFailure(new AddLessonCommand(moduleCode, lessonInList), model,
                AddLessonCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_duplicateExam_throwsCommandException() {
        Exam examInList = model.getModBook().getModuleList().get(0).getExams().get(0);
        ModuleCode moduleCode = model.getModBook().getModuleList().get(0).getCode();
        CommandTestUtil.assertCommandFailure(new AddExamCommand(moduleCode, examInList), model, AddExamCommand.MESSAGE_DUPLICATE_EXAM);
    }
}
