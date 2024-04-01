package io.marioslab.sol.command;

import io.marioslab.sol.Command;
import io.marioslab.sol.Game;

public class Remove implements Command {
	String name;
	
	public Remove(String name) {
		this.name = name;
	}

	@Override
	public void update (float delta) {
		Game.ctx.removeDrawable(name);
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
