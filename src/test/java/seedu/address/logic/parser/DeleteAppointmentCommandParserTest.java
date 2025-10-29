package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.testutil.TypicalIndexes;

public class DeleteAppointmentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE);

    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = String.valueOf(TypicalIndexes.INDEX_FIRST_PERSON.getOneBased());

        DeleteAppointmentCommand expectedCommand = new DeleteAppointmentCommand(
                INDEX_FIRST_PERSON);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingIndex_failure() {
        // no index
        assertParseFailure(parser, VALID_NAME_BOB, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingField_failure() {
        // no field
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_whitespacePreamble_success() throws Exception {
        // allows leading whitespace
        String userInput = PREAMBLE_WHITESPACE + INDEX_FIRST_PERSON.getOneBased();

        DeleteAppointmentCommand expectedCommand = new DeleteAppointmentCommand(
                INDEX_FIRST_PERSON);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + INDEX_FIRST_PERSON.getOneBased();

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }
}
