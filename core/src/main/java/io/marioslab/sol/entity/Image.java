package io.marioslab.sol.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.marioslab.sol.Assets;
import io.marioslab.sol.Entity;

public class Image extends Entity {
	public String imageName;
	
	public Image(String name, String imageName, int x, int y, int z) {
		super(name, x, y, z);
		this.imageName = imageName;
	}
	
	@Override
	public void draw (float deltaTime, SpriteBatch batch) {
		batch.draw(Assets.getImage(imageName), x, y);
	}

	@Override
	public Entity copy () {
		return new Image(name, imageName, (int)x, (int)y, (int)z);
	}
}
