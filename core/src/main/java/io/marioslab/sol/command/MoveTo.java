package io.marioslab.sol.command;

import com.badlogic.gdx.math.Vector2;
import io.marioslab.sol.Command;
import io.marioslab.sol.Entity;
import io.marioslab.sol.Game;

/**
 * Moves an entity to the position at the given speed
 * @author badlogic
 *
 */
public class MoveTo implements Command {
	Entity d;
	String name;
	int x, y;
	float speed;
	Vector2 v = new Vector2();
	
	public MoveTo(String name, int x, int y, float speed) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	@Override
	public void update (float delta) {
		if(d == null) d = Game.ctx.getEntity(name, Entity.class);
		v.set(x - d.x, y - d.y).nor().scl(delta * speed);
		d.x = d.x + v.x;
		d.y = d.y + v.y;
	}

	@Override
	public boolean isDone () {
		return Math.abs(d.x - x) < 1 && Math.abs(d.y - y) < 1;
	}

	@Override
	public Command copy () {
		return new MoveTo(name, x, y, speed);
	}
}
