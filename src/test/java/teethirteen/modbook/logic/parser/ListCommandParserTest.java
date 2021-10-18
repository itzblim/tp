package teethirteen.modbook.logic.parser;

import org.junit.jupiter.api.Test;

import teethirteen.modbook.commons.core.Messages;
import teethirteen.modbook.logic.commands.GuiState;
import teethirteen.modbook.logic.commands.list.ListCommand;
import teethirteen.modbook.logic.commands.list.ListExamCommand;
import teethirteen.modbook.logic.commands.list.ListLessonCommand;
import teethirteen.modbook.logic.commands.list.ListModCommand;

/**
 * Contains unit tests for ListCommandParser.
 */
public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "    ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingKeyword_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "venue",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraKeyword_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "mod lesson",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsListCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListModCommand = new ListModCommand();
        CommandParserTestUtil.assertParseSuccess(parser, " mod", expectedListModCommand);

        ListCommand expectedListLessonCommand = new ListLessonCommand();
        CommandParserTestUtil.assertParseSuccess(parser, " lesson", expectedListLessonCommand);

        ListCommand expectedListExamCommand = new ListExamCommand();
        CommandParserTestUtil.assertParseSuccess(parser, " exam", expectedListExamCommand);

        // leading and trailing whitespaces
        CommandParserTestUtil.assertParseSuccess(parser, " mod" + " \n \t ", expectedListModCommand);

        // parse in various GuiStates
        CommandParserTestUtil.assertParseSuccess(parser, " mod", GuiState.SUMMARY, expectedListModCommand);
        CommandParserTestUtil.assertParseSuccess(parser, " mod", GuiState.LESSONS, expectedListModCommand);
        CommandParserTestUtil.assertParseSuccess(parser, " mod", GuiState.EXAMS, expectedListModCommand);
        CommandParserTestUtil.assertParseSuccess(parser, " mod", GuiState.DETAILS, expectedListModCommand);
    }
}
