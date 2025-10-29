package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.SearchAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentContainsKeywordsPredicate;
import seedu.address.model.appointment.TimeFrame;

/**
 * Parses input arguments and creates a new SearchAppointmentCommand object
 */
public class SearchAppointmentCommandParser implements Parser<SearchAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchAppointmentCommand
     * and returns a SearchAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TIME);

        String preamble = argMultimap.getPreamble().trim();
        Optional<String> timeFrameValue = argMultimap.getValue(PREFIX_TIME);

        // Parse timeframe and extract any additional keywords from tf/ value
        Optional<TimeFrame> timeFrame = Optional.empty();
        List<String> additionalKeywords = Arrays.asList();

        if (timeFrameValue.isPresent()) {
            String timeValueStr = timeFrameValue.get().trim();
            String[] timeValueParts = timeValueStr.split("\\s+", 2); // Split into max 2 parts
            String timeFrameStr = timeValueParts[0];

            if (!TimeFrame.isValidTimeFrame(timeFrameStr)) {
                throw new ParseException(TimeFrame.MESSAGE_CONSTRAINTS);
            }
            timeFrame = Optional.of(TimeFrame.fromString(timeFrameStr));

            // If there are words after the timeframe, treat them as keywords
            if (timeValueParts.length > 1 && !timeValueParts[1].trim().isEmpty()) {
                additionalKeywords = Arrays.asList(timeValueParts[1].trim().split("\\s+"));
            }
        }

        // Combine preamble keywords with any additional keywords from tf/ value
        List<String> allKeywords = new ArrayList<>();
        if (!preamble.isEmpty()) {
            allKeywords.addAll(Arrays.asList(preamble.split("\\s+")));
        }
        allKeywords.addAll(additionalKeywords);

        // Validate that at least keywords or timeframe is provided
        if (allKeywords.isEmpty() && timeFrame.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchAppointmentCommand.MESSAGE_USAGE));
        }

        // Verify no duplicate tf/ prefix
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TIME);

        return new SearchAppointmentCommand(
                new AppointmentContainsKeywordsPredicate(allKeywords, timeFrame));
    }
}
