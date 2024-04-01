
package io.marioslab.sol;

import com.badlogic.gdx.ApplicationAdapter;
import io.marioslab.sol.scenes.OutdoorScene;


public class Sol extends ApplicationAdapter {
	Game game;
	
	@Override
	public void create () {
		game = new Game();
		game.setScene(new OutdoorScene());
	}

	@Override
	public void render () {
		game.render();
	}
}
