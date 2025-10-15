package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AppointmentDatetimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppointmentDatetime(null));
    }

    @Test
    public void constructor_invalidAppointment_throwsIllegalArgumentException() {
        String invalidAppointmentDatetime = "";
        assertThrows(IllegalArgumentException.class, () -> new AppointmentDatetime(invalidAppointmentDatetime));
    }

    @Test
    public void isValidDatetime() {
        // null datetime
        assertThrows(NullPointerException.class, () -> AppointmentDatetime.isValidDatetime(null));

        // blank datetime
        assertFalse(AppointmentDatetime.isValidDatetime("")); // empty string
        assertFalse(AppointmentDatetime.isValidDatetime(" ")); // spaces only

        // missing parts
        assertFalse(AppointmentDatetime.isValidDatetime("2025-01-01T15")); // missing minutes
        assertFalse(AppointmentDatetime.isValidDatetime("2025-01-01")); // missing time
        assertFalse(AppointmentDatetime.isValidDatetime("15:00")); // missing date
        assertFalse(AppointmentDatetime.isValidDatetime("2025-01-0115:00")); // missing separator

        // extra parts
        assertFalse(AppointmentDatetime.isValidDatetime("2025-01-01T15:00.000")); // extra milliseconds

        // invalid parts
        assertFalse(AppointmentDatetime.isValidDatetime("20025-01-01T15:00")); // invalid year
        assertFalse(AppointmentDatetime.isValidDatetime("2025-9-01T15:00")); // invalid month - too few digits
        assertFalse(AppointmentDatetime.isValidDatetime("2025-100-01T15:00")); // invalid month - too many digits
        assertFalse(AppointmentDatetime.isValidDatetime("2025-13-01T15:00")); // invalid month
        assertFalse(AppointmentDatetime.isValidDatetime("2025-01-1T15:00")); // invalid day - too few digits
        assertFalse(AppointmentDatetime.isValidDatetime("2025-01-111T15:00")); // invalid day - too many digits
        assertFalse(AppointmentDatetime.isValidDatetime("2025-01-321T15:00")); // invalid day
        assertFalse(AppointmentDatetime.isValidDatetime("2025-01-01T1:00")); // invalid hour - too few digits
        assertFalse(AppointmentDatetime.isValidDatetime("2025-01-01T111:00")); // invalid hour - too many digits
        assertFalse(AppointmentDatetime.isValidDatetime("2025-01-01T99:00")); // invalid hour
        assertFalse(AppointmentDatetime.isValidDatetime("2025-01-01T00:0")); // invalid minute - too few digits
        assertFalse(AppointmentDatetime.isValidDatetime("2025-01-01T00:000")); // invalid minute - too many digits
        assertFalse(AppointmentDatetime.isValidDatetime("2025-01-01T00:61")); // invalid minute

        // valid datetime
        assertTrue(AppointmentDatetime.isValidDatetime("2025-01-01T00:00")); // first month
        assertTrue(AppointmentDatetime.isValidDatetime("2025-12-01T00:00")); // last month
        assertTrue(AppointmentDatetime.isValidDatetime("2025-01-31T00:00")); // last day of month
        assertTrue(AppointmentDatetime.isValidDatetime("2025-01-01T23:00")); // last hour of day
        assertTrue(AppointmentDatetime.isValidDatetime("2025-01-01T00:59")); // last minute of hour
        assertTrue(AppointmentDatetime.isValidDatetime("2024-02-29T00:59")); // leap year
    }

    @Test
    public void equals() {
        AppointmentDatetime appointmentDatetime = new AppointmentDatetime("2025-01-01T00:00");

        // same values -> returns true
        assertTrue(appointmentDatetime.equals(new AppointmentDatetime("2025-01-01T00:00")));

        // same object -> returns true
        assertTrue(appointmentDatetime.equals(appointmentDatetime));

        // null -> returns false
        assertFalse(appointmentDatetime.equals(null));

        // different types -> returns false
        assertFalse(appointmentDatetime.equals(5.0f));

        // different values -> returns false
        assertFalse(appointmentDatetime.equals(new AppointmentDatetime("2025-02-01T00:00")));
    }
}
