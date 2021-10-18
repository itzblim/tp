package teethirteen.modbook.logic.parser;

import static teethirteen.modbook.logic.parser.CliSyntax.PREFIX_CODE;
import static teethirteen.modbook.logic.parser.ParserUtil.arePrefixesPresent;

import teethirteen.modbook.commons.core.Messages;
import teethirteen.modbook.commons.core.index.Index;
import teethirteen.modbook.logic.commands.GuiState;
import teethirteen.modbook.logic.commands.delete.DeleteCommand;
import teethirteen.modbook.logic.commands.delete.DeleteExamCommand;
import teethirteen.modbook.logic.commands.delete.DeleteLessonCommand;
import teethirteen.modbook.logic.commands.delete.DeleteModCommand;
import teethirteen.modbook.logic.parser.exceptions.GuiStateException;
import teethirteen.modbook.logic.parser.exceptions.ParseException;
import teethirteen.modbook.model.module.ModuleCode;


/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    public static final String MESSAGE_WRONG_VIEW_DETAILS = "Please execute the \"detail\" command first!";
    public static final String MESSAGE_WRONG_VIEW_SUMMARY = "Please execute \"list mod\" first!";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args, GuiState guiState) throws ParseException {
        Type type = ParserUtil.parseFirstArg(args, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
        switch(type) {
        case MOD:
            return parseDeleteMod(args, guiState);
        case LESSON:
            return parseDeleteLesson(args, guiState);
        case EXAM:
            return parseDeleteExam(args, guiState);
        default:
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteModCommand
     * and returns a DeleteModCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteModCommand parseDeleteMod(String args, GuiState guiState) throws ParseException {
        if (guiState != GuiState.SUMMARY) {
            throw new GuiStateException(MESSAGE_WRONG_VIEW_SUMMARY);
        }
        Index index = ParserUtil.parseFirstIndex(args);
        return new DeleteModCommand(index);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLessonCommand
     * and returns a DeleteLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteLessonCommand parseDeleteLesson(String args, GuiState guiState) throws ParseException {
        if (guiState != GuiState.DETAILS) {
            throw new GuiStateException(MESSAGE_WRONG_VIEW_DETAILS);
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CODE);
        if (!arePrefixesPresent(argMultimap, PREFIX_CODE)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteLessonCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseFirstIndex(args);
        ModuleCode modCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_CODE).get());
        return new DeleteLessonCommand(index, modCode);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteExamCommand
     * and returns a DeleteExamCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteExamCommand parseDeleteExam(String args, GuiState guiState) throws ParseException {
        if (guiState != GuiState.DETAILS) {
            throw new GuiStateException(MESSAGE_WRONG_VIEW_DETAILS);
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CODE);
        if (!arePrefixesPresent(argMultimap, PREFIX_CODE)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteExamCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseFirstIndex(args);
        ModuleCode modCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_CODE).get());
        return new DeleteExamCommand(index, modCode);
    }
}
