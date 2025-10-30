package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentDatetime;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_SELLER, PREFIX_BUYER);

        Index sellerIndex;
        Index buyerIndex;
        AppointmentDatetime appointmentDatetime;

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATETIME, PREFIX_SELLER, PREFIX_BUYER);

        if (!argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_SELLER).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        appointmentDatetime = ParserUtil.parseAppointmentDatetime(argMultimap.getValue(PREFIX_DATETIME).get());
        sellerIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_SELLER).get());

        if (argMultimap.getValue(PREFIX_BUYER).isPresent()) {
            buyerIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_BUYER).get());
        } else {
            buyerIndex = null;
        }

        if (buyerIndex != null) {
            return new AddAppointmentCommand(appointmentDatetime, sellerIndex, buyerIndex);
        } else {
            return new AddAppointmentCommand(appointmentDatetime, sellerIndex);
        }
    }
}
