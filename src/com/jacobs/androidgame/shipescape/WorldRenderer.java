package com.jacobs.androidgame.shipescape;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLGraphics;

public class WorldRenderer {

	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	GLGraphics glGraphics;
	World world;
	Camera2D cam;
	SpriteBatcher batcher;
	
	public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
		this.glGraphics = glGraphics;
		this.world = world;
		this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.batcher = batcher;
	}
	
	public void render() {
		cam.setViewportAndMatrices();
		renderBackground();
		renderObjects();
	}
	
	public void renderBackground() {
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(world.background1.position.x, world.background1.position.y,
				FRUSTUM_WIDTH, FRUSTUM_HEIGHT,
				Assets.backgroundRegion);
		batcher.drawSprite(world.background2.position.x, world.background2.position.y,
				FRUSTUM_WIDTH, FRUSTUM_HEIGHT,
				Assets.backgroundRegion);
		batcher.endBatch();
	}
	
	public void renderObjects() {
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		renderShip();
		renderAsteroids();
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void renderShip() {
		batcher.drawSprite(world.ship.position.x, world.ship.position.y, 1, 1, Assets.ship);
	}
	
	private void renderAsteroids() {
		int len = world.asteroids.size();
		for (int i = 0; i < len; i++) {
			Asteroid asteroid = world.asteroids.get(i);
			switch (asteroid.type) {
			case Asteroid.ASTEROID_TYPE_1:
				batcher.drawSprite(asteroid.position.x, asteroid.position.y,
						asteroid.scale, asteroid.scale, Assets.asteroid1);
				break;
			case Asteroid.ASTEROID_TYPE_2:
				batcher.drawSprite(asteroid.position.x, asteroid.position.y,
						asteroid.scale, asteroid.scale, Assets.asteroid2);
				break;
			case Asteroid.ASTEROID_TYPE_3:
				batcher.drawSprite(asteroid.position.x, asteroid.position.y,
						asteroid.scale, asteroid.scale, Assets.asteroid3);
				break;
			}

		}
	}
}
