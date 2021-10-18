package t13.modbook.logic.parser;

import t13.modbook.logic.commands.DetailCommand;
import t13.modbook.logic.commands.GuiState;
import t13.modbook.logic.parser.exceptions.ParseException;
import t13.modbook.model.module.ModuleCode;
import t13.modbook.model.module.predicates.HasModuleCodePredicate;
import t13.modbook.commons.core.Messages;

public class DetailCommandParser implements Parser<DetailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DetailCommand
     * and returns a DetailCommand for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DetailCommand parse(String args, GuiState guiState) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_CODE);

        if (!argMultimap.getValue(CliSyntax.PREFIX_CODE).isPresent()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DetailCommand.MESSAGE_USAGE));
        }

        ModuleCode code = ParserUtil.parseModuleCode(argMultimap.getValue(CliSyntax.PREFIX_CODE).get());

        return new DetailCommand(new HasModuleCodePredicate(code));
    }
}
