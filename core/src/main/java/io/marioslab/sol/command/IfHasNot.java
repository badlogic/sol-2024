package io.marioslab.sol.command;

import java.util.Set;

import com.badlogic.gdx.utils.Array;
import io.marioslab.sol.Command;
import io.marioslab.sol.Game;
import io.marioslab.sol.Inventory;

/**
 * Checks if a set of items is in the inventory. If not the given
 * list of commands will be executed, otherwise execution continues
 * as usual.
 * @author badlogic
 *
 */
public class IfHasNot implements Command {
	Set<String> neededItems;
	Array<Command> commands;
	
	public IfHasNot(Set<String> neededItems, Array<Command> commands) {
		this.neededItems = neededItems;
		this.commands = commands;
	}

	@Override
	public void update (float delta) {
		boolean hasAll = true;
		for(String item: neededItems) {
			if(!Inventory.has(item)) {
				hasAll = false;
				break;
			}
		}
		if(!hasAll) {
			Game.ctx.getScene().commands.clear();
			// need to add a nop as the while loop in Scene#update() will kill the
			// first command we are about to add
			Game.ctx.getScene().commands.add(new Nop());
			for(Command c: commands) {
				Game.ctx.getScene().commands.add(c.copy());
			}
		}
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
