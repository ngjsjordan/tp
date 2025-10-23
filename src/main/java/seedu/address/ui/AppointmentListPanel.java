package seedu.address.ui;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;

/**
 * Panel containing the list of appointments.
 */
public class AppointmentListPanel extends UiPart<Region> {
    private static final String FXML = "AppointmentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AppointmentListPanel.class);

    @FXML
    private ListView<Appointment> appointmentListView;

    /**
     * Creates an {@code AppointmentListPanel} with the given {@code ObservableList} of appointments.
     * Sorts appointments by datetime.
     */
    public AppointmentListPanel(ObservableList<Appointment> appointments) {
        super(FXML);

        // Sort appointments by datetime
        List<Appointment> sortedAppointments = appointments.stream()
                .sorted(Comparator.naturalOrder())
                .toList();

        ObservableList<Appointment> appointmentList =
                FXCollections.observableArrayList(sortedAppointments);

        appointmentListView.setItems(appointmentList);
        appointmentListView.setCellFactory(listView -> new AppointmentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Appointment}
     * using an {@code AppointmentCard}.
     */
    class AppointmentListViewCell extends ListCell<Appointment> {
        @Override
        protected void updateItem(Appointment appointment, boolean empty) {
            super.updateItem(appointment, empty);

            if (empty || appointment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AppointmentCard(appointment, getIndex() + 1).getRoot());
            }
        }
    }
}
