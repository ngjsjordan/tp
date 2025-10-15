package seedu.address.model.appointment.exceptions;

/**
 * Signals that the operation will result in duplicate Appointment
 */
public class DuplicateAppointmentException extends RuntimeException {
    public DuplicateAppointmentException() {
        super("Operation would result in duplicate appointments");
    }
}
