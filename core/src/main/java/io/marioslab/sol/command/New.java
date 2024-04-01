package io.marioslab.sol.command;

import io.marioslab.sol.Command;
import io.marioslab.sol.Entity;
import io.marioslab.sol.Game;

/**
 * Creates a new {@link Entity} and adds it to 
 * the {@link Game}
 * @author badlogic
 *
 */
public class New implements Command {
	Entity drawable;
	
	public New(Entity drawable) {
		this.drawable = drawable;
	}
	
	@Override
	public void update (float delta) {
		Game.ctx.addDrawable(drawable);
	}

	@Override
	public boolean isDone () {
		return true;
	}

	@Override
	public Command copy () {
		return new New(drawable.copy());
	}
}
