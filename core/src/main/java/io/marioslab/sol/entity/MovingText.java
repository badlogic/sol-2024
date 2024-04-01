package io.marioslab.sol.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MovingText extends Text {

	public MovingText (String text, Color color, float duration, int x, int y) {
		super(text, color, duration, x, y);
	}

	@Override
	public void draw (float deltaTime, SpriteBatch batch) {
		super.draw(deltaTime, batch);
		y+=deltaTime * 30;
	}
}