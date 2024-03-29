package com.badlogic.androidgames.framework.gl;

public class Animation {

	public static final int ANIMATION_LOOPING = 0;
	public static final int ANIMATION_NONLOOPING = 1;
	
	final TextureRegion[] keyFrames;
	final float frameDuration;
	
	public Animation(float frameDurection, TextureRegion ... keyFrames) {
		this.frameDuration = frameDurection;
		this.keyFrames = keyFrames;
	}
	
	public TextureRegion getKeyFrame(float stateTime, int mode) {
		int frameNumber = (int)(stateTime / frameDuration);
		
		if (mode == ANIMATION_NONLOOPING) {
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
		} else {
			frameNumber = frameNumber % keyFrames.length;
		}
		
		return keyFrames[frameNumber];
	}
}
