package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Represents an appointment in the address book.
 * Guarantees: immutable; details are present not null; field values are validated.
 *
 */
public class Appointment implements Comparable<Appointment> {

    public final AppointmentDatetime appointmentDatetime;
    public final Person seller;
    public final Person buyer;

    /**
     * Constructs an {@code Appointment} with a buyer.
     *
     * @param appointmentDatetime An AppointmentDatetime object representing the datetime of the appointment.
     * @param seller A Person object representing the seller.
     * @param buyer A Person object representing the buyer.
     */
    public Appointment(AppointmentDatetime appointmentDatetime, Person seller, Person buyer) {
        requireNonNull(appointmentDatetime);
        requireNonNull(seller);
        requireNonNull(buyer);
        this.appointmentDatetime = appointmentDatetime;
        this.seller = seller;
        this.buyer = buyer;
    }

    /**
     * Constructs an {@code Appointment} without a buyer.
     *
     * @param appointmentDatetime An AppointmentDatetime object representing the datetime of the appointment.
     * @param seller A Person object representing the seller.
     */
    public Appointment(AppointmentDatetime appointmentDatetime, Person seller) {
        requireNonNull(appointmentDatetime);
        requireNonNull(seller);
        this.appointmentDatetime = appointmentDatetime;
        this.seller = seller;
        this.buyer = null;
    }

    public AppointmentDatetime getAppointmentDatetime() {
        return appointmentDatetime;
    }

    public Person getSeller() {
        return seller;
    }

    public Optional<Person> getBuyer() {
        return Optional.ofNullable(buyer);
    }

    /**
     * Returns true if the given {@code person} is the seller of this appointment.
     *
     * @param person Person to compare.
     * @return true if {@code person} is the seller, otherwise false.
     */
    public boolean isPersonSeller(Person person) {
        return seller.equals(person);
    }

    /**
     * Returns true if the given {@code person} is the buyer of this appointment. If person is null, will return false,
     * even if the appointment has no buyer.
     *
     * @param person Person to compare.
     * @return true if {@code person} is the buyer, otherwise false. If person is null, also returns false.
     */
    public boolean isPersonBuyer(Person person) {
        return buyer != null && buyer.equals(person);
    }

    /**
     * Returns a new appointment with the details of this one, but with {code target} replaced by {@code editedPerson}.
     *
     * @param target Person object to be replaced.
     * @param editedPerson Person object to replace with.
     * @return A new Appointment object which replaces {@code target} with {@code editedPerson}.
     */
    public Appointment updatedWithEditedPerson(Person target, Person editedPerson) {
        Person updatedSeller = isPersonSeller(target) ? editedPerson : seller;
        Person updatedBuyer = isPersonBuyer(target) ? editedPerson : buyer;

        if (updatedBuyer == null) {
            return new Appointment(appointmentDatetime, updatedSeller);
        } else {
            return new Appointment(appointmentDatetime, updatedSeller, updatedBuyer);
        }
    }

    /**
     * Returns the storage identifier of the seller.
     *
     * @return String representing the storage identifier of the seller.
     */
    public String getSellerStorageIdentifier() {
        return seller.getStorageIdentifier();
    }

    /**
     * Returns the storage identifier of the buyer, if present.
     *
     * @return Optional string representing the storage identifier of the buyer.
     */
    public Optional<String> getBuyerStorageIdentifier() {
        return Optional.ofNullable(buyer).map(Person::getStorageIdentifier);
    }

    /**
     * Returns true if any field of this appointment contains the given keyword (case-insensitive).
     * Searches across seller name, seller phone, buyer name, buyer phone, seller address,
     * and appointment datetime.
     */
    public boolean containsKeyword(String keyword) {
        return seller.containsKeywordInName(keyword)
                || seller.containsKeywordInPhone(keyword)
                || seller.containsKeywordInAddress(keyword)
                || (buyer != null && buyer.containsKeywordInName(keyword))
                || (buyer != null && buyer.containsKeywordInPhone(keyword))
                || StringUtil.containsWordIgnoreCase(appointmentDatetime.toString(), keyword);
    }

    @Override
    public String toString() {
        return appointmentDatetime.toString() + ", Seller: " + seller.getName()
                + (buyer != null ? ", Buyer: " + buyer.getName() : "");
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
        return appointmentDatetime.equals(otherAppointment.appointmentDatetime)
                && seller.equals(otherAppointment.seller)
                && Objects.equals(buyer, otherAppointment.buyer);
    }

    @Override
    public int compareTo(Appointment other) {
        return appointmentDatetime.compareTo(other.getAppointmentDatetime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentDatetime, seller, buyer);
    }

}
