package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BroadcastScreen implements Screen {

    final Heist game;

    private Texture backgroundImage;
    private final Music backgroundMusic;

    private TextButton button1;
    private TextButton button2;
    private TextArea textA;

    private Stage stage;
    private Table table;

    private String[] text;

    public BroadcastScreen (final Heist game) {
        this.game = game;

        //load images (64x64 pixels)
        backgroundImage = new Texture("21 broadcast.jpeg");

        //load sound effects and music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Big Fig Wasp.mp3"));
        backgroundMusic.setLooping(true);

    }
    @Override
    public void show() {
        backgroundMusic.play();

        //stage setup
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //table setup
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.pad(30);
        table.padTop(75);

        //button and text box setup

        text = returnText();

        textA = new TextArea(text[0], Heist.skin);
        table.add(textA).grow().colspan(3);

        table.row();

        button1 = new TextButton(text[1], Heist.skin);
        table.add(button1).width(150).expand();

        button2 = new TextButton(text[2], Heist.skin);
        table.add(button2).width(150).expand();


        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.wasCaptured) { //ATTACK
                    //funnel to good or bad end
                    game.setScreen(new GoodEndScreen(game));
                }
                else { //KILL HER
                    //funnel to good or bad end
                    game.setScreen(new GoodEndScreen(game));
                }
            }
        });

        //SWEET TALK THEM
        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.wasCaptured) { //SPIT IN HER FACE
                    //funnel to good or bad end
                    game.setScreen(new GoodEndScreen(game));
                }
                else { //SPARE HER
                    //funnel to good or bad end
                    game.setScreen(new GoodEndScreen(game));
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 0,0);

        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0);
        game.batch.end();

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
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    @Override
    public void dispose() {
        game.inHallSecondTime = true;
        backgroundImage.dispose();
        backgroundMusic.dispose();
        stage.dispose();
    }

    public String[] returnText() {
        String[] text = new String[3];
        text[0] = "TBW";

        //BROADCAST 2 - IF YOU'VE BEEN CAPTURED THEN WAITED
        if (game.attackedFirst) {
            text[0] = "TBW";
            text[1] = "Attack";
            text[2] = "Spit in her face";
        }
        else if ((game.wasCaptured) && (!game.withWesdru) && (game.enteredSR)) {
            text[0] = "TBW";
            text[1] = "Attack";
            text[2] = "Spit in her face";
        }
        else if ((game.wasCaptured) && (game.withWesdru) && (game.enteredSR)) {
            text[0] = "TBW";
            text[1] = "Attack";
            text[2] = "Spit in her face";
        }
        else if ((game.wasCaptured) && (!game.withWesdru)) {
            text[0] = "TBW";
            text[1] = "Attack";
            text[2] = "Spit in her face";
        }
        else if ((game.wasCaptured)) {
            text[0] = "TBW";
            text[1] = "Attack";
            text[2] = "Spit in her face";
        }
        //IF YOU AMBUSH ROOM (NOT CAPTURED)
        else if ((!game.withWesdru) && (game.enteredSR)) {
            text[0] = "TBW";
            text[1] = "Kill her";
            text[2] = "Spare her";
        }
        else if ((game.withWesdru) && (game.enteredSR)) {
            text[0] = "TBW";
            text[1] = "Kill her";
            text[2] = "Spare her";
        }
        else if ((!game.withWesdru)) {
            text[0] = "TBW";
            text[1] = "Kill her";
            text[2] = "Spare her";
        }
        else {
            text[0] = "TBW";
            text[1] = "Kill her";
            text[2] = "Spare her";
        }

        return text;
    }
}
