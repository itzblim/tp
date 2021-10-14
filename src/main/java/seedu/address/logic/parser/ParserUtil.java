package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Day;
import seedu.address.model.module.Link;
import seedu.address.model.module.ModBookDate;
import seedu.address.model.module.ModBookTime;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.Timeslot;
import seedu.address.model.module.Venue;
import seedu.address.model.module.exam.Exam;
import seedu.address.model.module.exam.ExamName;
import seedu.address.model.module.lesson.Lesson;
import seedu.address.model.module.lesson.LessonName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String link} into a {@code Link}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code link} is invalid.
     */
    public static Link parseLink(String link) throws ParseException {
        requireNonNull(link);
        String trimmedLink = link.trim();
        if (!Link.isValidLink(trimmedLink)) {
            throw new ParseException(Link.MESSAGE_CONSTRAINTS);
        }
        return new Link(trimmedLink);
    }

    /**
     * Parses a {@code String time} into a {@code ModBookTime}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static ModBookTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!ModBookTime.isValidTime(trimmedTime)) {
            throw new ParseException(ModBookTime.MESSAGE_CONSTRAINTS);
        }
        return new ModBookTime(trimmedTime);
    }

    /**
     * Parses a {@code String startTime} and {@code String endTime} into a {@code Timeslot}
     *
     * @throws ParseException if the given startTime and endTime are invalid.
     */
    public static Timeslot parseTimeslot(String startTime, String endTime) throws ParseException {
        ModBookTime start = parseTime(startTime);
        ModBookTime end = parseTime(endTime);
        if (!Timeslot.isValidTimeslot(start, end)) {
            throw new ParseException(Timeslot.MESSAGE_CONSTRAINTS);
        }
        return new Timeslot(start, end);
    }

    /**
     * Parses a {@code String date} into a {@code ModBookDate}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static ModBookDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!ModBookDate.isValidDate(trimmedDate)) {
            throw new ParseException(ModBookDate.MESSAGE_CONSTRAINTS);
        }
        return new ModBookDate(trimmedDate);
    }

    /**
     * Parses a {@code String examName} into a {@code ExamName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code examName} is invalid.
     */
    public static ExamName parseExamName(String examName) throws ParseException {
        requireNonNull(examName);
        String trimmedExamName = examName.trim();
        if (!ExamName.isValidExamName(trimmedExamName)) {
            throw new ParseException(ExamName.MESSAGE_CONSTRAINTS);
        }
        return new ExamName(trimmedExamName);
    }

    /**
     * Parses a {@code String venueName} into a {@code Venue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code venueName} is invalid.
     */
    public static Venue parseVenue(String venueName) throws ParseException {
        requireNonNull(venueName);
        String trimmedVenueName = venueName.trim();
        if (!Venue.isValidVenue(trimmedVenueName)) {
            throw new ParseException(Venue.MESSAGE_CONSTRAINTS);
        }
        return new Venue(trimmedVenueName);
    }

    /**
     * Parses a {@code String name}, {@code String date}, {@code String startTime}, {@code String endTime},
     * {@code Optional<String> venueName}, {@code Optional<String> linkString} into an {@code Exam}
     *
     * @throws ParseException if any of the data strings are invalid.
     */
    public static Exam parseExam(String name, String date, String startTime, String endTime,
                                 Optional<String> venueName, Optional<String> linkString) throws ParseException {
        ExamName examName = parseExamName(name);
        ModBookDate modBookDate = parseDate(date);
        Timeslot timeslot = parseTimeslot(startTime, endTime);

        Optional<Venue> venue = venueName.isPresent()
                ? Optional.of(parseVenue(venueName.get()))
                : Optional.empty();
        Optional<Link> link = linkString.isPresent()
                ? Optional.of(parseLink(linkString.get()))
                : Optional.empty();
        return new Exam(examName, modBookDate, timeslot, venue, link);
    }

    /**
     * Parses a {@code String lessonName} into a {@code LessonName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code lessonName} is invalid.
     */
    public static LessonName parseLessonName(String lessonName) throws ParseException {
        requireNonNull(lessonName);
        String trimmedLessonName = lessonName.trim();
        if (!LessonName.isValidLessonName(trimmedLessonName)) {
            throw new ParseException(LessonName.MESSAGE_CONSTRAINTS);
        }
        return new LessonName(trimmedLessonName);
    }

    /**
     * Parses a {@code String dayString} into a {@code Day}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dayString} is invalid.
     */
    public static Day parseDay(String dayString) throws ParseException {
        requireNonNull(dayString);
        String trimmedDay = dayString.trim();
        if (!Day.isValidDay(trimmedDay)) {
            throw new ParseException(Day.MESSAGE_CONSTRAINTS);
        }
        return Day.valueOf(trimmedDay.toUpperCase());
    }

    /**
     * Parses a {@code String name}, {@code String dayString}, {@code String startTime}, {@code String endTime},
     * {@code Optional<String> venueName}, {@code Optional<String> linkString} into an {@code Lesson}
     *
     * @throws ParseException if any of the data strings are invalid.
     */
    public static Lesson parseLesson(String name, String dayString, String startTime, String endTime,
                                     Optional<String> venueName, Optional<String> linkString) throws ParseException {
        LessonName lessonName = parseLessonName(name);
        Day day = parseDay(dayString.toUpperCase());
        Timeslot timeslot = parseTimeslot(startTime, endTime);

        Optional<Venue> venue = venueName.isPresent()
                ? Optional.of(parseVenue(venueName.get()))
                : Optional.empty();
        Optional<Link> link = linkString.isPresent()
                ? Optional.of(parseLink(linkString.get()))
                : Optional.empty();
        return new Lesson(lessonName, day, timeslot, venue, link);
    }

    /**
     * Parses a {@code String moduleCode} into a {@code ModuleCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code moduleCode} is invalid.
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim();
        if (!ModuleCode.isValidModuleCode(trimmedModuleCode)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedModuleCode);
    }

    /**
     * Parses a {@code String moduleName} into a {@code ModuleName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code moduleName} is invalid.
     */
    public static ModuleName parseModuleName(String moduleName) throws ParseException {
        requireNonNull(moduleName);
        String trimmedModuleName = moduleName.trim();
        if (!ModuleName.isValidModuleName(trimmedModuleName)) {
            throw new ParseException(ModuleName.MESSAGE_CONSTRAINTS);
        }
        return new ModuleName(trimmedModuleName);
    }

    /**
     * Parses a {@code String code} and a {@code Optional<String> name} into a {@code Module}.
     *
     * @throws ParseException if any of the data strings are invalid.
     */
    public static Module parseModule(String code, Optional<String> name) throws ParseException {
        ModuleCode moduleCode = parseModuleCode(code);
        Optional<ModuleName> moduleName = name.isPresent()
                ? Optional.of(parseModuleName(name.get()))
                : Optional.empty();
        return new Module(moduleCode, moduleName);
    }

    /**
     * Parses a {@code String args} and returns first word if it is one of mod, lesson, or exam.
     * If args is invalid, returns a ParseException with the provided errorMessage.
     * Note that we only allow exact lowercase matches for now.
     *
     * @throws ParseException if the args string is invalid.
     */
    public static Type parseFirstArg(String args, String errorMessage) throws ParseException {
        String firstArg = args.trim().split(" ", 2)[0];
        switch (firstArg) {
        case "mod":
            return Type.MOD;
        case "lesson":
            return Type.LESSON;
        case "exam":
            return Type.EXAM;
        default:
            throw new ParseException(errorMessage);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
