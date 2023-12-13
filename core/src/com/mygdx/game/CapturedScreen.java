package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CapturedScreen implements Screen {

    final Heist game;

    private final Texture backgroundImage;
    private final Music backgroundMusic;

    private TextButton button1;
    private TextButton button2;
    private TextButton button3;
    private TextArea text;

    private Stage stage;
    private Table table;

    private OrthographicCamera camera;

    public CapturedScreen (final Heist game) {
        this.game = game;

        //load images (64x64 pixels)
        backgroundImage = new Texture("background.jpeg");

        //load sound effects and music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("ironLung.mp3"));
        backgroundMusic.setLooping(true);

        //create camera and spriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //stage setup
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //table setup
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.pad(50);
        table.padTop(100);

        //button and text box setup
        String dialogue = "You turn around from the market stall to the sound of your name at a shout. It's your long-time friend and " +
                " fellow bounty hunter, Wesdru. There's an urgency to her mannerisms that raises your heart rate immediately, " +
                "your hand flying to your sword pommel at your hip. 'It's Lorena. Dras has her.' Keegin Dras, this lawless town's " +
                "most feared crime boss. 'What,' you intone breathlessly. 'She doesn't stoop to meddle with cowboys!' Wesdru grabs " +
                "your shoulders roughly and makes brutal eye contact. 'I don't know why. But I do know we have to go NOW. You and I " +
                "both know how cruel Dras can be.'\n\n\n\nYou only have enough time to buy one thing from the market. What do you take?";


        text = new TextArea(dialogue, Heist.skin);
        table.add(text).grow().colspan(2);

        table.row();

        button1 = new TextButton("Modded Grenade", Heist.skin);
        table.add(button1).width(200).expand();

        button2 = new TextButton("Lockpick", Heist.skin);
        table.add(button2).width(100).expand();

        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.hasGrenade = true;
            }
        });

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.hasLockpick = true;
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
