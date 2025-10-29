package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.address.PropertyType;

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
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        if (roleLowerCase.equals(Role.BUYER)) {
            role.getStyleClass().add(Role.BUYER);
        } else if (roleLowerCase.equals(Role.SELLER)) {
            role.getStyleClass().add(Role.SELLER);
        }
        try {
            PropertyType type = PropertyType.fromString(propertyTypeCapitalised);
            propertyType.getStyleClass().add(type.getCssClass());
        } catch (IllegalArgumentException e) {
            propertyType.getStyleClass().add("property_unknown");
        }
    }
}
