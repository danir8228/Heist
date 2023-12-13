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


public class OutsideScreen implements Screen {

    final Heist game;

    private final Texture backgroundImage;
    private final Music backgroundMusic;

    private TextButton button1;
    private TextButton button2;
    private TextButton button3;
    private TextArea text;

    private Stage stage;
    private Table table;

    public OutsideScreen(final Heist game) {
        this.game = game;

        //load images (64x64 pixels)
        backgroundImage = new Texture("state2.png");

        //load sound effects and music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Doom City.mp3"));
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

        String dialogue = "Wesdru leads you through the city to one of Dras's storage facilities, only known to people" +
                "--cowboys--who deliberately keep tabs on her. It's a nondescript warehouse, dusty and unassuming from " +
                "a couple blocks down. 'There's no way we'll get in through the front; there's too many guards, not to " +
                "mention the security cameras. But the back should be clean.' The few pedestrians that scurry by serve " +
                "as perfect cover for you and Wesdru to sneak into any alleyway with a clear view of the back entrance. " +
                "Sure enough, there are only three guards--armed to the teeth, but chatting casually. Distractedly. \n" +
                "\n If we just charge it'll be hard to surprise them considering there's not much cover,' you muse. " +
                "'I might be able to convince them to let us in.' Wesdru chuckles fondly. 'You wish! " +
                "We should climb onto the roof from the building over and try to find a way in from there. " +
                "Only Lorena's charming enough to sweet talk three of Dras's guards.' \n\n" +
                "The comment sobers both of you, the stakes newly fresh. 'Lets...'";

        text = new TextArea(dialogue, Heist.skin);
        table.add(text).grow().colspan(3);

        table.row();

        button1 = new TextButton("Storm them", Heist.skin);
        table.add(button1).width(150).expand();

        button2 = new TextButton("Sweet talk them", Heist.skin);
        table.add(button2).width(150).expand();

        button3 = new TextButton("Climb the building", Heist.skin);
        table.add(button3).width(175).expand();

        //STORM THEM
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.hasGrenade = true;
                game.setScreen(new CapturedScreen(game));
            }
        });

        //SWEET TALK THEM
        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.hasLockpick = true;
                game.setScreen(new CapturedScreen(game));
            }
        });

        //CLIMB THE BUILDING
        button3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.hasLockpick = false;
                game.hasGrenade = false;
                game.setScreen(new MarketScreen(game));
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);

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
        backgroundImage.dispose();
        backgroundMusic.dispose();
        stage.dispose();
    }
}
