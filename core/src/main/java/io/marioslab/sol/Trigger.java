package io.marioslab.sol;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.marioslab.sol.command.New;

/**
 * Rectangular area that when touched will trigger a list of commands to be
 * executed
 * @author badlogic
 *
 */
public class Trigger {
	public String name;
	public boolean disabled;
	Array<Command> commands = new Array<Command>();
	Rectangle rect = new Rectangle();
	boolean active = false;
	
	public Trigger(String name, int x, int y, int x2, int y2) {
		this.name = name;
		this.rect.x = Math.min(x, x2);
		this.rect.y = Math.min(y, y2);
		this.rect.width = Math.abs(x2 - x);
		this.rect.height = Math.abs(y2 - y);
	}
	
	public Trigger add(Entity drawable) {
		commands.add(new New(drawable));
		return this;
	}
	
	public Trigger add(Command command) {
		commands.add(command);
		return this;
	}
	
	public boolean hit(int x, int y) {
		return rect.contains(x, y);
	}
	
	public Trigger disable() {
		commands.add(new Command() {
			@Override
			public void update (float delta) {
				disabled = true;
			}

			@Override
			public boolean isDone () {
				return true;
			}

			@Override
			public Command copy () {
				return this;
			}
		});
		return this;
	}
}
