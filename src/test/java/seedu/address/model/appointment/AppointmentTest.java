package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Appointment(null, null, null));
    }

    @Test
    public void equals() {
        AppointmentDatetime appointmentDatetime = new AppointmentDatetime("2025-01-01T00:00");
        Appointment appointment = new Appointment(appointmentDatetime, BENSON, ALICE);

        // same values -> returns true
        appointmentDatetime = new AppointmentDatetime("2025-01-01T00:00");
        assertTrue(appointment.equals(new Appointment(appointmentDatetime, BENSON, ALICE)));

        // same object -> returns true
        assertTrue(appointment.equals(appointment));

        // null -> returns false
        assertFalse(appointment.equals(null));

        // different types -> returns false
        assertFalse(appointment.equals(5.0f));

        // different datetime -> returns false
        appointmentDatetime = new AppointmentDatetime("2025-02-01T00:00");
        assertFalse(appointment.equals(new Appointment(appointmentDatetime, BENSON, ALICE)));

        // different seller -> returns false
        appointmentDatetime = new AppointmentDatetime("2025-01-01T00:00");
        assertFalse(appointment.equals(new Appointment(appointmentDatetime, CARL, ALICE)));

        // different buyer -> returns false
        assertFalse(appointment.equals(new Appointment(appointmentDatetime, BENSON, CARL)));

        // no buyer -> returns false
        assertFalse(appointment.equals(new Appointment(appointmentDatetime, BENSON)));

        // appointment with no buyer
        appointment = new Appointment(appointmentDatetime, BENSON);

        // same values -> returns true
        assertTrue(appointment.equals(new Appointment(appointmentDatetime, BENSON)));

        // different values -> returns false
        assertFalse(appointment.equals(new Appointment(appointmentDatetime, ALICE)));

        // compare to appointment with buyer -> returns false
        assertFalse(appointment.equals(new Appointment(appointmentDatetime, BENSON, ALICE)));


    }
}
