package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDatetime;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    // Fixed date constants for testing
    // Mock "today" as a fixed date for consistent test results
    public static final String MOCK_TODAY_DATE = "2025-10-29";

    // Appointments - Past (before mock today: 2025-10-29)
    public static final Appointment FIONA_ELLE_1 = new Appointment(
            new AppointmentDatetime("2025-01-01T12:00"), FIONA, ELLE);
    public static final Appointment FIONA_DANIEL = new Appointment(
            new AppointmentDatetime("2025-01-02T12:00"), FIONA, DANIEL);
    public static final Appointment FIONA_NOBUYER = new Appointment(
            new AppointmentDatetime("2025-01-03T12:00"), FIONA);
    public static final Appointment CARL_ALICE_PAST = new Appointment(
            new AppointmentDatetime("2025-10-15T10:00"), CARL, ALICE);

    // Appointments - Today (mock today: 2025-10-29)
    public static final Appointment FIONA_BENSON_TODAY = new Appointment(
            new AppointmentDatetime(MOCK_TODAY_DATE + "T15:30"), FIONA, BENSON);

    // Appointments - Upcoming (after mock today: 2025-10-29)
    public static final Appointment CARL_ELLE_UPCOMING = new Appointment(
            new AppointmentDatetime("2025-11-01T14:00"), CARL, ELLE);
    public static final Appointment GEORGE_ALICE_UPCOMING = new Appointment(
            new AppointmentDatetime("2025-12-15T11:00"), GEORGE, ALICE);

    private TypicalAppointments() {} // prevents instantiation

    /**
     * Returns a list of typical appointments for testing.
     * The list includes appointments from past, today (mock date: 2025-10-29), and upcoming.
     * All appointments use fixed dates to ensure consistent test results.
     */
    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(
                FIONA_ELLE_1, FIONA_DANIEL, FIONA_NOBUYER, CARL_ALICE_PAST,
                FIONA_BENSON_TODAY, CARL_ELLE_UPCOMING, GEORGE_ALICE_UPCOMING));
    }
}
