package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * Accepts either:
     * - prefixed form: n/KEYWORD [MORE_KEYWORDS]..., ic/IC_NUMBER, p/PHONE_NUMBER
     * - legacy form: KEYWORD [MORE_KEYWORDS]... (no prefix used)
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_IC, PREFIX_PHONE);

        boolean hasName = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean hasIc = argMultimap.getValue(PREFIX_IC).isPresent();
        boolean hasPhone = argMultimap.getValue(PREFIX_PHONE).isPresent();

        // Legacy behaviour: no prefixes, treat entire args as name keywords
        if (!hasName && !hasIc && !hasPhone) {
            List<String> legacyKeywords = Arrays.asList(trimmedArgs.split("\\s+"));
            return new FindCommand(new NameContainsKeywordsPredicate(legacyKeywords));
        }

        Predicate<Person> predicate = person -> false;

        if (hasName) {
            String nameArgs = argMultimap.getValue(PREFIX_NAME).get().trim();
            if (nameArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            List<String> nameKeywords = Arrays.asList(nameArgs.split("\\s+"));
            predicate = predicate.or(new NameContainsKeywordsPredicate(nameKeywords));
        }

        if (hasIc) {
            String icArg = argMultimap.getValue(PREFIX_IC).get().trim();
            if (icArg.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            String icToMatch = icArg;
            predicate = predicate.or(person -> person.getIc().value.equalsIgnoreCase(icToMatch));
        }

        if (hasPhone) {
            String phoneArg = argMultimap.getValue(PREFIX_PHONE).get().trim();
            if (phoneArg.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            String phoneToMatch = phoneArg;
            predicate = predicate.or(person -> person.getPhone().value.equals(phoneToMatch));
        }

        return new FindCommand(predicate);
    }

}
