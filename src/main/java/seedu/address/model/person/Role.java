package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's role in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    public static final String BUYER = "buyer";
    public static final String SELLER = "seller";

    public static final String MESSAGE_CONSTRAINTS = "Role must be either '%s' or '%s'.".formatted(BUYER, SELLER);
    public static final String VALIDATION_REGEX = "(?i)(%s|%s)".formatted(BUYER, SELLER);

    public final String value;

    /**
     * Constructs an {@code Role}.
     *
     * @param role A valid role for the clients.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        value = role.toLowerCase();
    }

    /**
     * Returns if a given string is a valid role.
     */
    public static boolean isValidRole(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the role is a buyer.
     *
     * @return boolean if role is a buyer
     */
    public boolean isBuyer() {
        return value.equals(BUYER);
    }

    /**
     * Returns true if the role is a seller.
     *
     * @return boolean if role is a seller
     */
    public boolean isSeller() {
        return value.equals(SELLER);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return value.equals(otherRole.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
