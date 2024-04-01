package io.marioslab.sol.command;

import io.marioslab.sol.Command;
import io.marioslab.sol.Game;
import io.marioslab.sol.Scene;

/**
 * Switches to the next scene
 * @author badlogic
 *
 */
public class NextScene implements Command {
	Scene scene;
	
	public NextScene(Scene scene) {
		this.scene = scene;
	}
	
	@Override
	public void update (float delta) {
		Game.ctx.setScene(scene);
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
