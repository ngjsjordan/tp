package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_APPOINTMENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;

public class DeleteAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validAppointment_success() {
        // Test deleting a valid appointment from the appointment list
        Appointment appointmentToDelete = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentToDelete.toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deleteAppointment(appointmentToDelete);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(outOfBoundIndex);

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT);

        // same values -> returns true
        DeleteAppointmentCommand commandWithSameValues =
                new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT);
        assertTrue(deleteAppointmentCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(deleteAppointmentCommand.equals(deleteAppointmentCommand));

        // null -> returns false
        assertFalse(deleteAppointmentCommand.equals(null));

        // different types -> returns false
        assertFalse(deleteAppointmentCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(deleteAppointmentCommand.equals(
                new DeleteAppointmentCommand(INDEX_SIXTH_APPOINTMENT)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(index);
        String expected = DeleteAppointmentCommand.class.getCanonicalName()
                + "{index=" + index + "}";
        assertEquals(expected, deleteAppointmentCommand.toString());
    }
}
