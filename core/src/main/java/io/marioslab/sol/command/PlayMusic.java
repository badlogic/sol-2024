package io.marioslab.sol.command;

import io.marioslab.sol.Assets;
import io.marioslab.sol.Command;

public class PlayMusic implements Command {
	String name;
	float volume;
	boolean looped = true;
	
	public PlayMusic(String name) {
		this(name, 1.0f, true);
	}
	
	public PlayMusic(String name, float volume, boolean looped) {
		this.name = name;
		this.volume = volume;
		this.looped = looped;
	}
	
	@Override
	public void update (float delta) {
		Assets.playMusic(name, volume, looped);
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
