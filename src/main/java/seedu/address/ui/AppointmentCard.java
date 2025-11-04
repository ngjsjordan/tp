package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of an {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "AppointmentListCard.fxml";
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final String NO_BUYER_TEXT = "-";

    public final Appointment appointment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label datetime;
    @FXML
    private Label appointmentLocation;
    @FXML
    private Label buyer;
    @FXML
    private Label seller;

    /**
     * Creates an {@code AppointmentCard} with the given {@code Appointment} and index to display.
     */
    public AppointmentCard(Appointment appointment, int displayedIndex) {
        super(FXML);
        this.appointment = appointment;
        id.setText(displayedIndex + ". ");
        datetime.setText(appointment.getAppointmentDatetime().datetime.format(DISPLAY_FORMATTER));
        appointmentLocation.setText(appointment.getSeller().getAddress().value);

        // Display buyer AND seller information
        buyer.setText("Buyer: " + appointment.getBuyer()
                .map(this::formatPersonWithPhone)
                .orElse(NO_BUYER_TEXT));
        seller.setText("Seller: " + formatPersonWithPhone(appointment.getSeller()));
    }

    /**
     * Formats a person's name with their phone number.
     * @param person The person to format.
     * @return Formatted string in the format "Name (Phone)".
     */
    private String formatPersonWithPhone(Person person) {
        return person.getName() + " (" + person.getPhone() + ")";
    }
}
