package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        String roleFilter = null;
        String locationFilter = null;
        List<String> keywords = new ArrayList<>();

        for (String keyword : nameKeywords) {
            if (keyword.equals("r/buyer")) {
                roleFilter = "buyer";
            } else if (keyword.equals("r/seller")) {
                roleFilter = "seller";
            } else if (keyword.startsWith("l/")) {
                locationFilter = keyword.substring(2);
            } else {
                keywords.add(keyword);
            }
        }

        return new FindCommand(new NameContainsKeywordsPredicate(keywords, roleFilter, locationFilter));
    }

}
