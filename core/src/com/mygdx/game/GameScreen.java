package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class GameScreen implements Screen {

    final Heist game;

    private final Texture backgroundImage;
    private final Music backgroundMusic;
    private OrthographicCamera camera;

    private Stage stage;
    private TextButton button1;
    private static Skin skin;


    public GameScreen(final Heist game) {
        this.game = game;

        //load images (64x64 pixels)
        backgroundImage = new Texture("background.jpeg");

        //load sound effects and music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("ironLung.mp3"));
        backgroundMusic.setLooping(true);

        //create camera and spriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);


        //button setup
        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        button1 = new TextButton("RUN!", skin);
        button1.setPosition(stage.getWidth()/2, stage.getHeight()/2, Align.center);
        stage.addActor(button1);
    }

    @Override
    public void show() { //called when screen is shown.
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);

        camera.update(); //tell camera to update its matrices
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0);
        game.batch.end();

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
        backgroundImage.dispose();
        backgroundMusic.dispose();
    }
}
