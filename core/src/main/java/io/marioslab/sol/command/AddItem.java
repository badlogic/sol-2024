package io.marioslab.sol.command;

import io.marioslab.sol.Command;
import io.marioslab.sol.Inventory;

/**
 * Adds the item to the inventory
 * @author badlogic
 *
 */
public class AddItem implements Command {
	String item;
	
	public AddItem(String item) {
		this.item = item;
	}
	
	@Override
	public void update (float delta) {
		Inventory.add(item);
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
