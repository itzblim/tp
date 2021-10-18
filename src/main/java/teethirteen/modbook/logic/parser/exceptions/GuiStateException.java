package teethirteen.modbook.logic.parser.exceptions;

public class GuiStateException extends ParseException {

    private static final String ERROR_MESSAGE = "This command cannot be used in this screen!";

    public GuiStateException() {
        super(ERROR_MESSAGE);
    }

    public GuiStateException(String message) {
        super(message);
    }

    public GuiStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
