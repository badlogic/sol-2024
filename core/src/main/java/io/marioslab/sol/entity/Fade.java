package io.marioslab.sol.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import io.marioslab.sol.Entity;
import io.marioslab.sol.Game;

public class Fade extends Entity {
	Color color;
	float duration;
	boolean isFadeIn;
	float stateTime;
	
	public Fade (Color color, float duration, boolean isFadeIn) {
		super("", 0, 0, 10000000);
		this.color = color;
		this.duration = duration;
		this.isFadeIn = isFadeIn;
	}

	@Override
	public void draw (float deltaTime, SpriteBatch batch) {
		stateTime += deltaTime;
		if(stateTime > duration) {
			Game.ctx.removeDrawable(this);
		}
		
		float alpha = isFadeIn? (1 - stateTime / duration): (stateTime / duration);
		Game.ctx.getBatch().end();

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Game.ctx.getRenderer().begin(ShapeType.Filled);
		Game.ctx.getRenderer().setColor(color.r, color.g, color.b, alpha);
		Game.ctx.getRenderer().rect(0, 0, 320, 240);
		Game.ctx.getRenderer().end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		Game.ctx.getBatch().begin();
	}

	@Override
	public Entity copy () {
		return new Fade(color, duration, isFadeIn);
	}
}
