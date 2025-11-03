package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.address.Address;
import seedu.address.model.person.address.AddressType;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Role role;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Role role, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    /**
     * Returns true if this person has a seller role.
     */
    public boolean isSeller() {
        return role.isSeller();
    }

    /**
     * Returns true if this person has a buyer role.
     */
    public boolean isBuyer() {
        return role.isBuyer();
    }

    public Address getAddress() {
        return address;
    }

    public AddressType getAddressType() {
        return address.getAddressType();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if any field of this person contains the given keyword (case-insensitive).
     * Searches across name, role, address, property type, email, phone, and tags.
     */
    public boolean containsKeyword(String keyword) {
        return StringUtil.containsWordIgnoreCase(name.fullName, keyword)
                || StringUtil.containsWordIgnoreCase(role.value, keyword)
                || StringUtil.containsWordIgnoreCase(address.value, keyword)
                || StringUtil.containsWordIgnoreCase(address.getAddressType().toString(), keyword)
                || StringUtil.containsWordIgnoreCase(email.value, keyword)
                || StringUtil.containsWordIgnoreCase(phone.value, keyword)
                || tags.stream().anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword));
    }

    /**
     * Returns true if this person's name contains the given keyword (case-insensitive).
     */
    public boolean containsKeywordInName(String keyword) {
        return StringUtil.containsWordIgnoreCase(name.fullName, keyword);
    }

    /**
     * Returns true if this person's address contains the given keyword (case-insensitive).
     */
    public boolean containsKeywordInAddress(String keyword) {
        return StringUtil.containsWordIgnoreCase(address.value, keyword);
    }

    /**
     * Returns true if both persons have the same identifier (phone number).
     * This defines a weaker notion of equality between two persons.
     */
    public boolean hasSameIdentifier(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getStorageIdentifier().equals(getStorageIdentifier());
    }

    /**
     * Returns a String of this person's phone number, to be used as an identifier for Storage.
     *
     * @return String of phone number, to be used as storage identifier.
     */
    public String getStorageIdentifier() {
        return phone.value;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && role.equals(otherPerson.role)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, role, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("role", role)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
