package io.marioslab.sol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;

public class Assets {
	static ObjectMap<String, Animation<TextureRegion>> animations = new ObjectMap<String, Animation<TextureRegion>>();
	static ObjectMap<String, TextureRegion> images = new ObjectMap<String, TextureRegion>();
	static ObjectMap<String, Sound> sounds = new ObjectMap<String, Sound>();
	static ObjectMap<String, Music> music = new ObjectMap<String, Music>();
	public static BitmapFont font;
	
	public static void load() {
		// stef
		animations.put("idle-right", loadAnim("stef/stef-idle.png", 32, 64, 0.5f, false));
		animations.put("idle-left", loadAnim("stef/stef-idle.png", 32, 64, 0.5f, true));
		animations.put("walk-right", loadAnim("stef/stef-walk.png", 32, 64, 0.15f, false));
		animations.put("walk-left", loadAnim("stef/stef-walk.png", 32, 64, 0.15f, true));
		animations.put("back", loadAnim("stef/stef-back.png", 32, 64, 0.5f, false));
		animations.put("front", loadAnim("stef/stef-front.png", 32, 64, 0.5f, false));
		animations.put("kick-right", loadAnim("stef/stef-kick.png", 32, 64, 0.5f, false));
		animations.put("kick-left", loadAnim("stef/stef-kick.png", 32, 64, 0.5f, true));
		animations.put("paddle", loadAnim("stef/stef-paddle.png", 32, 64, 0.5f, false));
		animations.put("kiss", loadAnim("stef/stef-kiss.png", 32, 64, 0.3f, false));
		
		animations.put("realmario-idle-right", loadAnim("stef/mario-idle.png", 32, 64, 0.5f, false));
		animations.put("realmario-idle-left", loadAnim("stef/mario-idle.png", 32, 64, 0.5f, true));
		animations.put("realmario-walk-right", loadAnim("stef/mario-walk.png", 32, 64, 0.15f, false));
		animations.put("realmario-walk-left", loadAnim("stef/mario-walk.png", 32, 64, 0.15f, true));
		animations.put("realmario-front", loadAnim("stef/mario-front.png", 32, 64, 0.3f, true));
		animations.put("realmario-censored", loadAnim("stef/mario-censored.png", 32, 64, 0.3f, false));
		animations.put("realmario-censored-tada", loadAnim("stef/mario-censored-left.png", 32, 64, 0.3f, false));
		animations.put("realmario-kiss", loadAnim("stef/mario-kiss.png", 32, 64, 0.3f, false));
		
		// outdoor
		images.put("outdoor", loadImage("outdoor/outdoor.png"));
		animations.put("bird", loadAnim("outdoor/bird.png", 16, 16, 2, false));
		animations.put("bird-fly", loadAnim("outdoor/bird-fly.png", 16, 16, 0.3f, false));
		
		// entrance
		images.put("entrance", loadImage("entrance/entrance.png"));
		images.put("door", loadImage("entrance/door.png"));
		images.put("opendoor", loadImage("entrance/opendoor.png"));
		animations.put("panel", loadAnim("entrance/panel.png", 7, 11, 1, false));
		
		// mario
		images.put("supermario", loadImage("mario/supermario.png"));
		animations.put("goomba", loadAnim("mario/goomba.png", 16, 16, 0.5f, false));
		animations.put("goomba-dead", loadAnim("mario/goomba-dead.png", 16, 16, 0.1f, false));
		images.put("mushroom", loadImage("mario/mushroom.png"));
		animations.put("mario-right", loadAnim("mario/mario.png", 16, 16, 1, false));
		animations.put("mario-left", loadAnim("mario/mario.png", 16, 16, 1, true));
		animations.put("mario-walk-left", loadAnim("mario/mario-walk.png", 16, 16, 0.3f, true));
		animations.put("mario-walk-right", loadAnim("mario/mario-walk.png", 16, 16, 0.3f, false));
		images.put("controller", loadImage("mario/controller.png"));
		
		// bowser
		images.put("dungeon", loadImage("bowser/dungeon.png"));
		images.put("chain", loadImage("bowser/chain.png"));
		images.put("fireball", loadImage("bowser/fire.png"));
		animations.put("bowser", loadAnim("bowser/bowser.png", 32, 32, 0.3f, false));
		animations.put("bowser-hit", loadAnim("bowser/bowser-hit.png", 32, 32, 0.3f, false));
		
		// sounds
		sounds.put("door-open", loadSound("audio/door-open.mp3"));
		sounds.put("door-close", loadSound("audio/door-close.mp3"));
		sounds.put("door-bell", loadSound("audio/door-bell.mp3"));
		sounds.put("vulture", loadSound("audio/vulture.mp3"));
		sounds.put("glass", loadSound("audio/glass.mp3"));
		sounds.put("heating", loadSound("audio/heating.mp3"));
		sounds.put("flush", loadSound("audio/flush.mp3"));
		sounds.put("achievement", loadSound("audio/achievement.mp3"));
		sounds.put("shower", loadSound("audio/shower.mp3"));
		sounds.put("chunli", loadSound("audio/chunli.mp3"));
		sounds.put("chewing", loadSound("audio/chewing.mp3"));
		sounds.put("death", loadSound("audio/mariodeath.mp3"));
		sounds.put("bowserdeath", loadSound("audio/bowserdeath.mp3"));
		sounds.put("trumpet", loadSound("audio/trumpet.mp3"));
		sounds.put("coin", loadSound("audio/coin.mp3"));
		sounds.put("bump", loadSound("audio/bump.mp3"));
		sounds.put("block", loadSound("audio/block.mp3"));
		
		// music
		music.put("adventure", loadMusic("audio/adventure.mp3"));
		music.put("indoors", loadMusic("audio/indoors.mp3"));
		music.put("mario", loadMusic("audio/mario.mp3"));
		music.put("castle", loadMusic("audio/castle.mp3"));
		music.put("win", loadMusic("audio/win.mp3"));
		music.put("zelda", loadMusic("audio/zelda.mp3"));
		
		font = new BitmapFont(); // new FreeTypeFontGenerator(Gdx.files.internal("wendy.ttf")).generateFont(20, FreeTypeFontGenerator.DEFAULT_CHARS, false);
	}
	
