package io.marioslab.sol.command;

import com.badlogic.gdx.math.Vector2;
import io.marioslab.sol.Command;
import io.marioslab.sol.Game;
import io.marioslab.sol.entity.Animated;

/**
 * Moves an entity to a specific point at the given speed. Sets the animation,
 * appending -left or -right depending on which way the entity has to move.
 * @author badlogic
 *
 */
public class MoveToAnim implements Command {
	Animated d;
	String name;
	String baseAnimName;
	int x, y;
	float speed;
	Vector2 v = new Vector2();
	
	public MoveToAnim(String name, String baseAnimName, int x, int y, float speed) {
		this.name = name;
		this.baseAnimName = baseAnimName;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	@Override
	public void update (float delta) {
		if(d == null) {
			d = Game.ctx.getEntity(name, Animated.class);
			String dir = d.x < x? "-right": "-left"; 
			d.setAnimation(baseAnimName + dir, true);
		}
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
		return new MoveToAnim(name, baseAnimName, x, y, speed);
	}
}
