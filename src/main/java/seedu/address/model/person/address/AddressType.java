package seedu.address.model.person.address;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents an Address's AddressType in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidType(String)}
 */
public class AddressType {

    public static final String MESSAGE_CONSTRAINTS =
            "Property type should strictly follow any one of these given types: \n"
                    + Arrays.stream(PropertyType.values()).map(Enum::name).collect(Collectors.joining("\n"));

    public final PropertyType type;

    /**
     * Constructs a {@code AddressType}.
     *
     * @param type A valid address type.
     */
    public AddressType(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_CONSTRAINTS);
        this.type = PropertyType.valueOf(type.toUpperCase()); // Set the type to all uppercase to match enum constant
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
