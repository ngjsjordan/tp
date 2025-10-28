package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "clientsquare")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "Appointments list contains duplicate appointment(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedAppointment> appointments = new ArrayList<>();

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Json use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        appointments.addAll(source.getAppointmentList().stream()
                .map(JsonAdaptedAppointment::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        for (JsonAdaptedAppointment jsonAdaptedAppointment : appointments) {
            Appointment appointment = toModelAppointment(jsonAdaptedAppointment, addressBook);
            if (addressBook.hasAppointment(appointment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_APPOINTMENT);
            }
            addressBook.addAppointment(appointment);
        }
        return addressBook;
    }

    /**
     * Converts a given {@code JsonAdaptedAppointment} into the model's {@code Appointment} object, given an
     * AddressBook.
     *
     * @param jsonAdaptedAppointment the JsonAdaptedAppointment to be converted.
     * @param addressBook the AddressBook to be referenced
     * @return the Appointment object to be added.
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Appointment toModelAppointment(JsonAdaptedAppointment jsonAdaptedAppointment, AddressBook addressBook)
            throws IllegalValueException {
        Person seller = addressBook.findPerson(jsonAdaptedAppointment.getSeller());
        Person buyer = addressBook.findPerson(jsonAdaptedAppointment.getBuyer());

        return jsonAdaptedAppointment.toModelType(seller, buyer);
    }

}
