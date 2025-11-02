package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDatetime;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.address.Address;
import seedu.address.model.person.address.AddressType;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Role("buyer"), new Address("Blk 30 Geylang Street 29, #06-40",
                    new AddressType("HDB_4")),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Role("buyer"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18",
                    new AddressType("CONDO_5")),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Role("buyer"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04", new AddressType("LANDED_FH")),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Role("seller"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43",
                    new AddressType("COMMERCIAL_LH")),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Role("seller"), new Address("Blk 47 Tampines Street 20, #17-35", new AddressType("HDB_5")),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Role("seller"), new Address("Blk 45 Aljunied Street 85, #11-31", new AddressType("EC")),
                getTagSet("colleagues"))
        };
    }

    public static Appointment[] getSampleAppointments() {
        Person[] persons = getSamplePersons();

        return new Appointment[] {
            // Today appointment: Alex Yeoh (buyer) with David Li (seller)
            new Appointment(
                new AppointmentDatetime(LocalDate.now().toString() + "T09:00"),
                persons[3], persons[0]
            ),
            // Upcoming appointment: David Li (seller) with Alex Yeoh (buyer)
            new Appointment(
                new AppointmentDatetime(LocalDate.now().plusDays(3).toString() + "T14:00"),
                persons[3], persons[0]
            ),
            // Upcoming appointment: Irfan Ibrahim (seller) with Bernice Yu (buyer)
            new Appointment(
                new AppointmentDatetime(LocalDate.now().plusDays(7).toString() + "T10:30"),
                persons[4], persons[1]
            ),
            // Upcoming appointment: Roy Balakrishnan (seller) with Charlotte Oliveiro (buyer)
            new Appointment(
                new AppointmentDatetime(LocalDate.now().plusDays(14).toString() + "T15:00"),
                persons[5], persons[2]
            ),
            // Past appointment: David Li (seller) with Bernice Yu (buyer)
            new Appointment(
                new AppointmentDatetime(LocalDate.now().minusDays(7).toString() + "T11:00"),
                persons[3], persons[1]
            ),
            // Appointment without buyer: Irfan Ibrahim (seller) only
            new Appointment(
                new AppointmentDatetime(LocalDate.now().plusDays(10).toString() + "T16:00"),
                persons[4]
            )
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Appointment sampleAppointment : getSampleAppointments()) {
            sampleAb.addAppointment(sampleAppointment);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
