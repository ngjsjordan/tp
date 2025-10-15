package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
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
    private final Appointment appointmentToRemove;

    /**
     * @param index       of the person in the filtered person list to edit
     * @param appointment appointment to delete from the specified person
     */
    public DeleteAppointmentCommand(Index index, Appointment appointment) {
        requireNonNull(index);
        requireNonNull(appointment);

        this.personIndex = index;
        this.appointmentToRemove = appointment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());
        Person editedPerson = removeAppointmentFromPerson(personToEdit, appointmentToRemove);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, appointmentToRemove.toString(),
                Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with the given appointment removed {@code appointment}.
     */
    private Person removeAppointmentFromPerson(Person personToEdit, Appointment appointmentToRemove)
            throws CommandException {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Role updatedRole = personToEdit.getRole();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedRole, updatedAddress,
                updatedTags);
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
                && appointmentToRemove.equals(otherDeleteAppointmentCommand.appointmentToRemove);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", personIndex)
                .add("appointment", appointmentToRemove)
                .toString();
    }
}
