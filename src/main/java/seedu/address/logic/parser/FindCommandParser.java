package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.ConjunctiveContactPredicateSet;
import seedu.address.model.contact.Contact;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getPreamble().isEmpty()
                && argMultimap.getAllValues(PREFIX_NAME).isEmpty()
                && argMultimap.getAllValues(PREFIX_PHONE).isEmpty()
                && argMultimap.getAllValues(PREFIX_EMAIL).isEmpty()
                && argMultimap.getAllValues(PREFIX_ADDRESS).isEmpty()
                && argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ConjunctiveContactPredicateSet cumulativePredicate = new ConjunctiveContactPredicateSet();
        splitKeywords(argMultimap.getPreamble()).forEach(
                        keyword -> cumulativePredicate.addPredicate((Contact contact) -> contact.contains(keyword)));
        splitKeywords(String.join(" ", argMultimap.getAllValues(PREFIX_NAME))).forEach(
                keyword -> cumulativePredicate.addPredicate((Contact contact) -> contact.containsInName(keyword)));
        splitKeywords(String.join(" ", argMultimap.getAllValues(PREFIX_PHONE))).forEach(
                keyword -> cumulativePredicate.addPredicate((Contact contact) -> contact.containsInPhone(keyword)));
        splitKeywords(String.join(" ", argMultimap.getAllValues(PREFIX_EMAIL))).forEach(
                keyword -> cumulativePredicate.addPredicate((Contact contact) -> contact.containsInEmail(keyword)));
        splitKeywords(String.join(" ", argMultimap.getAllValues(PREFIX_ADDRESS))).forEach(
                keyword -> cumulativePredicate.addPredicate((Contact contact) -> contact.containsInAddress(keyword)));
        splitKeywords(String.join(" ", argMultimap.getAllValues(PREFIX_TAG))).forEach(
                keyword -> cumulativePredicate.addPredicate((Contact contact) -> contact.hasTag(keyword)));

        return new FindCommand(cumulativePredicate);
    }

    /**
     * Splits a raw keyword string by whitespace and drops empty tokens.
     */
    private List<String> splitKeywords(String rawKeywords) {
        return Arrays.stream(rawKeywords.trim().split("\\s+"))
                .filter(keyword -> !keyword.isBlank())
                .collect(Collectors.toList());
    }
}
