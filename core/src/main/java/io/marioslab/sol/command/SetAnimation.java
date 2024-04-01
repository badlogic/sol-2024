package io.marioslab.sol.command;

import io.marioslab.sol.Command;
import io.marioslab.sol.Game;
import io.marioslab.sol.entity.Animated;

/**
 * Sets the animation on an {@link Animated} entity.
 * @author badlogic
 *
 */
public class SetAnimation implements Command {
	String name;
	String animationName;
	boolean looped = true;
	
	public SetAnimation(String name, String animationName) {
		this.name = name;
		this.animationName = animationName;
	}
	
	public SetAnimation(String name, String animationName, boolean looped) {
		this.name = name;
		this.animationName = animationName;
		this.looped = looped;
	}
	
	@Override
	public void update (float delta) {
		Game.ctx.getEntity(name, Animated.class).setAnimation(animationName, looped);
	}

	@Override
	public boolean isDone () {
		return true;
	}


	@Override
	public Command copy () {
		return new SetAnimation(name, animationName);
	}
}
