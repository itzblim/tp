package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXAM_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LINK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RANDOM_TEXT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalExams.PHYSICAL_FINALS;
import static seedu.address.testutil.TypicalExams.PHYSICAL_FINALS_NO_LINK_NO_VENUE;
import static seedu.address.testutil.TypicalLessons.CS2103T_LECTURE_NO_LINK_NO_VENUE;
import static seedu.address.testutil.TypicalLessons.CS2103T_LECTURE_WITH_VENUE;
import static seedu.address.testutil.TypicalModules.CS2103T_CODE_NAME;
import static seedu.address.testutil.TypicalModules.CS2103T_NO_NAME;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.add.AddCommand;
import seedu.address.logic.commands.add.AddExamCommand;
import seedu.address.logic.commands.add.AddLessonCommand;
import seedu.address.logic.commands.add.AddModCommand;
import seedu.address.model.module.Day;
import seedu.address.model.module.Link;
import seedu.address.model.module.ModBookDate;
import seedu.address.model.module.ModBookTime;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.Venue;
import seedu.address.model.module.exam.Exam;
import seedu.address.model.module.exam.ExamName;
import seedu.address.model.module.lesson.Lesson;
import seedu.address.model.module.lesson.LessonName;
import seedu.address.testutil.builders.ExamBuilder;
import seedu.address.testutil.builders.LessonBuilder;
import seedu.address.testutil.builders.ModuleBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Module expectedModule = new ModuleBuilder(CS2103T_CODE_NAME).build();
        ModuleCode expectedModuleCode = expectedModule.getCode();
        Lesson expectedLesson = new LessonBuilder(CS2103T_LECTURE_WITH_VENUE).build();
        Exam expectedExam = new ExamBuilder(PHYSICAL_FINALS).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "mod"
                + VALID_MODULE_CODE_DESC + VALID_MODULE_NAME_DESC, new AddModCommand(expectedModule));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "lesson"
                + VALID_MODULE_CODE_DESC + VALID_LESSON_NAME_DESC + VALID_DAY_DESC
                + VALID_START_TIME_DESC + VALID_END_TIME_DESC + VALID_LINK_DESC
                + VALID_VENUE_DESC, new AddLessonCommand(expectedModuleCode, expectedLesson));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "exam"
                + VALID_MODULE_CODE_DESC + VALID_EXAM_NAME_DESC + VALID_DATE_DESC
                + VALID_START_TIME_DESC + VALID_END_TIME_DESC + VALID_LINK_DESC
                + VALID_VENUE_DESC, new AddExamCommand(expectedModuleCode, expectedExam));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Module expectedModule = new ModuleBuilder(CS2103T_NO_NAME).build();
        ModuleCode expectedModuleCode = expectedModule.getCode();
        Lesson expectedLesson = new LessonBuilder(CS2103T_LECTURE_NO_LINK_NO_VENUE).build();
        Exam expectedExam = new ExamBuilder(PHYSICAL_FINALS_NO_LINK_NO_VENUE).build();


        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "mod"
                + VALID_MODULE_CODE_DESC, new AddModCommand(expectedModule));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "lesson"
                + VALID_MODULE_CODE_DESC + VALID_LESSON_NAME_DESC + VALID_DAY_DESC
                + VALID_START_TIME_DESC
                + VALID_END_TIME_DESC, new AddLessonCommand(expectedModuleCode, expectedLesson));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "exam"
                + VALID_MODULE_CODE_DESC + VALID_EXAM_NAME_DESC + VALID_DATE_DESC
                + VALID_START_TIME_DESC + VALID_END_TIME_DESC, new AddExamCommand(expectedModuleCode, expectedExam));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        String expectedModMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModCommand.MESSAGE_USAGE);
        String expectedLessonMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE);
        String expectedExamMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExamCommand.MESSAGE_USAGE);

        // Invalid add command
        assertParseFailure(parser, PREAMBLE_WHITESPACE + RANDOM_TEXT , expectedMessage);

        // Invalid add mod command
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "mod" + RANDOM_TEXT, expectedModMessage);

        // Invalid add lesson command
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "lesson" + RANDOM_TEXT, expectedLessonMessage);

        // Invalid add exam command
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "exam" + RANDOM_TEXT, expectedExamMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid Module Code
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "lesson"
                + INVALID_MODULE_CODE_DESC + VALID_LESSON_NAME_DESC + VALID_DAY_DESC
                + VALID_START_TIME_DESC + VALID_END_TIME_DESC, ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid Module Name
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "mod" + VALID_MODULE_CODE_DESC
                + INVALID_MODULE_NAME_DESC, ModuleName.MESSAGE_CONSTRAINTS);

        // invalid Lesson Name
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "lesson"
                + VALID_MODULE_CODE_DESC + INVALID_LESSON_NAME_DESC + VALID_DAY_DESC
                + VALID_START_TIME_DESC + VALID_END_TIME_DESC, LessonName.MESSAGE_CONSTRAINTS);

        // invalid Exam name
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "exam"
                + VALID_MODULE_CODE_DESC + INVALID_EXAM_NAME_DESC + VALID_DATE_DESC
                + VALID_START_TIME_DESC + VALID_END_TIME_DESC + VALID_LINK_DESC
                + VALID_VENUE_DESC, ExamName.MESSAGE_CONSTRAINTS);

        // invalid Day
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "lesson"
                + VALID_MODULE_CODE_DESC + VALID_LESSON_NAME_DESC + INVALID_DAY_DESC
                + VALID_START_TIME_DESC + VALID_END_TIME_DESC, Day.MESSAGE_CONSTRAINTS);

        // invalid Date
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "exam"
                + VALID_MODULE_CODE_DESC + VALID_EXAM_NAME_DESC + INVALID_DATE_DESC
                + VALID_START_TIME_DESC + VALID_END_TIME_DESC + VALID_LINK_DESC
                + VALID_VENUE_DESC, ModBookDate.MESSAGE_CONSTRAINTS);

        // invalid Start Time
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "lesson"
                + VALID_MODULE_CODE_DESC + VALID_LESSON_NAME_DESC + VALID_DAY_DESC
                + INVALID_START_TIME_DESC + VALID_END_TIME_DESC, ModBookTime.MESSAGE_CONSTRAINTS);

        // invalid End Time
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "lesson"
                + VALID_MODULE_CODE_DESC + VALID_LESSON_NAME_DESC + VALID_DAY_DESC
                + VALID_START_TIME_DESC + INVALID_END_TIME_DESC, ModBookTime.MESSAGE_CONSTRAINTS);

        // invalid Link
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "exam"
                + VALID_MODULE_CODE_DESC + VALID_EXAM_NAME_DESC + VALID_DATE_DESC
                + VALID_START_TIME_DESC + VALID_END_TIME_DESC + INVALID_LINK_DESC
                + VALID_VENUE_DESC, Link.MESSAGE_CONSTRAINTS);

        // invalid Venue
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "exam"
                + VALID_MODULE_CODE_DESC + VALID_EXAM_NAME_DESC + VALID_DATE_DESC
                + VALID_START_TIME_DESC + VALID_END_TIME_DESC + VALID_LINK_DESC
                + INVALID_VENUE_DESC, Venue.MESSAGE_CONSTRAINTS);
    }

}
