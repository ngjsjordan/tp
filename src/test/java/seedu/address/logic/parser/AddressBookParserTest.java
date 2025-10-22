package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.BUYER_DESC_2;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
// import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAppointmentsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ToggleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
// import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDatetime;
import seedu.address.model.person.DetailsContainKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        System.out.println("Descriptor: " + descriptor);
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        System.out.println("PersonUtil.getEditPerson: " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        System.out.println("Command: " + command);
        System.out.println("New Command: " + new EditCommand(INDEX_FIRST_PERSON, descriptor));

        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_toggleTheme() throws Exception {
        assertTrue(parser.parseCommand(ToggleCommand.COMMAND_WORD) instanceof ToggleCommand);
        assertTrue(parser.parseCommand(ToggleCommand.COMMAND_WORD + " 3") instanceof ToggleCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new DetailsContainKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_lap() throws Exception {
        assertTrue(parser.parseCommand(ListAppointmentsCommand.COMMAND_WORD) instanceof ListAppointmentsCommand);
        assertTrue(parser.parseCommand(ListAppointmentsCommand.COMMAND_WORD + " 3")
                instanceof ListAppointmentsCommand);
    }

    @Test
    public void parseCommand_ap() throws Exception {
        String datetime = "2025-01-01T12:00";
        AddAppointmentCommand command = (AddAppointmentCommand) parser.parseCommand(
                AddAppointmentCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + "d/" + datetime + BUYER_DESC_2);
        assertEquals(new AddAppointmentCommand(new AppointmentDatetime(datetime), INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON), command);
    }

    /*
    * @Test
    * public void parseCommand_dap() throws Exception {
    *    String datetime = "2025-01-01T12:00";
    *    DeleteAppointmentCommand command = (DeleteAppointmentCommand) parser.parseCommand(
    *            DeleteAppointmentCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
    *                    + "d/" + datetime);
    *    assertEquals(new DeleteAppointmentCommand(INDEX_FIRST_PERSON,
    *            new Appointment(new AppointmentDatetime(datetime))), command);
    }*/

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
