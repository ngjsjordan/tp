package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

/**
 * Represents an appointment with a Person in the address book.
 * Guarantees: immutable; details are present and not null, field values are validated.}
 */
public class Appointment implements Comparable<Appointment> {

    public final AppointmentDatetime appointmentDatetime;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param appointmentDatetime An AppointmentDatetime object representing the datetime of the appointment.
     */
    public Appointment(AppointmentDatetime appointmentDatetime) {
        requireNonNull(appointmentDatetime);
        this.appointmentDatetime = appointmentDatetime;
    }

    public AppointmentDatetime getAppointmentDatetime() {
        return appointmentDatetime;
    }

    @Override
    public String toString() {
        return appointmentDatetime.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return appointmentDatetime.equals(otherAppointment.appointmentDatetime);
    }

    @Override
    public int compareTo(Appointment other) {
        return appointmentDatetime.compareTo(other.getAppointmentDatetime());
    }

    @Override
    public int hashCode() {
        return appointmentDatetime.hashCode();
    }

}
