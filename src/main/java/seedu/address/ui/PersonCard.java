package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label role;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label propertyType;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        String roleLowerCase = person.getRole().value.toLowerCase();
        role.setText(roleLowerCase);
        String propertyTypeCapitalised = person.getAddressType().toString().toUpperCase();
        propertyType.setText(propertyTypeCapitalised);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.getStyleClass().add("tag_label");
                    tags.getChildren().add(tagLabel);
                });

        if (roleLowerCase.equals(Role.BUYER)) {
            role.getStyleClass().add("role_buyer");
        } else if (roleLowerCase.equals(Role.SELLER)) {
            role.getStyleClass().add("role_seller");
        }

        switch (propertyTypeCapitalised) {
        case "HDB_2":
        case "HDB_3":
        case "HDB_4":
        case "HDB_5":
        case "HDB_J":
            propertyType.getStyleClass().add("property_hdb");
            break;
        case "EM":
        case "EC":
            propertyType.getStyleClass().add("property_executive");
            break;
        case "CONDO_2":
        case "CONDO_3":
        case "CONDO_4":
        case "CONDO_5":
        case "CONDO_J":
            propertyType.getStyleClass().add("property_condo");
            break;

        case "LANDED_LH":
        case "LANDED_FH":
            propertyType.getStyleClass().add("property_landed");
            break;

        case "COMMERCIAL_LH":
        case "COMMERCIAL_FH":
            propertyType.getStyleClass().add("property_commercial");
            break;

        default:
            propertyType.getStyleClass().add("property_unknown");
            break;
        }

    }


}
