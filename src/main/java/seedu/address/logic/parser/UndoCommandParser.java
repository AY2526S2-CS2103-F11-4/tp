package seedu.address.logic.parser;

import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code UndoCommand} object.
 * <p>
 * The undo command does not accept any parameters. If any additional
 * arguments are provided, a {@link ParseException} will be thrown.
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    /**
     * Parses the given {@code String} of arguments and returns an {@code UndoCommand}.
     *
     * @param args The input arguments provided by the user after the command word.
     * @return An {@code UndoCommand} object.
     * @throws ParseException If additional parameters are detected.
     */
    @Override
    public UndoCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(UndoCommand.MESSAGE_EXTRA_PARAMETERS);
        }
        return new UndoCommand();
    }
}
