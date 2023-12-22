package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

//entry point for the game.
public class Heist extends Game {

    //DRY (other screens will share these objects)
    public SpriteBatch batch;
    public BitmapFont font;

    //game file - decisions
    public boolean hasGrenade = false;
    public boolean hasLockpick = false;
    public boolean attackedFirst = false;
    public boolean withWesdru = false;
    public boolean enteredSR = false; //security room; false means entered MR (mystery room)
    public boolean inHallSecondTime = false;
    public boolean wasCaptured = false;
    public boolean badEscaped = false;
    public boolean usedGrenade = false;

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

