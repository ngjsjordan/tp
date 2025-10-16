package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATETIME_DEC_31;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATETIME_JAN_1;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
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

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddAppointmentCommand.
 */
public class AddAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_appointmentAcceptedByModel_success() {
        Person seller = new PersonBuilder(model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased())).build();
        Person buyer = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())).build();
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1), INDEX_THIRD_PERSON, INDEX_FIRST_PERSON);

        String expectedMessage = String.format(AddAppointmentCommand.MESSAGE_ADD_APPOINTMENT_SUCCESS,
                Messages.format(seller));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addAppointment(new Appointment(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1),
                seller, buyer));

        assertCommandSuccess(addAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1), outOfBoundIndex, outOfBoundIndex);

        assertCommandFailure(addAppointmentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidSellerRole_failure() {
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1), INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);

        assertCommandFailure(addAppointmentCommand, model, AddAppointmentCommand.MESSAGE_INVALID_SELLER_ROLE);
    }

    @Test
    public void execute_invalidBuyerRole_failure() {
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1), INDEX_THIRD_PERSON, INDEX_SIXTH_PERSON);

        assertCommandFailure(addAppointmentCommand, model, AddAppointmentCommand.MESSAGE_INVALID_BUYER_ROLE);
    }

    @Test
    public void execute_samePersonBuyerSeller_failure() {
        // Using the same person for both buyer and seller
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1), INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);

        assertCommandFailure(addAppointmentCommand, model, AddAppointmentCommand.MESSAGE_SAME_PERSON);
    }

    @Test
    public void equals() {
        final AddAppointmentCommand standardCommand = new AddAppointmentCommand(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1), INDEX_THIRD_PERSON, INDEX_FIRST_PERSON);

        // same values -> returns true
        AddAppointmentCommand commandWithSameValues = new AddAppointmentCommand(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1), INDEX_THIRD_PERSON, INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddAppointmentCommand(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1), INDEX_FIRST_PERSON, INDEX_THIRD_PERSON)));

        // different datetime -> returns false
        assertFalse(standardCommand.equals(new AddAppointmentCommand(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_DEC_31), INDEX_THIRD_PERSON, INDEX_FIRST_PERSON)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(
                new AppointmentDatetime(VALID_APPOINTMENT_DATETIME_JAN_1), index, index);
        String expected = AddAppointmentCommand.class.getCanonicalName()
                + "{appointmentDatetime=" + VALID_APPOINTMENT_DATETIME_JAN_1 + ", sellerIndex="
                + index + ", buyerIndex=" + index + "}";
        assertEquals(expected, addAppointmentCommand.toString());
    }

}
