package io.marioslab.sol.command;

import io.marioslab.sol.Assets;
import io.marioslab.sol.Command;

public class StopMusic implements Command {
	String name;
	
	public StopMusic(String name) {
		this.name = name;
	}
	
	@Override
	public void update (float delta) {
		Assets.stopMusic(name);
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
