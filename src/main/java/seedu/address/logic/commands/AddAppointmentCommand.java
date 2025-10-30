package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDatetime;
import seedu.address.model.person.Person;

/**
 * Edits the details of an existing person in the address book.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "ap";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the client specified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: SELLER_INDEX (must be a positive integer) "
            + PREFIX_DATETIME + "DATETIME "
            + "[" + PREFIX_BUYER + "BUYER_INDEX] (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATETIME + "2025-01-01T12:00 "
            + PREFIX_BUYER + "2";

    public static final String MESSAGE_ADD_APPOINTMENT_SUCCESS =
            "Appointment added with seller %1$s.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "That appointment already exists.";
    public static final String MESSAGE_INVALID_SELLER_ROLE = "The person assigned as seller must have a seller role.";
    public static final String MESSAGE_INVALID_BUYER_ROLE = "The person assigned as buyer must have a buyer role.";

    private final AppointmentDatetime appointmentDatetime;
    private final Index sellerIndex;
    private final Index buyerIndex;

    /**
     * Constructor for AddAppointmentCommand with buyer.
     *
     * @param appointmentDatetime datetime of appointment to be added
     * @param sellerIndex index of the seller in the appointment
     * @param buyerIndex index of the buyer in the appointment.
     */
    public AddAppointmentCommand(AppointmentDatetime appointmentDatetime, Index sellerIndex, Index buyerIndex) {
        requireNonNull(appointmentDatetime);
        requireNonNull(sellerIndex);
        requireNonNull(buyerIndex);

        this.appointmentDatetime = appointmentDatetime;
        this.sellerIndex = sellerIndex;
        this.buyerIndex = buyerIndex;
    }

    /**
     * Constructor for AddAppointmentCommand without buyer.
     *
     * @param appointmentDatetime datetime of appointment to be added
     * @param sellerIndex index of the seller in the appointment
     */
    public AddAppointmentCommand(AppointmentDatetime appointmentDatetime, Index sellerIndex) {
        requireNonNull(appointmentDatetime);
        requireNonNull(sellerIndex);

        this.appointmentDatetime = appointmentDatetime;
        this.sellerIndex = sellerIndex;
        this.buyerIndex = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        Appointment appointment = getAppointment(lastShownList);

        if (model.hasAppointment(appointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.addAppointment(appointment);
        return new CommandResult(String.format(MESSAGE_ADD_APPOINTMENT_SUCCESS,
                Messages.format(appointment.getSeller())));
    }

    /**
     * Gets the appointment to be added.
     *
     * @param lastShownList the list of persons that the index is based on.
     * @return the appointment to be added.
     * @throws CommandException if indices are invalid or if roles are wrong.
     */
    private Appointment getAppointment(List<Person> lastShownList) throws CommandException {
        boolean isInvalidSellerIndex = sellerIndex.getZeroBased() >= lastShownList.size();
        boolean isInvalidBuyerIndex = buyerIndex != null && buyerIndex.getZeroBased() >= lastShownList.size();
        if (isInvalidSellerIndex || isInvalidBuyerIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person seller = lastShownList.get(sellerIndex.getZeroBased());

        if (!seller.isSeller()) {
            throw new CommandException(MESSAGE_INVALID_SELLER_ROLE);
        }

        if (buyerIndex == null) {
            return new Appointment(appointmentDatetime, seller);
        }

        Person buyer = lastShownList.get(buyerIndex.getZeroBased());

        if (!buyer.isBuyer()) {
            throw new CommandException(MESSAGE_INVALID_BUYER_ROLE);
        }

        assert !buyer.hasSameIdentifier(seller);

        return new Appointment(appointmentDatetime, seller, buyer);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAppointmentCommand)) {
            return false;
        }

        AddAppointmentCommand otherAddAppointmentCommand = (AddAppointmentCommand) other;
        return appointmentDatetime.equals(otherAddAppointmentCommand.appointmentDatetime)
                && sellerIndex.equals(otherAddAppointmentCommand.sellerIndex)
                && Objects.equals(buyerIndex, otherAddAppointmentCommand.buyerIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("appointmentDatetime", appointmentDatetime)
                .add("sellerIndex", sellerIndex)
                .add("buyerIndex", buyerIndex)
                .toString();
    }

}
