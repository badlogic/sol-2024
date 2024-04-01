package io.marioslab.sol.command;

import io.marioslab.sol.Command;
import io.marioslab.sol.Entity;
import io.marioslab.sol.Game;

public class SetZ implements Command {
	String name;
	int z;
	
	public SetZ(String name, int z) {
		this.name = name;
		this.z = z;
	}

	@Override
	public void update (float delta) {
		Game.ctx.getEntity(name, Entity.class).z = z;
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
