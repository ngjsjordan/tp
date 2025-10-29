package seedu.address.storage;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDatetime;
import seedu.address.model.person.Person;

/**
 * Json-friendly version of {@link Appointment}.
 */
class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";
    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedAppointment.class);

    private final String appointmentDateTime;
    private final String seller;
    private final String buyer;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code appointmentDateTime}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("datetime") String appointmentDateTime,
            @JsonProperty("seller") String seller, @JsonProperty("buyer") String buyer) {
        this.appointmentDateTime = appointmentDateTime;
        this.seller = seller;
        this.buyer = buyer;
    }

    /**
     * Converts a given {@code Appointment} into this class for Json use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        appointmentDateTime = source.getAppointmentDatetime().toString();
        seller = source.getSellerStorageIdentifier();
        buyer = source.getBuyerStorageIdentifier().orElse(null);
    }

    public String getSeller() {
        return seller;
    }

    public String getBuyer() {
        return buyer;
    }

    /**
     * Converts this Json-friendly adapted appointment object into the model's {@code Appointment} object, given
     * references to the buyer and seller objects.
     *
     * @param seller the Person object representing the seller of the appointment
     * @param buyer the Person object representing the buyer of the appointment. Can be null.
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Appointment toModelType(Person seller, Person buyer) throws IllegalValueException {
        logger.fine("Converting JsonAdaptedAppointment to Model Appointment with seller: " + seller
                + " and buyer: " + buyer + " at " + appointmentDateTime);

        if (appointmentDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentDatetime.class.getSimpleName()));
        }

        if (!AppointmentDatetime.isValidDatetime(appointmentDateTime)) {
            throw new IllegalValueException(AppointmentDatetime.MESSAGE_CONSTRAINTS);
        }

        if (seller == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Person.class.getSimpleName()));
        }

        if (buyer == null) {
            return new Appointment(new AppointmentDatetime(appointmentDateTime), seller);
        } else {
            return new Appointment(new AppointmentDatetime(appointmentDateTime), seller, buyer);
        }
    }
}
