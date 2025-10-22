package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Toggle the theme of the UI between light and dark.
 */
public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";

    public static final String MESSAGE_TOGGLE_ACKNOWLEDGEMENT = "Switching UI theme...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_TOGGLE_ACKNOWLEDGEMENT, false, false, true, false, false);
    }

}
