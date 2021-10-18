package teethirteen.modbook.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import teethirteen.modbook.logic.commands.Command;
import teethirteen.modbook.logic.commands.GuiState;
import teethirteen.modbook.logic.parser.exceptions.ParseException;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {
    public static final GuiState DEFAULT_STATE = GuiState.SUMMARY;

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand} based on the given {@code GuiState}.
     */
    public static void assertParseSuccess(Parser parser, String userInput, GuiState guiState, Command expectedCommand) {
        try {
            Command command = parser.parse(userInput, guiState);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     * Will use the default {@code GuiState}.
     */
    public static void assertParseSuccess(Parser parser, String userInput, Command expectedCommand) {
        assertParseSuccess(parser, userInput, DEFAULT_STATE, expectedCommand);
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage} based on the given {@code GuiState}.
     */
    public static void assertParseFailure(Parser parser, String userInput, GuiState guiState, String expectedMessage) {
        try {
            parser.parse(userInput, guiState);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage} based on the given {@code GuiState}.
     * Will use the default {@code GuiState}.
     */
    public static void assertParseFailure(Parser parser, String userInput, String expectedMessage) {
        assertParseFailure(parser, userInput, DEFAULT_STATE, expectedMessage);
    }
}
