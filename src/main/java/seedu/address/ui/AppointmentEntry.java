package seedu.address.ui;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Represents an appointment entry that pairs an appointment with its associated person.
 * This is used for displaying appointments in the UI.
 */
public class AppointmentEntry {
    private final Appointment appointment;
    private final Person person;

    /**
     * Creates an AppointmentEntry with the given appointment and person.
     */
    public AppointmentEntry(Appointment appointment, Person person) {
        this.appointment = appointment;
        this.person = person;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Person getPerson() {
        return person;
    }
}
