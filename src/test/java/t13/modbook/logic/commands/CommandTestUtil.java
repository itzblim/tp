package t13.modbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_CODE;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_DAY;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_END;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_LINK;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_NAME;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_START;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.ArrayList;
import java.util.List;

import t13.modbook.commons.core.index.Index;
import t13.modbook.logic.commands.exceptions.CommandException;
import t13.modbook.model.ModBook;
import t13.modbook.model.Model;
import t13.modbook.model.module.Module;
import t13.modbook.model.module.predicates.HasModuleCodePredicate;
import t13.modbook.testutil.Assert;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_MODULE_CODE = "CS2103T";
    public static final String VALID_MODULE_NAME = "Software Engineering";
    public static final String VALID_LESSON_NAME = "CS2103T Lecture";
    public static final String VALID_EXAM_NAME = "Finals";
    public static final String VALID_DAY = "FRIDAY";
    public static final String VALID_DATE = "24/11/2021";
    public static final String VALID_START_TIME = "16:00";
    public static final String VALID_END_TIME = "18:00";
    public static final String VALID_LINK = "profDamith.com";
    public static final String VALID_VENUE = "COM1";

    public static final String VALID_MODULE_CODE_DESC = " " + PREFIX_CODE + VALID_MODULE_CODE;
    public static final String VALID_MODULE_NAME_DESC = " " + PREFIX_NAME + VALID_MODULE_NAME;
    public static final String VALID_LESSON_NAME_DESC = " " + PREFIX_NAME + VALID_LESSON_NAME;
    public static final String VALID_EXAM_NAME_DESC = " " + PREFIX_NAME + VALID_EXAM_NAME;
    public static final String VALID_DAY_DESC = " " + PREFIX_DAY + VALID_DAY;
    public static final String VALID_DATE_DESC = " " + PREFIX_DAY + VALID_DATE;
    public static final String VALID_START_TIME_DESC = " " + PREFIX_START + VALID_START_TIME;
    public static final String VALID_END_TIME_DESC = " " + PREFIX_END + VALID_END_TIME;
    public static final String VALID_LINK_DESC = " " + PREFIX_LINK + VALID_LINK;
    public static final String VALID_VENUE_DESC = " " + PREFIX_VENUE + VALID_VENUE;

    public static final String INVALID_MODULE_CODE_DESC = " " + PREFIX_CODE + "C2030S";
    public static final String INVALID_MODULE_NAME_DESC = " " + PREFIX_NAME + "";
    public static final String INVALID_LESSON_NAME_DESC = " " + PREFIX_NAME + "";
    public static final String INVALID_EXAM_NAME_DESC = " " + PREFIX_NAME + "";
    public static final String INVALID_DAY_DESC = " " + PREFIX_DAY + "jupiter";
    public static final String INVALID_DATE_DESC = " " + PREFIX_DAY + "mon";
    public static final String INVALID_LINK_DESC = " " + PREFIX_LINK + "";
    public static final String INVALID_START_TIME_DESC = " " + PREFIX_START + "100";
    public static final String INVALID_END_TIME_DESC = " " + PREFIX_END + "100";
    public static final String INVALID_VENUE_DESC = " " + PREFIX_VENUE + "";

    public static final String RANDOM_TEXT = " " + "momo";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the mod book, filtered module list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ModBook expectedModBook = new ModBook(actualModel.getModBook());
        List<Module> expectedFilteredList = new ArrayList<>(actualModel.getFilteredModuleList());

        Assert.assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedModBook, actualModel.getModBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredModuleList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the module at the given {@code targetIndex} in the
     * {@code model}'s ModBook.
     */
    public static void showModuleAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredModuleList().size());

        Module module = model.getFilteredModuleList().get(targetIndex.getZeroBased());
        model.updateFilteredModuleList(new HasModuleCodePredicate(module.getCode()));

        assertEquals(1, model.getFilteredModuleList().size());
    }

}
