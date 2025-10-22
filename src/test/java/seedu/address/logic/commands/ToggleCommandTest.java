package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ToggleCommand.MESSAGE_TOGGLE_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ToggleCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_toggleTheme_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_TOGGLE_ACKNOWLEDGEMENT, false,
                false, true, false, false);
        assertCommandSuccess(new ToggleCommand(), model, expectedCommandResult, expectedModel);
    }
}
