package io.marioslab.sol;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Game {
	public static Game ctx;
	SpriteBatch batch;
	public OrthographicCamera camera;
	Array<Entity> drawables = new Array<Entity>();
	Set<Entity> removed = new HashSet<Entity>();
	Scene scene;
	Vector3 v = new Vector3();
	ShapeRenderer renderer;
	public boolean[] justTouched = new boolean[10];
	public boolean[] justLifted = new boolean[10];

	public Game() {
		ctx = this;
		Assets.load();
		Inventory.clear();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 240);
		batch = new SpriteBatch();
		renderer = new ShapeRenderer();
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean touchDown (int screenX, int screenY, int pointer, int button) {
				justTouched[pointer] = true;
				camera.unproject(v.set(Gdx.input.getX(), Gdx.input.getY(), 0));
				return false;
			}

			@Override
			public boolean touchUp (int screenX, int screenY, int pointer, int button) {
				justLifted[pointer] = true;
				return false;
			}
		});
	}

	public void setScene(Scene scene) {
		drawables.clear();
		this.scene = scene;
	}

	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float deltaTime = Gdx.graphics.getDeltaTime();
		scene.update(deltaTime);

		drawables.sort(new Comparator<Entity>() {
			@Override
			public int compare (Entity o1, Entity o2) {
				return o1.z - o2.z;
			}
		});

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		renderer.setProjectionMatrix(camera.combined);

		batch.begin();
		for(Entity d: drawables) {
			d.draw(deltaTime, batch);
		}
		batch.end();

		if(removed.size() > 0) {
			Iterator<Entity> iter = drawables.iterator();
			while(iter.hasNext()) {
				if(removed.contains(iter.next())) {
					iter.remove();
				}
			}
		}

		for(int i = 0; i < justTouched.length; i++) {
			justTouched[i] = false;
			justLifted[i] = false;
		}
	}

	public void addDrawable(Entity drawable) {
		drawables.add(drawable);
	}

	public void removeDrawable(String name) {
		Iterator<Entity> iter = drawables.iterator();
		while(iter.hasNext()) {
			if(iter.next().name.equals(name)) {
				iter.remove();
				break;
			}
		}
	}

	public <T extends Entity> T getEntity (String name, Class<T> clazz) {
		Iterator<Entity> iter = drawables.iterator();
		while(iter.hasNext()) {
			Entity d = iter.next();
			if(d.name.equals(name)) {
				return (T)d;
			}
		}
		return null;
	}

	public int getX() {
		return (int)v.x;
	}

	public int getY() {
		return (int)v.y;
	}

	public void removeDrawable (Entity drawable) {
		removed.add(drawable);
	}

	public SpriteBatch getBatch () {
		return batch;
	}

	public ShapeRenderer getRenderer() {
		return renderer;
	}

	public Scene getScene () {
		return scene;
	}
}
