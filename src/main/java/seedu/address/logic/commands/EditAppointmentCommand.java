package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDatetime;
import seedu.address.model.person.Person;

/**
 * Edits the details of an existing appointment in the address book.
 */
public class EditAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "eap";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the appointment identified "
            + "by the index number used in the displayed appointment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_SELLER + "SELLER_INDEX] "
            + "[" + PREFIX_BUYER + "BUYER_INDEX]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATETIME + "2025-01-15T14:00 "
            + PREFIX_SELLER + "2";

    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited Appointment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the address book.";
    public static final String MESSAGE_INVALID_SELLER_ROLE = "The person assigned as seller must have a seller role.";
    public static final String MESSAGE_INVALID_BUYER_ROLE = "The person assigned as buyer must have a buyer role.";

    private final Index index;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    /**
     * @param index of the appointment in the filtered appointment list to edit
     * @param editAppointmentDescriptor details to edit the appointment with
     */
    public EditAppointmentCommand(Index index, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAppointmentDescriptor);

        this.index = index;
        this.editAppointmentDescriptor = new EditAppointmentDescriptor(editAppointmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToEdit = lastShownList.get(index.getZeroBased());
        Appointment editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor, model);

        if (!appointmentToEdit.equals(editedAppointment) && model.hasAppointment(editedAppointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.setAppointment(appointmentToEdit, editedAppointment);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS,
                Messages.format(editedAppointment)));
    }

    /**
     * Creates and returns an {@code Appointment} with the details of {@code appointmentToEdit}
     * edited with {@code editAppointmentDescriptor}.
     */
    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
            EditAppointmentDescriptor editAppointmentDescriptor, Model model) throws CommandException {
        assert appointmentToEdit != null;

        AppointmentDatetime updatedDatetime = editAppointmentDescriptor.getAppointmentDatetime()
                .orElse(appointmentToEdit.getAppointmentDatetime());

        Person updatedSeller = getUpdatedSeller(appointmentToEdit, editAppointmentDescriptor, model);
        Person updatedBuyer = getUpdatedBuyer(appointmentToEdit, editAppointmentDescriptor, model);

        assert !updatedSeller.equals(updatedBuyer) : "Seller and buyer should be different persons";

        return createAppointment(updatedDatetime, updatedSeller, updatedBuyer);
    }

    /**
     * Returns the updated seller for the appointment.
     * If a new seller index is provided, retrieves and validates the seller from the model.
     * Otherwise, returns the original seller from the appointment.
     */
    private static Person getUpdatedSeller(Appointment appointmentToEdit,
            EditAppointmentDescriptor editAppointmentDescriptor, Model model) throws CommandException {
        if (!editAppointmentDescriptor.getSellerIndex().isPresent()) {
            return appointmentToEdit.getSeller();
        }

        Index sellerIndex = editAppointmentDescriptor.getSellerIndex().get();
        Person seller = getPersonFromIndex(sellerIndex, model);
        validateSellerRole(seller);
        return seller;
    }

    /**
     * Returns the updated buyer for the appointment.
     * If a new buyer index is provided, retrieves and validates the buyer from the model.
     * Otherwise, returns the original buyer from the appointment (which may be null).
     */
    private static Person getUpdatedBuyer(Appointment appointmentToEdit,
            EditAppointmentDescriptor editAppointmentDescriptor, Model model) throws CommandException {
        if (!editAppointmentDescriptor.getBuyerIndex().isPresent()) {
            return appointmentToEdit.getBuyer().orElse(null);
        }

        Index buyerIndex = editAppointmentDescriptor.getBuyerIndex().get();
        Person buyer = getPersonFromIndex(buyerIndex, model);
        validateBuyerRole(buyer);
        return buyer;
    }

    /**
     * Retrieves a person from the model at the specified index.
     *
     * @throws CommandException if the index is invalid
     */
    private static Person getPersonFromIndex(Index index, Model model) throws CommandException {
        List<Person> personList = model.getFilteredPersonList();

        if (index.getZeroBased() >= personList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return personList.get(index.getZeroBased());
    }

    /**
     * Validates that the person has a seller role.
     *
     * @throws CommandException if the person does not have a seller role
     */
    private static void validateSellerRole(Person seller) throws CommandException {
        if (!seller.isSeller()) {
            throw new CommandException(MESSAGE_INVALID_SELLER_ROLE);
        }
    }

    /**
     * Validates that the person has a buyer role.
     *
     * @throws CommandException if the person does not have a buyer role
     */
    private static void validateBuyerRole(Person buyer) throws CommandException {
        if (!buyer.isBuyer()) {
            throw new CommandException(MESSAGE_INVALID_BUYER_ROLE);
        }
    }

    /**
     * Creates an Appointment with the given datetime, seller, and optional buyer.
     */
    private static Appointment createAppointment(AppointmentDatetime datetime, Person seller, Person buyer) {
        if (buyer != null) {
            return new Appointment(datetime, seller, buyer);
        } else {
            return new Appointment(datetime, seller);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAppointmentCommand)) {
            return false;
        }

        EditAppointmentCommand otherEditAppointmentCommand = (EditAppointmentCommand) other;
        return index.equals(otherEditAppointmentCommand.index)
                && editAppointmentDescriptor.equals(otherEditAppointmentCommand.editAppointmentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editAppointmentDescriptor", editAppointmentDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the appointment with. Each non-empty field value will replace the
     * corresponding field value of the appointment.
     */
    public static class EditAppointmentDescriptor {
        private AppointmentDatetime appointmentDatetime;
        private Index sellerIndex;
        private Index buyerIndex;

        public EditAppointmentDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setAppointmentDatetime(toCopy.appointmentDatetime);
            setSellerIndex(toCopy.sellerIndex);
            setBuyerIndex(toCopy.buyerIndex);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(appointmentDatetime, sellerIndex, buyerIndex);
        }

        public void setAppointmentDatetime(AppointmentDatetime appointmentDatetime) {
            this.appointmentDatetime = appointmentDatetime;
        }

        public Optional<AppointmentDatetime> getAppointmentDatetime() {
            return Optional.ofNullable(appointmentDatetime);
        }

        public void setSellerIndex(Index sellerIndex) {
            this.sellerIndex = sellerIndex;
        }

        public Optional<Index> getSellerIndex() {
            return Optional.ofNullable(sellerIndex);
        }

        public void setBuyerIndex(Index buyerIndex) {
            this.buyerIndex = buyerIndex;
        }

        public Optional<Index> getBuyerIndex() {
            return Optional.ofNullable(buyerIndex);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentDescriptor)) {
                return false;
            }

            EditAppointmentDescriptor otherEditAppointmentDescriptor = (EditAppointmentDescriptor) other;
            return Objects.equals(appointmentDatetime, otherEditAppointmentDescriptor.appointmentDatetime)
                    && Objects.equals(sellerIndex, otherEditAppointmentDescriptor.sellerIndex)
                    && Objects.equals(buyerIndex, otherEditAppointmentDescriptor.buyerIndex);
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
}
