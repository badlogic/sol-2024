package io.marioslab.sol.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import io.marioslab.sol.Assets;
import io.marioslab.sol.Game;
import io.marioslab.sol.Scene;
import io.marioslab.sol.command.MoveTo;
import io.marioslab.sol.command.MoveToAnim;
import io.marioslab.sol.command.NextScene;
import io.marioslab.sol.command.PlayMusic;
import io.marioslab.sol.command.PlaySound;
import io.marioslab.sol.command.Remove;
import io.marioslab.sol.command.SetAnimation;
import io.marioslab.sol.command.StopMusic;
import io.marioslab.sol.command.Wait;
import io.marioslab.sol.entity.Animated;
import io.marioslab.sol.entity.Fade;
import io.marioslab.sol.entity.Image;
import io.marioslab.sol.entity.MovingText;
import io.marioslab.sol.entity.Text;

public class MarioScene extends Scene {
	Animated stef;
	Array<Goomba> goombas = new Array<Goomba>();
	Vector3 touch = new Vector3();
	long kickstart = 0;
	float lastMovingDirection = 1;
	State state = State.Idle;
	
	String[] messages = {
		"Come Get Some",
		"Groovy",
		"And stay dead",
		"muahahaha",
		"blood is in the air",
		"bam",
		"Pow",
	};
	
	enum State {
		Left,
		Right,
		Idle,
		Kick
	}
	
	class Goomba extends Animated {
		float dir = -1;
		public Goomba (String name, String animationName, int x, int y, int z) {
			super(name, animationName, x, y, z);
		}
	}
	
	public MarioScene(boolean isRestart) {
		for(int i = 0; i < 20; i++) {
			Goomba g = new Goomba("g" + i, "goomba", 320 + i * MathUtils.random(20, 40), 64, MathUtils.random(1, 10));
			g.dir = -1;
			goombas.add(g);
			add(g);
		}
		
		for(int i = 0; i < 20; i++) {
			Goomba g = new Goomba("g" + i, "goomba", -100 - i * MathUtils.random(20, 40), 64, MathUtils.random(1, 10));
			g.dir = 1;
			goombas.add(g);
			add(g);
		}				
		
		add(new PlayMusic("mario"));
		add(new Image("background", "supermario", 0, 0, 0));
		stef = new Animated("stef", "idle-left", 280, 64, 0); 
		add(stef);
		
		add(new Fade(Color.WHITE, 1, true));
		add(new Wait(1));
		add(new MoveToAnim("stef", "walk", 87, 64, 64));
		
		if(!isRestart) {
			add(new SetAnimation("stef", "front"));
			add(new Wait(1));
			add(new SetAnimation("stef", "idle-left"));
			add(new Wait(1f));
			add(new SetAnimation("stef", "idle-right"));
			add(new Wait(1f));
			add(new SetAnimation("stef", "front"));
			add(new Text("W.T.F?!", Color.WHITE, 3, 87, 130));
			add(new Animated("goomba", "goomba", 320, 64, 0));
			add(new MoveTo("goomba", 240, 64, 50));
			add(new SetAnimation("stef", "idle-right"));
			add(new Wait(2));
			add(new SetAnimation("stef", "front"));
			add(new Text("Uh Oh", Color.WHITE, 2, 87, 130));
			add(new SetAnimation("stef", "idle-right"));
			add(new MoveTo("goomba", 104, 64, 50));
			add(new SetAnimation("goomba", "goomba-dead", false));
			add(new SetAnimation("stef", "kick-right", false));
			add(new PlaySound("chunli"));
			add(new Wait(1));
			add(new SetAnimation("stef", "front", false));
			add(new Text("Stomp Stomp Motherfucker", Color.RED, 2, 110, 130));
		} else {
			add(new Text("Bring it on", Color.RED, 2, 110, 130));
		}
		add(new Image("controller", "controller", 0, 0, 1));
	}	

	@Override
	public void update (float deltaTime) {
		super.update(deltaTime);
		
		if(commands.size == 0) {
			processInput(deltaTime);
			updateGoombas(deltaTime);
		}
	}
	
	Rectangle rNear = new Rectangle(0, 0, 0, 0);
	Rectangle rFar = new Rectangle(0, 0, 0, 0);
	Rectangle r2 = new Rectangle(0, 0, 0, 0);
	
