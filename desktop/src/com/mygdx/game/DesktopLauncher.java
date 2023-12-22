package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.Heist;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Heist");
		config.setWindowedMode(1000,680);
		config.useVsync(true);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new Heist(), config);
	}
}

//"'You there! Stop!'\n The guard runs at you at full pelt, drawing his serrated knife with " +
//                "a snarl.\n Just as he puts his weight behind his weapon in anticipation of stabbing you through the chest," +
//                "the barrel of Wesdru's gun nudges him in the temple- a silent promise. 'Put the knife down,' she says coldly," +
//                "'before I blow your brains out.'"

//        Gdx.gl.glEnable(GL30.GL_BLEND);
//        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
//        game.shape.setProjectionMatrix(game.camera.combined);
//        game.shape.begin(ShapeRenderer.ShapeType.Filled);
//        game.shape.setColor(new Color(1, 0, 0, 0.5f));
//        game.shape.rect(100, 100, 100, 100);
//        game.shape.end();
//        Gdx.gl.glDisable(GL30.GL_BLEND);
//
//        game.shape.begin(ShapeRenderer.ShapeType.Line);
//        game.shape.setColor(new Color(0, 0, 0, 0));
//        game.shape.rect(100, 100, 100, 100);
//        game.shape.end();
//
//        game.shape.begin(ShapeRenderer.ShapeType.Line);
//        game.shape.setColor(new Color(0, 0, 0, 0));
//        game.shape.rect(100, 100, 100, 100);
//        game.shape.end();

//3 multiple bounty hunter cowboys spaghetti western with futuristic weapons talking and guarding a one floor one-story old dusty
//		warehouse from far away standing outside closed doors landscape

//        camera.update(); //tell camera to update its matrices
//        game.batch.setProjectionMatrix(camera.combined);
