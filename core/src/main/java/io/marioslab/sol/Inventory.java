package io.marioslab.sol;

import java.util.HashSet;
import java.util.Set;

public class Inventory {
	static Set<String> items = new HashSet<String>();	
	
	public static boolean has(String item) {
		return items.contains(item);
	}
	
	public static void clear() {
		items.clear();
	}
	
	public static void add(String item) {
		items.add(item);
	}
}
