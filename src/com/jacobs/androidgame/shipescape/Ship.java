package com.jacobs.androidgame.shipescape;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class Ship extends DynamicGameObject {

	public static final float SHIP_MOVE = 20;
	public static final float SHIP_WIDTH = 1.0f;
	public static final float SHIP_HEIGHT = 1.0f;
	
	public boolean isAlive = true;
	float stateTime;
	
	public Ship(float x, float y) {
		super(x, y, SHIP_WIDTH / 3, SHIP_HEIGHT / 3);
		stateTime = 0;
	}
	
	public void update(float deltaTime) {
		//Update position of ship
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
		
		//Keep the ship from going outside the horizontal bounds
		if (position.x < 0)
			position.x = 0;
		if (position.x > World.WORLD_WIDTH)
			position.x = World.WORLD_WIDTH;
		
		stateTime += deltaTime;
	}
	
	public void hitAsteroid() {
		//If it hits an asteroid, it's game over
		velocity.set(0, 0);
		isAlive = false;
		
		stateTime = 0;
	}
}