	public void updateGoombas(float deltaTime) {
		rNear.set(stef.x+13, 0, 6, 240);
		rFar.set(stef.x+2, 0, 30, 240);
		int deadGoombas = 0;
		boolean dead = false;
		
		for(Goomba g: goombas) {			
			if(!g.animationName.equals("goomba-dead")) {
				g.x += g.dir * 36 * deltaTime;
				r2.set(g.x, 0, 16, 240);
				if(rFar.overlaps(r2) && state == State.Kick) {
					g.setAnimation("goomba-dead", false);
					if(MathUtils.random() > 0.7f) {
						Game.ctx.addDrawable(new MovingText(messages[MathUtils.random(0, messages.length - 1)], Color.RED, 1f, (int)stef.x, (int)stef.y + 100));
					}
				}
				if(rNear.overlaps(r2)) {
					dead = true;
				}
			} else {
				deadGoombas++;
			}
		}
		
		if(deadGoombas == goombas.size) {
			add(new Remove("controller"));
			add(new SetAnimation("stef", "front"));
			add(new MoveToAnim("stef", "walk", 160, 64, 64));
			add(new SetAnimation("stef", "front"));
			add(new Text("I Won't clean this mess up", Color.RED, 2, 160, 164));
			add(new Wait(2));
			add(new Image("mushroom", "mushroom", 240, 64, 0));
			add(new Wait(0.5f));
			add(new SetAnimation("stef", "idle-right"));
			add(new Text("UHHH a mushroom!", Color.MAGENTA, 2, 160, 164));
			add(new Wait(2));
			add(new MoveToAnim("stef", "walk", 240, 64, 64));
			add(new SetAnimation("stef", "front"));
			add(new Remove("mushroom"));
			add(new Text("Om nom nom", Color.MAGENTA, 2, 160, 164));
			add(new PlaySound("chewing"));
			add(new MoveToAnim("stef", "walk", 320, 64, 64));
			add(new Animated("mario", "mario-walk-right", 16, 64, 0));
			add(new MoveToAnim("mario", "mario-walk", 64, 64, 64));
			add(new SetAnimation("mario", "mario-right", true));
			add(new Text("It'se me! Ma...", Color.WHITE, 2, 100, 132));
			add(new Wait(2));
			add(new Text("Mamma Mia!", Color.WHITE, 3, 100, 132));			
			add(new Fade(Color.WHITE, 3f, false));						
			add(new Wait(3));			
			add(new StopMusic("mario"));
			add(new NextScene(new BowserScene()));
		}
		
		if(dead) {
			Assets.stopMusic("mario");
			add(new SetAnimation("stef", "front"));
			add(new Text("Ouch", Color.RED, 2, (int)stef.x, (int)stef.y + 100));
			add(new PlaySound("death"));
			add(new MoveTo("stef", (int)stef.x, (int)stef.y + 40, 40 * 3));
			add(new MoveTo("stef", (int)stef.x, (int)-64, 40 * 6));
			add(new Fade(Color.WHITE, 1.5f, false));
			add(new Wait(1.5f));
			add(new NextScene(new MarioScene(true)));
		}
	}
	
	public void processInput(float deltaTime) {
		boolean kickingStart = false;
		
		if(state != State.Kick) {
			state = State.Idle;
			
			if(Gdx.input.isKeyPressed(Keys.A)) {
				state = State.Left;
				lastMovingDirection = -1;
			}
			if(Gdx.input.isKeyPressed(Keys.D)) {
				state = State.Right;
				lastMovingDirection = 1;
			}
			
			for(int i = 0; i < 2; i++) {
				touch.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				Game.ctx.camera.unproject(touch);
				
				if(Gdx.input.isTouched(i)) {
					if(touch.x > 80 && touch.x < 112) {
						state = State.Left;
						lastMovingDirection = -1;
					}
					
					if(touch.x > 112 && touch.x < 133) {
						state = State.Right;
						lastMovingDirection = 1;
					}
				}
				
				if(Game.ctx.justTouched[i]) {																
					if(touch.x > 160) {
						state = State.Kick;
						kickingStart = true;
						Assets.playSound("chunli");
					}
				}									
			}							
		}				
		
		if(kickingStart) {
			kickstart = System.nanoTime();
			if(lastMovingDirection < 0) stef.setAnimation("kick-left", false);
			if(lastMovingDirection >= 0) stef.setAnimation("kick-right", false);
		}		
		
		if(state == State.Left) {
			stef.setAnimation("walk-left", true);
			stef.x += -80 * deltaTime;
			if(stef.x < 0) stef.x = 0;
		}
		if(state == State.Right) {
			stef.setAnimation("walk-right", true);
			stef.x += 80 * deltaTime;
			if(stef.x > 288) stef.x = 288;
		}
		
		if(state == State.Idle) {
			if(lastMovingDirection < 0) stef.setAnimation("idle-left", true);
			else stef.setAnimation("idle-right", true);
		}
		
		if(state == State.Kick && (System.nanoTime() - kickstart > 100000000)) {
			kickstart = 0;				
			state = State.Idle;
		}
	}
}
