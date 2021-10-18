package teethirteen.modbook.logic.parser;

import static teethirteen.modbook.logic.parser.CliSyntax.PREFIX_CODE;
import static teethirteen.modbook.logic.parser.CliSyntax.PREFIX_DAY;
import static teethirteen.modbook.logic.parser.CliSyntax.PREFIX_END;
import static teethirteen.modbook.logic.parser.CliSyntax.PREFIX_LINK;
import static teethirteen.modbook.logic.parser.CliSyntax.PREFIX_NAME;
import static teethirteen.modbook.logic.parser.CliSyntax.PREFIX_START;
import static teethirteen.modbook.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.NoSuchElementException;
import java.util.Optional;

import teethirteen.modbook.commons.core.Messages;
import teethirteen.modbook.logic.commands.GuiState;
import teethirteen.modbook.logic.commands.add.AddCommand;
import teethirteen.modbook.logic.commands.add.AddExamCommand;
import teethirteen.modbook.logic.commands.add.AddLessonCommand;
import teethirteen.modbook.logic.commands.add.AddModCommand;
import teethirteen.modbook.logic.parser.exceptions.ParseException;
import teethirteen.modbook.model.module.Day;
import teethirteen.modbook.model.module.Link;
import teethirteen.modbook.model.module.ModBookDate;
import teethirteen.modbook.model.module.ModBookTime;
import teethirteen.modbook.model.module.Module;
import teethirteen.modbook.model.module.ModuleCode;
import teethirteen.modbook.model.module.ModuleName;
import teethirteen.modbook.model.module.Timeslot;
import teethirteen.modbook.model.module.Venue;
import teethirteen.modbook.model.module.exam.Exam;
import teethirteen.modbook.model.module.exam.ExamName;
import teethirteen.modbook.model.module.lesson.Lesson;
import teethirteen.modbook.model.module.lesson.LessonName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args, GuiState guiState) throws ParseException {
        String errorMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        Type type = ParserUtil.parseFirstArg(args, errorMessage);

        switch(type) {
        case MOD:
            return parseMod(args);
        case LESSON:
            return parseLesson(args);
        case EXAM:
            return parseExam(args);
        default:
            throw new ParseException(errorMessage);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddModCommand
     * and returns an AddModCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddModCommand parseMod(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CODE, PREFIX_NAME);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_CODE)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddModCommand.MESSAGE_USAGE));
        }

        ModuleCode modCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_CODE).get());
        Optional<ModuleName> modName;
        try {
            modName = Optional.of(ParserUtil.parseModuleName(argMultimap.getValue(PREFIX_NAME).get()));
        } catch (NoSuchElementException e) {
            modName = Optional.empty();
        }

        Module module = new Module(modCode, modName);
        return new AddModCommand(module);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddLessonCommand
     * and returns an AddLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parseLesson(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CODE, PREFIX_NAME, PREFIX_DAY, PREFIX_START, PREFIX_END,
                        PREFIX_LINK, PREFIX_VENUE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_CODE, PREFIX_NAME, PREFIX_DAY,
                PREFIX_START, PREFIX_END)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLessonCommand.MESSAGE_USAGE));
        }

        ModuleCode modCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_CODE).get());
        LessonName lessonName = ParserUtil.parseLessonName(argMultimap.getValue(PREFIX_NAME).get());
        Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
        ModBookTime startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START).get());
        ModBookTime endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END).get());
        Timeslot timeslot = new Timeslot(startTime, endTime);
        Optional<Link> link;
        Optional<Venue> venue;

        try {
            link = Optional.of(ParserUtil.parseLink(argMultimap.getValue(PREFIX_LINK).get()));
        } catch (NoSuchElementException e) {
            link = Optional.empty();
        }

        try {
            venue = Optional.of(ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get()));
        } catch (NoSuchElementException e) {
            venue = Optional.empty();
        }

        Lesson lesson = new Lesson(lessonName, day, timeslot, venue, link);
        return new AddLessonCommand(modCode, lesson);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddExamCommand
     * and returns an AddExamCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddExamCommand parseExam(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CODE, PREFIX_NAME, PREFIX_DAY, PREFIX_START, PREFIX_END,
                        PREFIX_LINK, PREFIX_VENUE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_CODE, PREFIX_NAME, PREFIX_DAY,
                PREFIX_START, PREFIX_END)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddExamCommand.MESSAGE_USAGE));
        }

        ModuleCode modCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_CODE).get());
        ExamName examName = ParserUtil.parseExamName(argMultimap.getValue(PREFIX_NAME).get());
        ModBookDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DAY).get());
        ModBookTime startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START).get());
        ModBookTime endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END).get());
        Timeslot timeslot = new Timeslot(startTime, endTime);
        Optional<Link> link;
        Optional<Venue> venue;

        try {
            link = Optional.of(ParserUtil.parseLink(argMultimap.getValue(PREFIX_LINK).get()));
        } catch (NoSuchElementException e) {
            link = Optional.empty();
        }

        try {
            venue = Optional.of(ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get()));
        } catch (NoSuchElementException e) {
            venue = Optional.empty();
        }

        Exam exam = new Exam(examName, date, timeslot, venue, link);
        return new AddExamCommand(modCode, exam);
    }
}
