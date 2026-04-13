package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UndoCommand;

/**
 * Contains unit tests for {@code UndoCommandParser}.
 */
public class UndoCommandParserTest {

    private UndoCommandParser parser = new UndoCommandParser();

    @Test
    public void parse_noArguments_success() {
        assertParseSuccess(parser, "", new UndoCommand());
    }

    @Test
    public void parse_emptyString_success() {
        assertParseSuccess(parser, "   ", new UndoCommand());
    }

    @Test
    public void parse_withArguments_throwsParseException() {
        // UndoCommand should not accept any arguments
        assertParseFailure(parser, "some random text", UndoCommand.MESSAGE_EXTRA_PARAMETERS);
    }
}
