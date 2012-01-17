package com.jacobs.androidgame.shipescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.androidgames.framework.math.OverlapTester;

public class World {

	public interface WorldListener {
		public void hit();
	}
	
	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 30;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_GAME_OVER = 1;
	
	public final Ship ship;
	public final Background background1;
	public final Background background2;
	public final List<Asteroid> asteroids;
	public final Random rand;
	public final WorldListener listener;
	
	public int score;
	public int state;
	public float asteroidCreate;
	public float scoreTime;
	public int difficulty;
	public int scoreCheck;
	
	public World(WorldListener listener) {
		this.ship = new Ship(5, 2);
		this.listener = listener;
		rand = new Random();
		this.asteroids = new ArrayList<Asteroid>();
		this.background1 = new Background(5.0f, 22.5f, 320, 480);
		this.background2 = new Background(5.0f, 7.5f, 320, 480);
		
		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
		this.difficulty = 0;
		this.scoreTime = 0;
		this.scoreCheck = 0;
	}
	
	public void update(float deltaTime, float accelX) {
		//Update game objects
		updateBackground(deltaTime);
		updateShip(deltaTime, accelX);
		updateAsteroids(deltaTime);
		makeAsteroid(deltaTime);
		
		if (ship.isAlive)
			checkCollisions();
		
		checkGameOver();
		
	}
	
	private void updateBackground(float deltaTime) {
		//Update the scrolling background pieces
		background1.update(deltaTime);
		background2.update(deltaTime);
	}

	private void updateShip(float deltaTime, float accelX) {
		//If the ship has not collided with any asteroids, then update its velocity
		if (ship.isAlive) {
			ship.velocity.x = -accelX / 10 * Ship.SHIP_MOVE;
		}
		
		//Update the ship's position
		ship.update(deltaTime);	
	}
	
	private void updateAsteroids(float deltaTime) {
		int len = asteroids.size();
		
		//For each asteroid in the array, update its position
		for (int i = 0; i < len; i++) {
			Asteroid asteroid = asteroids.get(i);
			asteroid.update(deltaTime);
			
			//If asteroid is not alive anymore, then remove it from the list.
			if (!asteroid.isAlive) {
				asteroids.remove(asteroid);
				len = asteroids.size();
			}
		}

			
	}
	
	private void makeAsteroid(float deltaTime) {
		//Update the difficulty level every 20 seconds
		if (scoreCheck == 20 && score != 0) {
			difficulty++;
			scoreCheck = 0;
		}
		
		//Cap the difficulty at 5
		if (difficulty > 5)
			difficulty = 5;
		
		asteroidCreate += deltaTime;
		scoreTime += deltaTime;
		
		//Increment score every second
		if (scoreTime > 1.0f) {
			scoreTime = 0;
			score += 1;
			scoreCheck++;
		}
		
		//Create new asteroid depending on time expired and difficulty
		if (asteroidCreate > 1.0f - ((float)difficulty) * 0.15f) {
			float nextX;
			float nextY;
			
			asteroidCreate = 0.0f;
			
			nextX = rand.nextFloat() * WORLD_WIDTH;
			nextY = rand.nextFloat() * 5.0f + 15.0f;

			Asteroid newAsteroid = new Asteroid(nextX, nextY, 
					rand.nextFloat() + 1.0f);
			asteroids.add(newAsteroid);
		}
	}
	
	private void checkCollisions() {
		checkShipCollisions();
		checkAsteroidCollisions();
	}
	
	private void checkShipCollisions() {
		int len = asteroids.size();
		
		//Check if ship has collided with any asteroids
		for (int i = 0; i < len; i++) {
			if (OverlapTester.overlapCircleRectangle(asteroids.get(i).bound, ship.bounds)) {
				ship.isAlive = false;
				Assets.playSound(Assets.explodeSound);
			}
		}
	}
	
	private void checkAsteroidCollisions() {
		int len = asteroids.size();
		//Check if asteroid has collided with other asteroids
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				if (OverlapTester.overlapCircles(asteroids.get(i).bound, asteroids.get(j).bound)) {
					asteroids.get(i).resolveCollision(asteroids.get(j));
				}
			}
		}
		
		
	}
	
	private void checkGameOver() {
		if (!ship.isAlive) {
			state = WORLD_STATE_GAME_OVER;
		}
	}
}
