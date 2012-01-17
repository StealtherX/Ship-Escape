package com.jacobs.androidgame.shipescape;

import com.badlogic.androidgames.framework.DynamicGameObject;
import com.badlogic.androidgames.framework.math.Circle;
import com.badlogic.androidgames.framework.math.Vector2;

public class Asteroid extends DynamicGameObject {

	public static final float DEFAULT_HEIGHT = 2;
	public static final float DEFAULT_WIDTH = 2;
	public static final float DEFAULT_VELOCITY_Y = -8f;
	public static final float DEFAULT_VELOCITY_X = 1.0f;
	public static final int ASTEROID_TYPE_1 = 0;
	public static final int ASTEROID_TYPE_2 = 1;
	public static final int ASTEROID_TYPE_3 = 2;
	public float scale;
	public boolean isAlive;
	public final int type;
	
	float stateTime = 0;
	Circle bound;
	
	public Asteroid(float x, float y, float scale) {
		super(x, y, scale * DEFAULT_WIDTH, scale * DEFAULT_HEIGHT);
		this.scale = scale;
		float randSpeed = (float)Math.random() + 0.5f;
		velocity.set(randSpeed * DEFAULT_VELOCITY_X,
				randSpeed * DEFAULT_VELOCITY_Y);
		if (Math.random() > 0.5f)
			velocity.x *= -1;
		isAlive = true;
		bound = new Circle(x, y, scale * DEFAULT_WIDTH / 4);
		type = (int)(Math.random() * 3.0f);
	}

	public void update(float deltaTime) {
		//Update position and boundary of the asteroid
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(DEFAULT_WIDTH * scale / 2, 
				DEFAULT_HEIGHT * scale / 2);
		bound.center.set(position.x, position.y);
		
		//If asteroid is off the screen, then flag it for deletion
		if (position.x < -2)
			isAlive = false;
		if (position.x > World.WORLD_WIDTH + 2)
			isAlive = false;
		if (position.y < -3)
			isAlive = false;
		
		stateTime += deltaTime; 
	}
	
	public void resolveCollision(Asteroid other) {

		float m1 = this.scale;
		float m2 = other.scale;
		float v1 = (float) this.velocity.x;
		float v2 = (float) other.velocity.x;
		
		//Resolve collision with another asteroid using basic kinematics
		float newVx1 = (v1*(m1 - m2) + 2*m2*v2) / (m1 + m2); 
		float newVx2 = (v2*(m2 - m1) + 2*m1*v1) / (m1 + m2);
		
		v1 = (float) this.velocity.y;
		v2 = (float) other.velocity.y;
		
		float newVy1 = (v1*(m1 - m2) + 2*m2*v2) / (m1 + m2); 
		float newVy2 = (v2*(m2 - m1) + 2*m1*v1) / (m1 + m2);
		
		this.velocity.set(new Vector2(newVx1, newVy1));
		other.velocity.set(new Vector2(newVx2, newVy2));
	}
}
