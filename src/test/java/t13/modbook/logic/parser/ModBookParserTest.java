package t13.modbook.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static t13.modbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static t13.modbook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_CODE;
import static t13.modbook.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import t13.modbook.logic.commands.ClearCommand;
import seedu.modbook.logic.commands.EditCommand;
import seedu.modbook.logic.commands.EditCommand.EditPersonDescriptor;
import t13.modbook.logic.commands.ExitCommand;
import seedu.modbook.logic.commands.FindCommand;
import t13.modbook.logic.commands.GuiState;
import t13.modbook.logic.commands.HelpCommand;
import t13.modbook.logic.commands.add.AddModCommand;
import t13.modbook.logic.commands.delete.DeleteCommand;
import t13.modbook.logic.commands.delete.DeleteExamCommand;
import t13.modbook.logic.commands.delete.DeleteLessonCommand;
import t13.modbook.logic.commands.delete.DeleteModCommand;
import t13.modbook.logic.commands.list.ListCommand;
import t13.modbook.logic.parser.exceptions.ParseException;
import t13.modbook.model.module.Module;
import seedu.modbook.model.person.NameContainsKeywordsPredicate;
import seedu.modbook.model.person.Person;
import t13.modbook.testutil.EditPersonDescriptorBuilder;
import t13.modbook.testutil.PersonUtil;
import t13.modbook.testutil.builders.ModuleBuilder;
import t13.modbook.testutil.builders.PersonBuilder;
import t13.modbook.testutil.Assert;
import t13.modbook.testutil.TypicalIndexes;

public class ModBookParserTest {

    private static final GuiState DEFAULT_STATE = GuiState.SUMMARY;

    private final ModBookParser parser = new ModBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Module module = new ModuleBuilder().build();
        AddModCommand command = (AddModCommand) parser.parseCommand(PersonUtil.getAddCommand(module), DEFAULT_STATE);
        assertEquals(new AddModCommand(module), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, DEFAULT_STATE) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", DEFAULT_STATE) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        Module module = new ModuleBuilder().build();
        DeleteCommand deleteModCommand = (DeleteModCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " mod " + TypicalIndexes.INDEX_FIRST_MODULE.getOneBased(), DEFAULT_STATE);
        assertEquals(new DeleteModCommand(TypicalIndexes.INDEX_FIRST_MODULE), deleteModCommand);

        DeleteCommand deleteLessonCommand = (DeleteLessonCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " lesson "
                        + TypicalIndexes.INDEX_FIRST_LESSON.getOneBased() + " "
                        + PREFIX_CODE + module.getCode(), GuiState.DETAILS);
        assertEquals(new DeleteLessonCommand(TypicalIndexes.INDEX_FIRST_LESSON, module.getCode()), deleteLessonCommand);

        DeleteCommand deleteExamCommand = (DeleteExamCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " exam "
                        + TypicalIndexes.INDEX_FIRST_EXAM.getOneBased() + " "
                        + PREFIX_CODE + module.getCode(), GuiState.DETAILS);
        assertEquals(new DeleteExamCommand(TypicalIndexes.INDEX_FIRST_EXAM, module.getCode()), deleteExamCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor),
                DEFAULT_STATE);
        assertEquals(new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, DEFAULT_STATE) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", DEFAULT_STATE) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
                DEFAULT_STATE);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
        Assert.assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", DEFAULT_STATE));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand",
                DEFAULT_STATE));
    }
}
