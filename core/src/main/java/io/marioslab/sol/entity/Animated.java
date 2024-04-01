package io.marioslab.sol.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.marioslab.sol.Assets;
import io.marioslab.sol.Entity;

public class Animated extends Entity {
	public String animationName;
	float stateTime;
	boolean looping = true;
	
	public Animated (String name, String animationName, int x, int y, int z) {
		super(name, x, y, z);
		this.animationName = animationName;
	}
	
	public Animated (String name, String animationName, int x, int y, int z, boolean looping) {
		super(name, x, y, z);
		this.animationName = animationName;
		this.looping = looping;
	}

	@Override
	public void draw (float deltaTime, SpriteBatch batch) {
		stateTime += deltaTime;
		TextureRegion region = Assets.getAnimation(animationName).getKeyFrame(stateTime, looping);
		batch.draw(region, x, y);
	}
	
	public void setAnimation(String animationName, boolean looped) {
		if(this.animationName.equals(animationName) && this.looping == looped) return;
		this.animationName = animationName;
		this.stateTime = 0;
		this.looping = looped;
	}

	@Override
	public Entity copy () {
		return new Animated(name, animationName, (int)x, (int)y, (int)z);
	}
}
