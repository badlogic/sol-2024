package io.marioslab.sol.scenes;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import io.marioslab.sol.Assets;
import io.marioslab.sol.Game;
import io.marioslab.sol.Scene;
import io.marioslab.sol.command.MoveTo;
import io.marioslab.sol.command.MoveToAnim;
import io.marioslab.sol.command.PlayMusic;
import io.marioslab.sol.command.PlaySound;
import io.marioslab.sol.command.Remove;
import io.marioslab.sol.command.SetAnimation;
import io.marioslab.sol.command.Wait;
import io.marioslab.sol.entity.Animated;
import io.marioslab.sol.entity.Fade;
import io.marioslab.sol.entity.Image;
import io.marioslab.sol.entity.Text;

public class BowserScene extends Scene {
	Animated stef;
	Animated bowser;
	Animated mario;
	
	public BowserScene() {
		add(new Image("background", "dungeon", 0, 0, 0));
		stef = new Animated("stef", "idle-right", -32, 60, 0);
		bowser = new Animated("bowser", "bowser", 230, 60, 3);
		mario = new Animated("mario", "realmario-idle-left", 320, 60, 0);
		add(new PlayMusic("castle"));
		add(stef);
		add(bowser);
		add(mario);
		add(new Image("chain", "chain", 280, 60, 1));
		
		add(new Fade(Color.WHITE, 1, true));
		add(new Wait(1));
		add(new MoveToAnim("stef", "walk", 52, 60, 64));
		add(new SetAnimation("stef", "idle-right"));
		add(new Text("I've seen this before", Color.WHITE, 2, 100, 140));
		add(new Wait(2));
		add(new Text("Just need to hit the axe!", Color.WHITE, 2, 100, 140));
		add(new Wait(2));
		add(new Text("Not quite my dear", Color.RED, 2, 180, 140));
		add(new Wait(2));
		add(new Text("I have a surprise for you", Color.RED, 2, 180, 140));
		add(new Wait(2));
		add(new MoveToAnim("mario", "realmario-walk", 284, 60, 64));
		add(new SetAnimation("mario", "realmario-idle-left"));
		add(new Wait(1));
		add(new Text("I'm really sorry", Color.CYAN, 2, 240, 140));
		add(new Wait(2));
		add(new Text("What did you do this time?", Color.WHITE, 3, 100, 140));
		add(new Wait(3));
		add(new Text("It's a long story", Color.CYAN, 2, 240, 140));
		add(new Wait(2));
		add(new Text("It involves computers and midgets", Color.CYAN, 4, 180, 140));
		add(new Wait(4));
		add(new Text("and one drunken pony", Color.CYAN, 3, 200, 140));
		add(new Wait(3));
		add(new Text("Damn ponies", Color.WHITE, 2, 100, 140));
		add(new Wait(2));
		add(new Text("Enough!", Color.RED, 2, 180, 140));
		add(new Wait(2));
		add(new Text("I challenge you to a battle to death..", Color.RED, 3.5f, 150, 140));
		add(new Wait(3.5f));
		add(new Text("... Of pong", Color.RED, 3.5f, 150, 140));
		add(new Wait(3.5f));
		add(new Text("That makes no sense", Color.WHITE, 2, 100, 140));
		add(new Wait(2));
		add(new Text("Now it does! HAR HAR HAR HAR HAR", Color.RED, 3.5f, 150, 140));
		add(new SetAnimation("stef", "paddle"));
		add(new Wait(3.5f));
	}

	class Fireball extends Image {
		float dirX = -1, dirY = 0;
		int bounces = 0;
		
		public Fireball (int x, int y) {
			super("fireball", "fireball", x, y, 2);
		}

		@Override
		public void draw (float deltaTime, SpriteBatch batch) {
			if(dirX < 0) {
				batch.draw(Assets.getImage(imageName), x, y);
			} else {
				TextureRegion image = Assets.getImage(imageName);
				image.flip(true, false);
				batch.draw(image, x, y);
				image.flip(true, false);
			}
		}
		
