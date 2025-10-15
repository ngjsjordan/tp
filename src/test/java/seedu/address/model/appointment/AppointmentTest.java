package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.PersonBuilder;

public class AppointmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Appointment(null, null, null));
    }

    @Test
    public void equals() {
        AppointmentDatetime appointmentDatetime = new AppointmentDatetime("2025-01-01T00:00");
        Appointment appointment = new Appointment(appointmentDatetime, BENSON, null);

        // same values -> returns true
        appointmentDatetime = new AppointmentDatetime("2025-01-01T00:00");
        assertTrue(appointment.equals(new Appointment(appointmentDatetime, BENSON, null)));

        // same object -> returns true
        assertTrue(appointment.equals(appointment));

        // null -> returns false
        assertFalse(appointment.equals(null));

        // different types -> returns false
        assertFalse(appointment.equals(5.0f));

        // different values -> returns false
        appointmentDatetime = new AppointmentDatetime("2025-02-01T00:00");
        assertFalse(appointment.equals(new Appointment(appointmentDatetime, BENSON, null)));
    }
}
