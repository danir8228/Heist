package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

//entry point for the game.
public class Heist extends Game {

    //DRY (other screens will share these objects)
    public SpriteBatch batch;
    public BitmapFont font;
    public OrthographicCamera camera;

    //game file - decisions
    public boolean hasGrenade;
    public boolean hasLockpick;
    public boolean isInjured;
    public boolean camerasOff;
    public boolean hasPapers;

    static Skin skin;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

        this.setScreen(new MainMenuScreen(this)); //set screen to MainMenuScreen object with instance of Heist as param
    }

    @Override
    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}

