package io.marioslab.sol;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import io.marioslab.sol.command.New;

public class Scene {
	public Array<Command> commands = new Array<Command>();
	public Array<Trigger> triggers = new Array<Trigger>();
	
	public void update(float deltaTime) {
		while(commands.size > 0) {
			Command cmd = commands.get(0);
			cmd.update(deltaTime);
			if(cmd.isDone()) {
				commands.removeIndex(0);
			} else {
				break;
			}
		}
		
		if(commands.size == 0 && Gdx.input.justTouched()) {
			for(Trigger t: triggers) {
				if(t.hit(Game.ctx.getX(), Game.ctx.getY()) & !t.disabled) {
					for(Command c: t.commands) {
						commands.add(c.copy());
					}
				}
			}
		}
	}
	
	public void add(Entity drawable) {
		commands.add(new New(drawable));
	}
	
	public void add(Command command) {
		commands.add(command);
	}
	
	public void add(Trigger trigger) {
		triggers.add(trigger);
	}
	
	public void remove(Trigger trigger) {
		Iterator<Trigger> iter = triggers.iterator();
		while(iter.hasNext()) {
			Trigger t = iter.next();
			if(t == trigger) {
				iter.remove();
				break;
			}
		}
	}
	
	public static Set<String> set(String ... strings) {
		Set<String> set = new HashSet<String>();
		for(String s: strings) {
			set.add(s);
		}
		return set;
	}
	
	public static Array<Command> array(Command ... cmds) {
		Array<Command> array = new Array<Command>();
		for(Command cmd: cmds) {
			array.add(cmd);
		}
		return array;
	}

	public Trigger getTrigger (String triggerName) {
		for(Trigger t: triggers) {
			if(t.name.equals(triggerName)) return t;
		}
		return null;
	}
}
