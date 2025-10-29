package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole() {
        // null role
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // blank role
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only


        // invalid role
        assertFalse(Role.isValidRole("tester"));
        assertFalse(Role.isValidRole("middleman"));

        // valid role
        assertTrue(Role.isValidRole("Buyer"));
        assertTrue(Role.isValidRole("bUyer"));
        assertTrue(Role.isValidRole("buYer"));
        assertTrue(Role.isValidRole("buyEr"));
        assertTrue(Role.isValidRole("buyeR"));
        assertTrue(Role.isValidRole("BUYER"));
        assertTrue(Role.isValidRole("buyer"));

        assertTrue(Role.isValidRole("Seller"));
        assertTrue(Role.isValidRole("sEller"));
        assertTrue(Role.isValidRole("seLler"));
        assertTrue(Role.isValidRole("selLer"));
        assertTrue(Role.isValidRole("sellEr"));
        assertTrue(Role.isValidRole("selleR"));
        assertTrue(Role.isValidRole("SELLER"));
        assertTrue(Role.isValidRole("seller"));
    }

    @Test
    public void isValidRoles() {
        assertTrue(new Role("Buyer").isBuyer());
        assertTrue(new Role("SELLER").isSeller());
    }

    @Test
    public void equals() {
        Role role = new Role("seller");

        // same values -> returns true
        assertTrue(role.equals(new Role("Seller")));
        assertEquals(new Role("SELLER"), new Role("seller"));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("buyer")));
    }
}
