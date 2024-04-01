package io.marioslab.sol.scenes;

import io.marioslab.sol.Scene;
import io.marioslab.sol.entity.Animated;

public class TestScene extends Scene {
	public TestScene () {
		add(new Animated("", "realmario-front", 0, 60, 0));
		add(new Animated("", "realmario-idle-right", 32, 60, 0));
		add(new Animated("", "realmario-walk-right", 64, 60, 0));
		add(new Animated("", "realmario-censored", 96, 60, 0));
		add(new Animated("", "realmario-censored-tada", 128, 60, 0));
	}
}
