package t13.modbook.logic.parser;

import static t13.modbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static t13.modbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static t13.modbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static t13.modbook.testutil.TypicalModules.CS2103T_CODE_NAME;
import static t13.modbook.testutil.TypicalModules.CS2103T_NO_NAME;

import org.junit.jupiter.api.Test;

import t13.modbook.logic.commands.add.AddCommand;
import t13.modbook.logic.commands.add.AddExamCommand;
import t13.modbook.logic.commands.add.AddLessonCommand;
import t13.modbook.logic.commands.add.AddModCommand;
import t13.modbook.model.module.Day;
import t13.modbook.model.module.Link;
import t13.modbook.model.module.ModBookDate;
import t13.modbook.model.module.ModBookTime;
import t13.modbook.model.module.Module;
import t13.modbook.model.module.ModuleCode;
import t13.modbook.model.module.ModuleName;
import t13.modbook.model.module.Venue;
import t13.modbook.model.module.exam.Exam;
import t13.modbook.model.module.exam.ExamName;
import t13.modbook.model.module.lesson.Lesson;
import t13.modbook.model.module.lesson.LessonName;
import t13.modbook.testutil.builders.ExamBuilder;
import t13.modbook.testutil.builders.LessonBuilder;
import t13.modbook.testutil.builders.ModuleBuilder;
import t13.modbook.logic.commands.CommandTestUtil;
import t13.modbook.testutil.TypicalExams;
import t13.modbook.testutil.TypicalLessons;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Module expectedModule = new ModuleBuilder(CS2103T_CODE_NAME).build();
        ModuleCode expectedModuleCode = expectedModule.getCode();
        Lesson expectedLesson = new LessonBuilder(TypicalLessons.CS2103T_LECTURE_WITH_VENUE).build();
        Exam expectedExam = new ExamBuilder(TypicalExams.PHYSICAL_FINALS).build();

        // whitespace only preamble
        assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "mod"
                + CommandTestUtil.VALID_MODULE_CODE_DESC + CommandTestUtil.VALID_MODULE_NAME_DESC, new AddModCommand(expectedModule));

        // whitespace only preamble
        assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "lesson"
                + CommandTestUtil.VALID_MODULE_CODE_DESC + CommandTestUtil.VALID_LESSON_NAME_DESC + CommandTestUtil.VALID_DAY_DESC
                + CommandTestUtil.VALID_START_TIME_DESC + CommandTestUtil.VALID_END_TIME_DESC + CommandTestUtil.VALID_LINK_DESC
                + CommandTestUtil.VALID_VENUE_DESC, new AddLessonCommand(expectedModuleCode, expectedLesson));

        // whitespace only preamble
        assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "exam"
                + CommandTestUtil.VALID_MODULE_CODE_DESC + CommandTestUtil.VALID_EXAM_NAME_DESC + CommandTestUtil.VALID_DATE_DESC
                + CommandTestUtil.VALID_START_TIME_DESC + CommandTestUtil.VALID_END_TIME_DESC + CommandTestUtil.VALID_LINK_DESC
                + CommandTestUtil.VALID_VENUE_DESC, new AddExamCommand(expectedModuleCode, expectedExam));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Module expectedModule = new ModuleBuilder(CS2103T_NO_NAME).build();
        ModuleCode expectedModuleCode = expectedModule.getCode();
        Lesson expectedLesson = new LessonBuilder(TypicalLessons.CS2103T_LECTURE_NO_LINK_NO_VENUE).build();
        Exam expectedExam = new ExamBuilder(TypicalExams.PHYSICAL_FINALS_NO_LINK_NO_VENUE).build();


        // whitespace only preamble
        assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "mod"
                + CommandTestUtil.VALID_MODULE_CODE_DESC, new AddModCommand(expectedModule));

        // whitespace only preamble
        assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "lesson"
                + CommandTestUtil.VALID_MODULE_CODE_DESC + CommandTestUtil.VALID_LESSON_NAME_DESC + CommandTestUtil.VALID_DAY_DESC
                + CommandTestUtil.VALID_START_TIME_DESC
                + CommandTestUtil.VALID_END_TIME_DESC, new AddLessonCommand(expectedModuleCode, expectedLesson));

        // whitespace only preamble
        assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "exam"
                + CommandTestUtil.VALID_MODULE_CODE_DESC + CommandTestUtil.VALID_EXAM_NAME_DESC + CommandTestUtil.VALID_DATE_DESC
                + CommandTestUtil.VALID_START_TIME_DESC + CommandTestUtil.VALID_END_TIME_DESC, new AddExamCommand(expectedModuleCode, expectedExam));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        String expectedModMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModCommand.MESSAGE_USAGE);
        String expectedLessonMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE);
        String expectedExamMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExamCommand.MESSAGE_USAGE);

        // Invalid add command
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + CommandTestUtil.RANDOM_TEXT , expectedMessage);

        // Invalid add mod command
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "mod" + CommandTestUtil.RANDOM_TEXT, expectedModMessage);

        // Invalid add lesson command
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "lesson" + CommandTestUtil.RANDOM_TEXT, expectedLessonMessage);

        // Invalid add exam command
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "exam" + CommandTestUtil.RANDOM_TEXT, expectedExamMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid Module Code
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "lesson"
                + CommandTestUtil.INVALID_MODULE_CODE_DESC + CommandTestUtil.VALID_LESSON_NAME_DESC + CommandTestUtil.VALID_DAY_DESC
                + CommandTestUtil.VALID_START_TIME_DESC + CommandTestUtil.VALID_END_TIME_DESC, ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid Module Name
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "mod" + CommandTestUtil.VALID_MODULE_CODE_DESC
                + CommandTestUtil.INVALID_MODULE_NAME_DESC, ModuleName.MESSAGE_CONSTRAINTS);

        // invalid Lesson Name
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "lesson"
                + CommandTestUtil.VALID_MODULE_CODE_DESC + CommandTestUtil.INVALID_LESSON_NAME_DESC + CommandTestUtil.VALID_DAY_DESC
                + CommandTestUtil.VALID_START_TIME_DESC + CommandTestUtil.VALID_END_TIME_DESC, LessonName.MESSAGE_CONSTRAINTS);

        // invalid Exam name
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "exam"
                + CommandTestUtil.VALID_MODULE_CODE_DESC + CommandTestUtil.INVALID_EXAM_NAME_DESC + CommandTestUtil.VALID_DATE_DESC
                + CommandTestUtil.VALID_START_TIME_DESC + CommandTestUtil.VALID_END_TIME_DESC + CommandTestUtil.VALID_LINK_DESC
                + CommandTestUtil.VALID_VENUE_DESC, ExamName.MESSAGE_CONSTRAINTS);

        // invalid Day
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "lesson"
                + CommandTestUtil.VALID_MODULE_CODE_DESC + CommandTestUtil.VALID_LESSON_NAME_DESC + CommandTestUtil.INVALID_DAY_DESC
                + CommandTestUtil.VALID_START_TIME_DESC + CommandTestUtil.VALID_END_TIME_DESC, Day.MESSAGE_CONSTRAINTS);

        // invalid Date
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "exam"
                + CommandTestUtil.VALID_MODULE_CODE_DESC + CommandTestUtil.VALID_EXAM_NAME_DESC + CommandTestUtil.INVALID_DATE_DESC
                + CommandTestUtil.VALID_START_TIME_DESC + CommandTestUtil.VALID_END_TIME_DESC + CommandTestUtil.VALID_LINK_DESC
                + CommandTestUtil.VALID_VENUE_DESC, ModBookDate.MESSAGE_CONSTRAINTS);

        // invalid Start Time
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "lesson"
                + CommandTestUtil.VALID_MODULE_CODE_DESC + CommandTestUtil.VALID_LESSON_NAME_DESC + CommandTestUtil.VALID_DAY_DESC
                + CommandTestUtil.INVALID_START_TIME_DESC + CommandTestUtil.VALID_END_TIME_DESC, ModBookTime.MESSAGE_CONSTRAINTS);

        // invalid End Time
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "lesson"
                + CommandTestUtil.VALID_MODULE_CODE_DESC + CommandTestUtil.VALID_LESSON_NAME_DESC + CommandTestUtil.VALID_DAY_DESC
                + CommandTestUtil.VALID_START_TIME_DESC + CommandTestUtil.INVALID_END_TIME_DESC, ModBookTime.MESSAGE_CONSTRAINTS);

        // invalid Link
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "exam"
                + CommandTestUtil.VALID_MODULE_CODE_DESC + CommandTestUtil.VALID_EXAM_NAME_DESC + CommandTestUtil.VALID_DATE_DESC
                + CommandTestUtil.VALID_START_TIME_DESC + CommandTestUtil.VALID_END_TIME_DESC + CommandTestUtil.INVALID_LINK_DESC
                + CommandTestUtil.VALID_VENUE_DESC, Link.MESSAGE_CONSTRAINTS);

        // invalid Venue
        assertParseFailure(parser, CommandTestUtil.PREAMBLE_WHITESPACE + "exam"
                + CommandTestUtil.VALID_MODULE_CODE_DESC + CommandTestUtil.VALID_EXAM_NAME_DESC + CommandTestUtil.VALID_DATE_DESC
                + CommandTestUtil.VALID_START_TIME_DESC + CommandTestUtil.VALID_END_TIME_DESC + CommandTestUtil.VALID_LINK_DESC
                + CommandTestUtil.INVALID_VENUE_DESC, Venue.MESSAGE_CONSTRAINTS);
    }

}
