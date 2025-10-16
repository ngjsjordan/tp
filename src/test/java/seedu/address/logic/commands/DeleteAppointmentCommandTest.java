package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.FIONA_ELLE_1;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.AppointmentDatetime;

public class DeleteAppointmentCommandTest {

    private static final Index INDEX_FIFTH_PERSON = Index.fromOneBased(5);
    private static final Index INDEX_SIXTH_PERSON = Index.fromOneBased(6);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validAppointmentSeller_success() {
        // FIONA_ELLE_1 has FIONA as seller (index 6), ELLE as buyer (index 5)
        // Delete using seller's index
        AppointmentDatetime datetimeToDelete = new AppointmentDatetime("2025-01-01T12:00");
        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(INDEX_SIXTH_PERSON, datetimeToDelete);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                FIONA_ELLE_1, Messages.format(FIONA));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deleteAppointment(FIONA_ELLE_1);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validAppointmentBuyer_success() {
        // FIONA_ELLE_1 has FIONA as seller (index 6), ELLE as buyer (index 5)
        // Delete using buyer's index
        AppointmentDatetime datetimeToDelete = new AppointmentDatetime("2025-01-01T12:00");
        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(INDEX_FIFTH_PERSON, datetimeToDelete);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                FIONA_ELLE_1, Messages.format(model.getFilteredPersonList().get(INDEX_FIFTH_PERSON.getZeroBased())));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deleteAppointment(FIONA_ELLE_1);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_appointmentDoesNotExist_failure() {
        // Try to delete an appointment that doesn't exist
        AppointmentDatetime nonExistentDatetime = new AppointmentDatetime("2025-12-31T23:59");
        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(INDEX_FIRST_PERSON, nonExistentDatetime);

        assertCommandFailure(deleteAppointmentCommand, model,
                String.format(DeleteAppointmentCommand.MESSAGE_NO_APPOINTMENT_TO_DELETE, nonExistentDatetime,
                        Messages.format(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))));
    }

    @Test
    public void execute_personNotInvolvedInAppointment_failure() {
        // FIONA_ELLE_1 involves FIONA (index 6) and ELLE (index 5)
        // Try to delete with ALICE's index (index 1) who is not involved
        AppointmentDatetime datetime = new AppointmentDatetime("2025-01-01T12:00");
        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(INDEX_FIRST_PERSON, datetime);

        assertCommandFailure(deleteAppointmentCommand, model,
                String.format(DeleteAppointmentCommand.MESSAGE_NO_APPOINTMENT_TO_DELETE, datetime,
                        Messages.format(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))));
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AppointmentDatetime datetime = new AppointmentDatetime("2025-01-01T12:00");
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(outOfBoundIndex, datetime);

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AppointmentDatetime datetime1 = new AppointmentDatetime("2025-01-01T12:00");
        AppointmentDatetime datetime2 = new AppointmentDatetime("2025-12-31T12:00");

        final DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(INDEX_FIRST_PERSON, datetime1);

        // same values -> returns true
        DeleteAppointmentCommand commandWithSameValues =
                new DeleteAppointmentCommand(INDEX_FIRST_PERSON, datetime1);
        assertTrue(deleteAppointmentCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(deleteAppointmentCommand.equals(deleteAppointmentCommand));

        // null -> returns false
        assertFalse(deleteAppointmentCommand.equals(null));

        // different types -> returns false
        assertFalse(deleteAppointmentCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(deleteAppointmentCommand.equals(
                new DeleteAppointmentCommand(INDEX_SIXTH_PERSON, datetime1)));

        // different datetime -> returns false
        assertFalse(deleteAppointmentCommand.equals(
                new DeleteAppointmentCommand(INDEX_FIRST_PERSON, datetime2)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        AppointmentDatetime datetime = new AppointmentDatetime("2025-01-01T12:00");
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(index, datetime);
        String expected = DeleteAppointmentCommand.class.getCanonicalName()
                + "{index=" + index + ", appointmentDatetime=" + datetime + "}";
        assertEquals(expected, deleteAppointmentCommand.toString());
    }
}
