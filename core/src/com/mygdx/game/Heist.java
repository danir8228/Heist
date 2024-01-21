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
    public boolean usedLockpick = false;

    //determines endscreen
    public int END = 0;

    //for end tracker; doesn't reset
    public boolean END1 = false;
    public boolean END2 = false;
    public boolean END3 = false;
    public boolean END4 = false;
    public boolean END5 = false;
    public boolean END6 = false;
    public boolean END7 = false;
    public boolean END8 = false;
    public boolean END9 = false;
    public boolean END10 = false;
    public boolean END11 = false;

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

