package com.jacobs.androidgame.shipescape;

import com.badlogic.androidgames.framework.Sound;
import com.badlogic.androidgames.framework.gl.Font;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

public class Assets {

	public static Texture background;
	public static TextureRegion backgroundRegion;
	
	public static Texture items;
	public static TextureRegion mainMenu;
	public static TextureRegion pauseMenu;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion highScoresRegion;
	public static TextureRegion logo;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion pause;
	public static TextureRegion ship;
	public static TextureRegion asteroid1;
	public static TextureRegion asteroid2;
	public static TextureRegion asteroid3;
	public static Font font;
	
	public static Sound clickSound;
	public static Sound explodeSound;
	
	public static void load(GLGame game) {
		background = new Texture(game, "background.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 320, 400);
		
		items = new Texture(game, "items.png");
		mainMenu = new TextureRegion(items, 0, 224, 300, 110);
		pauseMenu = new TextureRegion(items, 224, 128, 192, 96);
		ready = new TextureRegion(items, 320, 224, 192, 32);
		gameOver = new TextureRegion(items, 352, 256, 160, 96);
		highScoresRegion = new TextureRegion(items, 0, 260, 300, 110 / 3);
		logo = new TextureRegion(items, 0, 352, 274, 142);
		soundOff = new TextureRegion(items, 128, 64, 64, 64);
		soundOn = new TextureRegion(items, 128, 128, 64, 64);
		arrow = new TextureRegion(items, 0, 64, 64, 64);
		pause = new TextureRegion(items, 64, 64, 64, 64);
		ship = new TextureRegion(items, 0, 128, 32, 32);
		asteroid1 = new TextureRegion(items, 0, 0, 64, 64);
		asteroid2 = new TextureRegion(items, 64, 0, 64, 64);
		asteroid3 = new TextureRegion(items, 128, 0, 64, 64);
		
		font = new Font(items, 224, 0, 16, 16, 20);
		
		clickSound = game.getAudio().newSound("select.wav");
		explodeSound = game.getAudio().newSound("explode.wav");
	}
	
	public static void reload() {
		background.reload();
		items.reload();
		//if (Settings.soundEnabled)
			
	}
	
	public static void playSound(Sound sound) {
        if(Settings.soundEnabled)
            sound.play(1);
	}
}
