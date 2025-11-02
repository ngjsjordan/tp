package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Represents the datetime of an appointment.
 * Guarantees: immutable; is valid as declared in {@link #isValidDatetime(String)}
 */
public class AppointmentDatetime implements Comparable<AppointmentDatetime> {

    public static final String MESSAGE_CONSTRAINTS =
            "Appointment Datetime must take a valid ISO 8601 datetime (e.g. 2025-01-01T00:00)";

    public final LocalDateTime datetime;

    /**
     * Constructs an {@code AppointmentDatetime}.
     *
     * @param datetimeStr A String that can be parsed into a valid LocalDateTime.
     */
    public AppointmentDatetime(String datetimeStr) {
        requireNonNull(datetimeStr);
        checkArgument(isValidDatetime(datetimeStr), MESSAGE_CONSTRAINTS);
        datetime = LocalDateTime.parse(datetimeStr);
    }

    /**
     * Returns true if a given string is a valid datetime.
     */
    public static boolean isValidDatetime(String test) {
        try {
            LocalDateTime.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return datetime.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentDatetime)) {
            return false;
        }

        AppointmentDatetime otherAppointmentDatetime = (AppointmentDatetime) other;
        return datetime.equals(otherAppointmentDatetime.datetime);
    }

    @Override
    public int compareTo(AppointmentDatetime other) {
        return datetime.compareTo(other.datetime);
    }

    @Override
    public int hashCode() {
        return datetime.hashCode();
    }

}
