package teethirteen.modbook.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static teethirteen.modbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static teethirteen.modbook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.jupiter.api.Test;

import teethirteen.modbook.logic.commands.ClearCommand;
import teethirteen.modbook.logic.commands.ExitCommand;
import teethirteen.modbook.logic.commands.GuiState;
import teethirteen.modbook.logic.commands.HelpCommand;
import teethirteen.modbook.logic.commands.add.AddModCommand;
import teethirteen.modbook.logic.commands.delete.DeleteCommand;
import teethirteen.modbook.logic.commands.delete.DeleteExamCommand;
import teethirteen.modbook.logic.commands.delete.DeleteLessonCommand;
import teethirteen.modbook.logic.commands.delete.DeleteModCommand;
import teethirteen.modbook.logic.commands.list.ListCommand;
import teethirteen.modbook.logic.parser.exceptions.ParseException;
import teethirteen.modbook.model.module.Module;
import teethirteen.modbook.testutil.ModuleUtil;
import teethirteen.modbook.testutil.builders.ModuleBuilder;
import teethirteen.modbook.testutil.Assert;
import teethirteen.modbook.testutil.TypicalIndexes;

public class ModBookParserTest {

    private static final GuiState DEFAULT_STATE = GuiState.SUMMARY;

    private final ModBookParser parser = new ModBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Module module = new ModuleBuilder().build();
        AddModCommand command = (AddModCommand) parser.parseCommand(ModuleUtil.getAddCommand(module), DEFAULT_STATE);
        assertEquals(new AddModCommand(module), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, DEFAULT_STATE) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", DEFAULT_STATE)
                instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        Module module = new ModuleBuilder().build();
        DeleteCommand deleteModCommand = (DeleteModCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " mod " + TypicalIndexes.INDEX_FIRST_MODULE.getOneBased(),
                DEFAULT_STATE);
        assertEquals(new DeleteModCommand(TypicalIndexes.INDEX_FIRST_MODULE), deleteModCommand);

        DeleteCommand deleteLessonCommand = (DeleteLessonCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " lesson "
                        + TypicalIndexes.INDEX_FIRST_LESSON.getOneBased() + " "
                        + CliSyntax.PREFIX_CODE + module.getCode(), GuiState.DETAILS);
        assertEquals(new DeleteLessonCommand(TypicalIndexes.INDEX_FIRST_LESSON, module.getCode()), deleteLessonCommand);

        DeleteCommand deleteExamCommand = (DeleteExamCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " exam "
                        + TypicalIndexes.INDEX_FIRST_EXAM.getOneBased() + " "
                        + CliSyntax.PREFIX_CODE + module.getCode(), GuiState.DETAILS);
        assertEquals(new DeleteExamCommand(TypicalIndexes.INDEX_FIRST_EXAM, module.getCode()), deleteExamCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, DEFAULT_STATE) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", DEFAULT_STATE) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, DEFAULT_STATE) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", DEFAULT_STATE) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " mod",
                DEFAULT_STATE) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " lesson",
                DEFAULT_STATE) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " exam",
                DEFAULT_STATE) instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("", DEFAULT_STATE));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND,
                () -> parser.parseCommand("unknownCommand", DEFAULT_STATE));
    }
}
