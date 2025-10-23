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
    private ListView<AppointmentEntry> appointmentListView;

    /**
     * Creates an {@code AppointmentListPanel} with the given {@code ObservableList} of appointments.
     * Transforms appointments to AppointmentEntry objects and sorts them by datetime.
     */
    public AppointmentListPanel(ObservableList<Appointment> appointments) {
        super(FXML);

        // Transform appointments to AppointmentEntry objects and sort by datetime
        List<AppointmentEntry> appointmentEntries = appointments.stream()
                .map(appointment -> new AppointmentEntry(appointment, appointment.getSeller()))
                .sorted(Comparator.comparing(AppointmentEntry::getAppointment))
                .toList();

        ObservableList<AppointmentEntry> appointmentList =
                FXCollections.observableArrayList(appointmentEntries);

        appointmentListView.setItems(appointmentList);
        appointmentListView.setCellFactory(listView -> new AppointmentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code AppointmentEntry}
     * using an {@code AppointmentCard}.
     */
    class AppointmentListViewCell extends ListCell<AppointmentEntry> {
        @Override
        protected void updateItem(AppointmentEntry appointmentEntry, boolean empty) {
            super.updateItem(appointmentEntry, empty);

            if (empty || appointmentEntry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AppointmentCard(appointmentEntry, getIndex() + 1).getRoot());
            }
        }
    }
}
