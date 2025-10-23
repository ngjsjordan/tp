package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDatetime;
import seedu.address.model.person.Person;

/**
 * Deletes an appointment from the address book.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "dap";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an appointment from the client specified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DATETIME + "DATETIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATETIME + "2025-01-01T12:00 ";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Appointment %1$s deleted from person: %2$s";
    public static final String MESSAGE_NO_APPOINTMENT_TO_DELETE = "%1$s: No such appointment to delete from %2$s";


    private final Index personIndex;
    private final AppointmentDatetime appointmentDatetime;

    /**
     * @param index       of the person in the filtered person list
     * @param appointmentDatetime datetime of the appointment to delete
     */
    public DeleteAppointmentCommand(Index index, AppointmentDatetime appointmentDatetime) {
        requireNonNull(index);
        requireNonNull(appointmentDatetime);

        this.personIndex = index;
        this.appointmentDatetime = appointmentDatetime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person referencedPerson = lastShownList.get(personIndex.getZeroBased());

        // Find the appointment by datetime
        Appointment actualAppointment = model.getAddressBook().getAppointmentList().stream()
                .filter(apt -> apt.appointmentDatetime.equals(appointmentDatetime))
                .filter(apt ->
                        apt.getSeller().equals(referencedPerson)
                                || apt.getBuyer().map(b -> b.equals(referencedPerson)).orElse(false))
                .findFirst()
                .orElse(null);

        if (actualAppointment == null) {
            throw new CommandException(String.format(MESSAGE_NO_APPOINTMENT_TO_DELETE,
                    appointmentDatetime, Messages.format(referencedPerson)));
        }

        model.deleteAppointment(actualAppointment);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, actualAppointment.toString(),
                Messages.format(referencedPerson)));
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
        return personIndex.equals(otherDeleteAppointmentCommand.personIndex)
                && appointmentDatetime.equals(otherDeleteAppointmentCommand.appointmentDatetime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", personIndex)
                .add("appointmentDatetime", appointmentDatetime)
                .toString();
    }
}