		public void normalizeDir() {
			float len = (float)Math.sqrt(dirX * dirX + dirY * dirY);
			if(len != 0) {
				dirX /= len;
				dirY /= len;
			}
		}
	}
	
	long lastFire;
	Array<Fireball> fireballs = new Array<Fireball>();
	float lastY = 0;
	float bowserJumpVel = 10;
	int bowserHealth = 3;
	long bowserHitTime = 0;
	float GRAVITY = -90f;
	
	@Override
	public void update (float deltaTime) {
		super.update(deltaTime);
		
		if(commands.size == 0 && bowserHealth > 0) {									
			processInput();
			updateFireballs(deltaTime);
			updateBowser(deltaTime);					
		}		
	}
	
	public void processInput() {
		if(Gdx.input.isTouched()) {
			float delta = Gdx.input.getDeltaY() / (float)Gdx.graphics.getHeight() * 240;
			stef.y -= delta;
			if(stef.y < 60) stef.y = 60;
			if(stef.y > 204 - 32) stef.y = 204 - 32;
		}
	}
		
	public void updateFireballs(float deltaTime) {
		Iterator<Fireball> iter = fireballs.iterator();
		while(iter.hasNext()) {
			Fireball fb = iter.next();
			fb.x += fb.dirX * deltaTime * 100;
			fb.y += fb.dirY * deltaTime * 100;
			if(fb.x < -16) {
				iter.remove();
				Game.ctx.removeDrawable(fb);
			}
			
			if(fb.x > 66 && fb.x < 73) {
				if(fb.y > stef.y - 16 && fb.y < stef.y + 56) {
					fb.dirX = 1;					
					fb.normalizeDir();
					fb.bounces ++;
					Assets.playSound("bump");
				}
			}
			
			if(fb.x > 265) {
				fb.dirX = -1;
				fb.normalizeDir();
				fb.bounces++;
				Assets.playSound("bump");
			}
			
			if(fb.y + 16 > 223) {
				fb.y = 223 - 17;
				fb.dirY = -fb.dirY;
				fb.normalizeDir();
				fb.bounces++;
				Assets.playSound("bump");
			}
			
			if(fb.y < 60) {
				fb.y = 60;
				fb.dirY = -fb.dirY;
				fb.normalizeDir();
				fb.bounces++;
				Assets.playSound("bump");
			}
			
			if(fb.bounces > 3) {
				iter.remove();
				Game.ctx.removeDrawable(fb);
				Assets.playSound("bump");
			}
			
			if(fb.x + 8 > bowser.x && fb.x + 8 < bowser.x + 32) {
				if(fb.y + 8 > bowser.y && fb.y + 8 < bowser.y + 32) {
					iter.remove();
					Game.ctx.removeDrawable(fb);
					bowserHealth--;
					bowserHitTime = System.nanoTime();
					bowser.setAnimation("bowser-hit", true);
					Assets.playSound("block");
				}
			}
		}
	}
	
	float bowserDir = -1;
	public void updateBowser(float deltaTime) {
		bowser.x += bowserDir * 30 * deltaTime;
		if(bowser.x < 160) {
			bowserDir = 1;
			bowser.x = 160;
		}
		if(bowser.x > 230) {
			bowserDir = -1;
			bowser.x = 230;
		}
		
		bowser.y += bowserJumpVel * deltaTime + GRAVITY * deltaTime;
		bowserJumpVel *= 0.98f;
		if(bowser.y < 60) {
			bowser.y = 60;
			bowserJumpVel = 200;
		}
		
		if(System.nanoTime() - lastFire > 2000000000l) {
			Fireball fb = new Fireball((int)bowser.x - 16, (int)bowser.y + 20);
			fb.dirY = MathUtils.random(-1f, 1f);
			fb.normalizeDir();
			fireballs.add(fb);
			Game.ctx.addDrawable(fb);
			lastFire = System.nanoTime();
		}
		
		if(System.nanoTime() - bowserHitTime > 2000000000) {
			bowser.setAnimation("bowser", true);
		}
		
		if(bowserHealth <= 0) {
			endSequence();
		}
	}
	