	private static Music loadMusic (String file) {
		return Gdx.audio.newMusic(Gdx.files.internal(file));
	}

	private static Sound loadSound (String file) {
		return Gdx.audio.newSound(Gdx.files.internal(file));
	}

	private static TextureRegion loadImage(String fileName) {
		return new TextureRegion(new Texture(fileName));
	}
	
	private static Animation<TextureRegion> loadAnim(String fileName, int fWidth, int fHeight, float frameDuration, boolean flipX) {
		Texture texture = new Texture(fileName);
		TextureRegion[] frames = TextureRegion.split(texture, fWidth, fHeight)[0];
		if(flipX) {
			for(TextureRegion r: frames) {
				r.flip(true, false);
			}
		}
		return new Animation<TextureRegion>(frameDuration, frames);
	}
	
	public static Animation<TextureRegion> getAnimation(String name) {
		Animation<TextureRegion> anim = animations.get(name);
		if(anim == null) throw new GdxRuntimeException("Couldn't find animation: " + name);
		return anim;
	}
	
	public static TextureRegion getImage(String name) {
		TextureRegion img = images.get(name);
		if(img == null) throw new GdxRuntimeException("Couldn't find image: " + name);
		return img;
	}
	
	public static void playSound(String name) {
		sounds.get(name).play();
	}
	
	public static void playMusic(String name, float volume, boolean looped) {
		music.get(name).setVolume(volume);
		music.get(name).setLooping(looped);
		music.get(name).play();	
	}
	
	public static void stopMusic(String name) {
		music.get(name).stop();
	}
}
