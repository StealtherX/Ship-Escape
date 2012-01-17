package com.jacobs.androidgame.shipescape;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class Background extends DynamicGameObject {

	public Background(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.set(0.0f, -1.2f);
	}
	
	public void update(float deltaTime) {
		//Update position of scrolling background
		position.add(0.0f, velocity.y * deltaTime);
		
		//If background is off the screen then move it above the screen to
		//continue scrolling down
		if (position.y < -7.5f)
			position.y = 22.5f;
			
	}

}