	public void endSequence() {
		Assets.stopMusic("castle");
		Assets.playSound("bowserdeath");
		
		
		for(Fireball fb: fireballs) {
			Game.ctx.removeDrawable(fb);
		}
		add(new SetAnimation("bowser", "bowser-hit"));
		add(new MoveTo("bowser", (int)bowser.x, -32, 100));
		add(new PlayMusic("win", 1, false));
		add(new MoveTo("stef", (int)stef.x, 60, 64));
		add(new SetAnimation("stef", "idle-right"));
		add(new Remove("chain"));
		add(new MoveToAnim("mario", "realmario-walk", 160, 60, 64));
		add(new SetAnimation("mario", "realmario-idle-left"));
		add(new MoveToAnim("stef", "walk", 160 - 5 - 32, 60, 64));
		add(new SetAnimation("stef", "idle-right"));
		add(new Text("That was ... weird", Color.WHITE, 3, 160, 140));
		add(new Wait(3));
		add(new Text("That's how we like it, right?", Color.CYAN, 3, 160, 140));
		add(new Wait(3));
		add(new Text("I guess so", Color.WHITE, 2, 160, 140));
		add(new Wait(2));
		add(new Text("Can we go home now?", Color.WHITE, 2, 160, 140));
		add(new PlayMusic("zelda"));
		add(new Wait(2));
		add(new Text("Nooo, it's your 20th birthday!", Color.CYAN, 3, 160, 140));
		add(new Wait(3));
		add(new Text("You first have to unpack your presents!", Color.CYAN, 3, 160, 140));
		add(new Wait(3));
		add(new SetAnimation("mario", "realmario-front"));		
		add(new Text("MY first present to you is...", Color.CYAN, 3, 160, 140));
		add(new Wait(3));
		add(new SetAnimation("mario", "realmario-censored-tada"));
		add(new Text("...ME! TADA!", Color.CYAN, 3, 160, 140));
		add(new PlaySound("trumpet"));
		add(new Wait(5));
		add(new SetAnimation("mario", "realmario-censored"));
		add(new Wait(2));
		add(new Text("well, good thing i have a fallback plan!", Color.CYAN, 4, 160, 140));
		add(new Wait(4));
		add(new Text("*400 COINS transfered to bankaccount*", Color.MAGENTA, 5, 160, 140));
		add(new PlaySound("coin"));
		add(new SetAnimation("mario", "realmario-censored-tada"));
		add(new Wait(5));
		add(new SetAnimation("mario", "realmario-censored"));
		add(new Text("these are to be spent on fancy loot at PSYLO!", Color.CYAN, 8, 160, 140));
		add(new Wait(8));	
		add(new Text("But i...", Color.WHITE, 2, 160, 140));
		add(new Wait(2));
		add(new Text("Silence", Color.CYAN, 2, 160, 140));
		add(new SetAnimation("mario", "realmario-censored-tada"));
		add(new Wait(2));
		add(new Text("For my final trick, you have to turn around...", Color.CYAN, 3, 160, 140));
		add(new SetAnimation("mario", "realmario-censored"));		
		add(new Wait(2));
		add(new SetAnimation("stef", "idle-left"));
		add(new Wait(1));
		add(new Text("In real life!", Color.CYAN, 4, 160, 140));
		add(new SetAnimation("mario", "realmario-censored-tada"));		
		add(new Wait(4));
		add(new SetAnimation("stef", "idle-right"));
		add(new MoveToAnim("mario", "realmario-walk", 143, 60, 64));
		add(new SetAnimation("stef", "kiss", false));
		add(new SetAnimation("mario", "realmario-kiss", false));
		add(new Text("* FIN *", Color.MAGENTA, 3600, 150, 140));
		add(new Wait(3600));
	}
}
