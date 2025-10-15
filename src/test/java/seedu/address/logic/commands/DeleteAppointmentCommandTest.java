package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATETIME_DEC_31;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATETIME_JAN_1;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDatetime;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validAppointment_success() {
        // Person with two appointments
        Person personWithAppointments = new PersonBuilder(model.getFilteredPersonList().get(0))
                .withAppointments(VALID_APPOINTMENT_DATETIME_JAN_1, VALID_APPOINTMENT_DATETIME_DEC_31)
                .build();

        model.setPerson(model.getFilteredPersonList().get(0), personWithAppointments);

        Appointment appointmentToRemove = new Appointment(new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1));
        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(INDEX_FIRST_PERSON, appointmentToRemove);

        // Expected edited person (after removing one appointment)
        Person editedPerson = new PersonBuilder(personWithAppointments)
                .withAppointments(VALID_APPOINTMENT_DATETIME_DEC_31)
                .build();

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentToRemove, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personWithAppointments, editedPerson);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_appointmentDoesNotExist_failure() {
        Person personWithoutAppointment = new PersonBuilder(model.getFilteredPersonList().get(0))
                .withAppointments(VALID_APPOINTMENT_DATETIME_JAN_1)
                .build();

        model.setPerson(model.getFilteredPersonList().get(0), personWithoutAppointment);

        Appointment nonExistentAppointment = new Appointment(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_DEC_31));
        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(INDEX_FIRST_PERSON, nonExistentAppointment);

        assertCommandFailure(deleteAppointmentCommand, model,
                String.format(DeleteAppointmentCommand.MESSAGE_NO_APPOINTMENT_TO_DELETE, nonExistentAppointment,
                        personWithoutAppointment.getName()));
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(outOfBoundIndex,
                new Appointment(new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1)));

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(INDEX_FIRST_PERSON,
                new Appointment(new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1)));

        // same values -> returns true
        DeleteAppointmentCommand commandWithSameValues = new DeleteAppointmentCommand(INDEX_FIRST_PERSON,
                new Appointment(new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1)));
        assertTrue(deleteAppointmentCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(deleteAppointmentCommand.equals(deleteAppointmentCommand));

        // null -> returns false
        assertFalse(deleteAppointmentCommand.equals(null));

        // different types -> returns false
        assertFalse(deleteAppointmentCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(deleteAppointmentCommand.equals(new DeleteAppointmentCommand(INDEX_SECOND_PERSON,
                new Appointment(new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1)))));

        // different descriptor -> returns false
        assertFalse(deleteAppointmentCommand.equals(new DeleteAppointmentCommand(INDEX_FIRST_PERSON,
                new Appointment(new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_DEC_31)))));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        Appointment appointment = new Appointment(new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1));
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(index, appointment);
        String expected = DeleteAppointmentCommand.class.getCanonicalName() + "{index=" + index + ", appointment="
                + appointment + "}";
        assertEquals(expected, deleteAppointmentCommand.toString());
    }
}
