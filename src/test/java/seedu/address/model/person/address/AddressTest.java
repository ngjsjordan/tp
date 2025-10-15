package seedu.address.model.person.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

public class AddressTest {

    private static final AddressType VALID_PROPERTY_TYPE = new AddressType(BENSON.getAddressType().toString());

    // valid value
    private static final String VALID_TYPE = "HDB_4";

    // Invalid or malformed strings
    private static final String INVALID_TYPE = "HDB_10";
    private static final String LOWERCASE_TYPE = "hdb_4";
    private static final String EMPTY_TYPE = "";
    private static final String SPACED_TYPE = "HDB 4";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null, VALID_PROPERTY_TYPE));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress, VALID_PROPERTY_TYPE));
    }

    @Test
    public void constructor_invalidType_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new AddressType(INVALID_TYPE));
        assertThrows(IllegalArgumentException.class, () -> new AddressType(LOWERCASE_TYPE));
        assertThrows(IllegalArgumentException.class, () -> new AddressType(EMPTY_TYPE));
        assertThrows(IllegalArgumentException.class, () -> new AddressType(SPACED_TYPE));
    }

    @Test
    public void constructor_validType_success() {
        AddressType addressType = new AddressType(VALID_TYPE);
        assertEquals(VALID_TYPE, addressType.toString());
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("-")); // one character
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    public void isValidType() {
        // null input
        assertThrows(NullPointerException.class, () -> AddressType.isValidType(null));

        // invalid types
        assertFalse(AddressType.isValidType("")); // empty string
        assertFalse(AddressType.isValidType(" ")); // spaces only
        assertFalse(AddressType.isValidType("random"));
        assertFalse(AddressType.isValidType("HDB-4")); // wrong delimiter
        assertFalse(AddressType.isValidType("hdb_4")); // lowercase
        assertFalse(AddressType.isValidType("LANDED")); // incomplete type

        // valid types
        assertTrue(AddressType.isValidType("HDB_2"));
        assertTrue(AddressType.isValidType("HDB_3"));
        assertTrue(AddressType.isValidType("EC"));
        assertTrue(AddressType.isValidType("CONDO_J"));
        assertTrue(AddressType.isValidType("COMMERCIAL_FH"));
    }

    @Test
    public void equals() {
        AddressType type1 = new AddressType("HDB_4");
        AddressType type2 = new AddressType("HDB_4");
        AddressType type3 = new AddressType("HDB_5");

        Address address = new Address("Valid Address", VALID_PROPERTY_TYPE);

        // same values -> returns true
        assertTrue(address.equals(new Address("Valid Address", VALID_PROPERTY_TYPE)));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Address("Other Valid Address", VALID_PROPERTY_TYPE)));

        // same type values -> true
        assertTrue(type1.equals(type2));

        // same type object -> true
        assertTrue(type1.equals(type1));

        // null type -> false
        assertFalse(type1.equals(null));

        // different type -> false
        assertFalse(type1.equals(5));

        // different type values -> false
        assertFalse(type1.equals(type3));
    }
}
