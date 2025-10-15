package seedu.address.model.person.address;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Address's AddressType in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidType(String)}
 */
public class AddressType {

    public static final String MESSAGE_CONSTRAINTS =
            "Address type should strictly follow any one of these given types: \n "
                    + "HDB_2\n"
                    + "HDB_3\n"
                    + "HDB_4\n"
                    + "HDB_5\n"
                    + "HDB_J\n"
                    + "EC\n"
                    + "EM\n"
                    + "CONDO_2\n"
                    + "CONDO_3\n"
                    + "CONDO_4\n"
                    + "CONDO_5\n"
                    + "CONDO_J\n"
                    + "LANDED_LH\n"
                    + "LANDED_FH\n"
                    + "COMMERCIAL_LH\n"
                    + "COMMERCIAL_FH";

    public final PropertyType type;

    /**
     * Constructs a {@code AddressType}.
     *
     * @param type A valid address type.
     */
    public AddressType(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_CONSTRAINTS);
        this.type = PropertyType.valueOf(type);
    }

    /**
     * Returns true if a given string is a valid address type.
     */
    public static boolean isValidType(String test) {
        requireNonNull(test);
        return PropertyType.contains(test);
    }


    @Override
    public String toString() {
        return type.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressType)) {
            return false;
        }

        AddressType otherAddressType = (AddressType) other;
        return type.equals(otherAddressType.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

}
