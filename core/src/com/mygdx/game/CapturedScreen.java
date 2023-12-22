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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CapturedScreen implements Screen {

    final Heist game;

    private final Texture backgroundImage;
    private final Music backgroundMusic;

    private int count;

    private TextButton button1;
    private TextButton button2;
    private TextArea textA;

    private Stage stage;
    private Table table;

    private String text;

    public CapturedScreen (final Heist game) {
        this.game = game;

        //load images (64x64 pixels)
        backgroundImage = new Texture("20 jails.png");

        //load sound effects and music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("The Land Before Timeland.mp3"));
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
        table.pad(50);
        table.padTop(100);

        //button and text box setup
        text = returnText();


        textA = new TextArea(returnText(), Heist.skin);
        table.add(textA).grow().colspan(2);

        table.row();

        if (game.hasLockpick) {
            button1 = new TextButton("Use Lockpick", Heist.skin);
        }
        else {
            button1 = new TextButton("Taunt Guards", Heist.skin);
        }
        table.add(button1).width(200).expand();

        button2 = new TextButton("Wait", Heist.skin);
        table.add(button2).width(100).expand();

        //LOCKPICK/TAUNT
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //changing screen - regardless of game progress
                textA.setText(returnResults());
                table.clearChildren();
                table.add(textA).grow();
                table.row();
                table.add(button1).width(100).expand();
                button1.setText("Continue");

                if (button1.getText() == "Use Lockpick") {
                    if (count == 1) { //if second click
                        game.setScreen(new HallwayScreen(game));
                    }

                    //changing game variables

                    count++;
                }
                else {
                    if (count == 1) { //if second click
                        game.setScreen(new GoodEndScreen(game));
                    }

                    //changing game variables

                    count++;
                }

            }
        });

        //WAIT
        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (count == 1) { //if second click
                    game.setScreen(new BroadcastScreen(game));
                }

                //changing game variables

                //changing screen
                textA.setText(returnResults());
                table.clearChildren();
                table.add(textA).grow();
                table.row();
                table.add(button2).width(100).expand();
                button2.setText("Continue");

                count++;
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

    public String returnText() {
        String text;

        if ((game.attackedFirst)) {
            text = "You come to in a dimly-lit room, piercing blue eyes staring at you through two sets of prison bars. 'Lorena!' you gasp, fighting your migraine to throw yourself at your set of bars. \n" +
                    "\n" +
                    "'y/n, holy f--' she takes a deep shaky breath. 'I thought you were *dead*. You *and* Wesdru--' she gestures at a prone body in the cell next to you. 'I'm fine,' you reassure her evenly. 'This isn't my blood. We...may have decided to fight our way in. I know, I know.' You wave off Lorena's outraged look before she can lecture you, leaning into Wesdru's cell to slap her awake. \n" +
                    "\n" +
                    "She snaps awake with a gasp. 'Fuck! What the hell is going on?!' 'I have no idea,' Lorena whispers with a grimace. 'They only bring food while I'm asleep.'\n" +
                    "\n" +
                    "'Guys,' you interrupt, 'We don't have time to speculate, we have to decide what to do before she makes the decision for us.' \n";
        }
        else if ((!game.withWesdru) && (game.enteredSR)) {
            text = "You come to in a dimly-lit room, piercing blue eyes staring at you through two sets of prison bars. 'Lorena!' you gasp, pushing through the piercing pain in your leg to turn and face her.\n" +
                    "\n" +
                    "'y/n, holy f--' she takes a deep shaky breath. 'I thought you were *dead*. What the fuck were you thinking, coming here alone! you-you--' You cut off her rambling sharply. 'There was a change of plans. Wesdru stayed behind. Can you fight?' 'Can *you*?' she replies icily. More rage to funnel, you think. 'I'll be fine.' ";
        }
        else if ((game.withWesdru) && (game.enteredSR)) {
            text = "You come to in a dimly-lit room, piercing blue eyes staring at you through two sets of prison bars. 'Lorena!' you gasp, fighting your migraine to throw yourself at your set of bars. 'Don't push yourself, you've been out a couple hours,' she says with a light smile. She's bleeding sluggishly from a cut to her temple and obviously bruised, but not majorly hurt, you inventory quickly. Lorena laughs knowingly. 'I'm fine. You should be worried about Wesdru.' Following her gaze, you notice a prone body in the cell next to yours. You can just barely reach her hand, which you squeeze as hard as you can, until Wesdru snaps awake with a gasp. 'Fuck! What the hell is going on?!' 'I have no idea,' Lorena whispers with a grimace. 'They only bring food while I'm asleep.'\n" +
                    "\n" +
                    "'Guys,' you interrupt, 'We don't have time to speculate, we have to decide what to do before she makes the decision for us.' ";
        }
        else if (!game.withWesdru) {
            text = "TBW";
        }
        else {
            text = "TBW";
        }

        return text;
    }

    public String returnResults() {
        String results = new String("TBW");
        return results;
    }
}
