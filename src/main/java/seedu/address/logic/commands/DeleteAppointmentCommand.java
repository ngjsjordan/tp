package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Deletes an appointment from the address book.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "dap";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the appointment identified by the "
            + "index number used in the displayed appointments list\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: dap 1";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted Appointment: %1$s";

    private static final Logger logger = LogsCenter.getLogger(DeleteAppointmentCommand.class);

    private final Index targetIndex;

    /**
     * @param index of the appointment in the filtered appointment list
     */
    public DeleteAppointmentCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteAppointment(appointmentToDelete);
        logger.info("Successfully deleted appointment at index " + targetIndex.getOneBased());
        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, appointmentToDelete.toString()),
                false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteAppointmentCommand)) {
            return false;
        }

        DeleteAppointmentCommand otherDeleteAppointmentCommand = (DeleteAppointmentCommand) other;
        return targetIndex.equals(otherDeleteAppointmentCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .toString();
    }
}
