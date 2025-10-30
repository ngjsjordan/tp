package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BUYER_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_JAN_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BUYER_DESC_SKELETON;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SELLER_DESC_SKELETON;
import static seedu.address.logic.commands.CommandTestUtil.SELLER_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATETIME_JAN_1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.model.appointment.AppointmentDatetime;

public class AddAppointmentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no datetime specified
        assertParseFailure(parser, SELLER_DESC_1 + BUYER_DESC_2, MESSAGE_INVALID_FORMAT);

        // no seller specified
        assertParseFailure(parser, DATETIME_DESC_JAN_1 + BUYER_DESC_2, MESSAGE_INVALID_FORMAT);

        // no datetime and no seller specified
        assertParseFailure(parser, BUYER_DESC_2, MESSAGE_INVALID_FORMAT);

        // no datetime, seller or buyer specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid datetime
        assertParseFailure(parser, INVALID_DATETIME_DESC + SELLER_DESC_1 + BUYER_DESC_2,
                AppointmentDatetime.MESSAGE_CONSTRAINTS);

        // invalid seller
        // negative index
        assertParseFailure(parser, DATETIME_DESC_JAN_1 + String.format(INVALID_SELLER_DESC_SKELETON, "-1")
                        + BUYER_DESC_2, MESSAGE_INVALID_INDEX);
        // zero index
        assertParseFailure(parser, DATETIME_DESC_JAN_1 + String.format(INVALID_SELLER_DESC_SKELETON, "0")
                        + BUYER_DESC_2, MESSAGE_INVALID_INDEX);
        // non-number index
        assertParseFailure(parser, DATETIME_DESC_JAN_1 + String.format(INVALID_SELLER_DESC_SKELETON, "hi")
                        + BUYER_DESC_2, MESSAGE_INVALID_INDEX);

        // invalid buyer
        // negative index
        assertParseFailure(parser, DATETIME_DESC_JAN_1 + SELLER_DESC_1
                        + String.format(INVALID_BUYER_DESC_SKELETON, "-1"), MESSAGE_INVALID_INDEX);
        // zero index
        assertParseFailure(parser, DATETIME_DESC_JAN_1 + SELLER_DESC_1
                        + String.format(INVALID_BUYER_DESC_SKELETON, "0"), MESSAGE_INVALID_INDEX);
        // non-number index
        assertParseFailure(parser, DATETIME_DESC_JAN_1 + SELLER_DESC_1
                        + String.format(INVALID_BUYER_DESC_SKELETON, "hi"), MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_appointmentWithBuyerAccepted_success() {
        String userInput = DATETIME_DESC_JAN_1 + SELLER_DESC_1 + BUYER_DESC_2;

        AddAppointmentCommand expectedCommand = new AddAppointmentCommand(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1), INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_appointmentWithoutBuyerAccepted_success() {
        String userInput = DATETIME_DESC_JAN_1 + SELLER_DESC_1;

        AddAppointmentCommand expectedCommand = new AddAppointmentCommand(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1), INDEX_FIRST_PERSON);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // valid followed by invalid
        String userInput = DATETIME_DESC_JAN_1 + INVALID_DATETIME_DESC + SELLER_DESC_1 + BUYER_DESC_2;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATETIME));

        // invalid followed by valid
        userInput = INVALID_DATETIME_DESC + DATETIME_DESC_JAN_1 + SELLER_DESC_1 + BUYER_DESC_2;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATETIME));

        // multiple valid fields repeated
        userInput = DATETIME_DESC_JAN_1 + DATETIME_DESC_JAN_1 + SELLER_DESC_1 + BUYER_DESC_2;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATETIME));

        // multiple invalid values
        userInput = INVALID_DATETIME_DESC + INVALID_DATETIME_DESC + SELLER_DESC_1 + BUYER_DESC_2;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATETIME));
    }
}
