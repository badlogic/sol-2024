package io.marioslab.sol;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
	public String name;
	public int z;
	public float x;
	public float y;
	
	public Entity(String name, int x, int y, int z) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public abstract void draw(float deltaTime, SpriteBatch batch);

	public abstract Entity copy ();
}
