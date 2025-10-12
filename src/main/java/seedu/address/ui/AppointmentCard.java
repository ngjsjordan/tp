package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of an {@code AppointmentEntry}.
 */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "AppointmentListCard.fxml";
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public final AppointmentEntry appointmentEntry;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label datetime;
    @FXML
    private Label location;
    @FXML
    private Label buyer;
    @FXML
    private Label seller;

    /**
     * Creates an {@code AppointmentCard} with the given {@code AppointmentEntry} and index to display.
     */
    public AppointmentCard(AppointmentEntry appointmentEntry, int displayedIndex) {
        super(FXML);
        this.appointmentEntry = appointmentEntry;
        id.setText(displayedIndex + ". ");
        datetime.setText(appointmentEntry.getAppointment().datetime.format(DISPLAY_FORMATTER));
        location.setText(appointmentEntry.getPerson().getAddress().value);

        // Display buyer/seller based on the person's role
        if (appointmentEntry.getPerson().getRole().value.equalsIgnoreCase("buyer")) {
            buyer.setText("Buyer: " + appointmentEntry.getPerson().getName().fullName);
            seller.setText("Seller: -");
        } else {
            buyer.setText("Buyer: -");
            seller.setText("Seller: " + appointmentEntry.getPerson().getName().fullName);
        }
    }
}
