package seedu.address.model.person.address;

/**
 * Represents the different types of properties in Singapore,
 * including HDB flats, condominiums, landed homes, and commercial units.
 * Each property type may include details such as size (e.g., 3-room, 4-room)
 * or tenure (e.g., leasehold, freehold).
 */
public enum PropertyType {

    // HDB Flats
    HDB_2("HDB_2", "property_hdb"),
    HDB_3("HDB_3", "property_hdb"),
    HDB_4("HDB_4", "property_hdb"),
    HDB_5("HDB_5", "property_hdb"),
    HDB_J("HDB_J", "property_hdb"),

    // Executive Condominium / Maisonette
    EC("EC", "property_executive"),
    EM("EM", "property_executive"),

    // Private condos
    CONDO_2("CONDO_2", "property_condo"),
    CONDO_3("CONDO_3", "property_condo"),
    CONDO_4("CONDO_4", "property_condo"),
    CONDO_5("CONDO_5", "property_condo"),
    CONDO_J("CONDO_J", "property_condo"),

    // Landed properties
    LANDED_LH("LANDED_LH", "property_landed"),
    LANDED_FH("LANDED_FH", "property_landed"),

    // Commercial properties
    COMMERCIAL_LH("COMMERCIAL_LH", "property_commercial"),
    COMMERCIAL_FH("COMMERCIAL_FH", "property_commercial");

    private final String displayName;
    private final String cssClass;

    PropertyType(String displayName, String cssClass) {
        this.displayName = displayName;
        this.cssClass = cssClass;
    }

    /**
     * Returns cssClass
     */
    public String getCssClass() {
        return this.cssClass;
    }

    /**
     * Converts a string into Property Enum if valid
     * @throws IllegalArgumentException if the displayName does not match any of the Enum constants
     */
    public static PropertyType fromString(String displayName) {
        for (PropertyType type : PropertyType.values()) {
            if (type.name().equalsIgnoreCase(displayName)) { // Case-sensitive
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid property type: " + displayName);
    }

    /**
     * Returns true if the input string matches any enum constant name.
     */
    public static boolean contains(String test) {
        for (PropertyType type : PropertyType.values()) {
            if (type.name().equals(test)) { // Case-sensitive
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
