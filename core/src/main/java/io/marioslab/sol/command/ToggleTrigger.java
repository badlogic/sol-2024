package io.marioslab.sol.command;

import io.marioslab.sol.Command;
import io.marioslab.sol.Game;
import io.marioslab.sol.Trigger;

/**
 * Enables/disables a trigger by name
 * @author badlogic
 *
 */
public class ToggleTrigger implements Command {
	String triggerName;
	
	public ToggleTrigger(String triggerName) {
		this.triggerName = triggerName;
	}
	
	@Override
	public void update (float delta) {
		Trigger trigger = Game.ctx.getScene().getTrigger(triggerName);
		trigger.disabled = !trigger.disabled;
	}

	@Override
	public boolean isDone () {
		return true;
	}

	@Override
	public Command copy () {
		return this;
	}
}
