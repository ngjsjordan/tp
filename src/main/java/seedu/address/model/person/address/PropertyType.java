package seedu.address.model.person.address;

/**
 * Represents the different types of properties in Singapore,
 * including HDB flats, condominiums, landed homes, and commercial units.
 * Each property type may include details such as size (e.g., 3-room, 4-room)
 * or tenure (e.g., leasehold, freehold).
 */
public enum PropertyType {

    // HDB Flats
    HDB_2("HDB_2"),
    HDB_3("HDB_3"),
    HDB_4("HDB_4"),
    HDB_5("HDB_5"),
    HDB_J("HDB_J"),

    // Executive Condominium / Maisonette
    EC("EC"),
    EM("EM"),

    // Private condos
    CONDO_2("CONDO_2"),
    CONDO_3("CONDO_3"),
    CONDO_4("CONDO_4"),
    CONDO_5("CONDO_5"),
    CONDO_J("CONDO_J"),

    // Landed properties
    LANDED_LH("LANDED_LH"),
    LANDED_FH("LANDED_FH"),

    // Commercial properties
    COMMERCIAL_LH("COMMERCIAL_LH"),
    COMMERCIAL_FH("COMMERCIAL_FH");

    private final String displayName;

    PropertyType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns true if the input string matches any enum constant name.
     */
    public static boolean contains(String test) {
        for (PropertyType type : PropertyType.values()) {
            if (type.name().equalsIgnoreCase(test)) { // Case-insensitive
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
