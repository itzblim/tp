package t13.modbook.logic.parser;

import static t13.modbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static t13.modbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import t13.modbook.logic.commands.DetailCommand;
import t13.modbook.logic.commands.GuiState;
import t13.modbook.model.module.ModuleCode;
import t13.modbook.model.module.predicates.HasModuleCodePredicate;
import t13.modbook.commons.core.Messages;
import t13.modbook.logic.commands.CommandTestUtil;

public class DetailCommandParserTest {

    private DetailCommandParser parser = new DetailCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "    ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DetailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingKeyword_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "cs", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DetailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidModuleCode_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_MODULE_CODE_DESC, ModuleCode.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validArgs_returnsDetailCommand() {
        // no leading and trailing whitespaces
        DetailCommand expectedDetailCommand =
                new DetailCommand(new HasModuleCodePredicate(new ModuleCode(CommandTestUtil.VALID_MODULE_CODE)));
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_MODULE_CODE_DESC, expectedDetailCommand);

        // leading and trailing whitespaces
        CommandParserTestUtil.assertParseSuccess(parser, " " + CliSyntax.PREFIX_CODE + " \n \t " + CommandTestUtil.VALID_MODULE_CODE
                + " \n \t", expectedDetailCommand);

        // parse in various GuiStates
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_MODULE_CODE_DESC, GuiState.LESSONS, expectedDetailCommand);
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_MODULE_CODE_DESC, GuiState.EXAMS, expectedDetailCommand);
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.VALID_MODULE_CODE_DESC, GuiState.DETAILS, expectedDetailCommand);
    }
}
