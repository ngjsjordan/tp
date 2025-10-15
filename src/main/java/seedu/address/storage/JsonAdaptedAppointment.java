package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDatetime;

/**
 * Json-friendly version of {@link Appointment}.
 */
class JsonAdaptedAppointment {

    private final String appointmentDateTime;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code appointmentDateTime}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(String appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    /**
     * Converts a given {@code Appointment} into this class for Json use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        appointmentDateTime = source.getAppointmentDatetime().toString();
    }

    @JsonValue
    public String getAppointmentDateTime() {
        return appointmentDateTime;
    }

    /**
     * Converts this Json-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Appointment toModelType() throws IllegalValueException {
        if (!AppointmentDatetime.isValidDatetime(appointmentDateTime)) {
            throw new IllegalValueException(AppointmentDatetime.MESSAGE_CONSTRAINTS);
        }
        return new Appointment(new AppointmentDatetime(appointmentDateTime));
    }

}
