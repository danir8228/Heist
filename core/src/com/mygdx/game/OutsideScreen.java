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


public class OutsideScreen implements Screen {

    final Heist game;

    private Texture backgroundImage;
    private final Music backgroundMusic;

    private Stage stage;
    private TextArea text;
    private int count;
    private Table table;
    private TextButton button1;
    private TextButton button2;
    private TextButton button3;


    public OutsideScreen(final Heist game) {
        this.game = game;

        //load images (64x64 pixels)
        backgroundImage = new Texture("2 standing.png");

        //load sound effects and music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("theBitterBoogie.mp3"));
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
                //int count = 0;
                if (count == 1) { //if second click
                    game.setScreen(new CapturedScreen(game));
                }

                //changing game variable
                game.attackedFirst = true;

                //changing screen
                backgroundImage = new Texture("2a gunFight.jpg");
                text.setText("Wesdru nods grimly. \n\n Keeping to the shadows as much as possible, you approach the guards " +
                        "from an angle, drawing your swords silently. The idiots are standing in a semicircle facing out " +
                        "from the door, meaning only one, an especially fresh-faced recruit, is in a position to widen his " +
                        "eyes comically at you and Wesdru's now-obvious attack. Before he can make a noise you and Wesdru " +
                        "spring into action; a dagger from the small of your back flies from your hand, pinning him to the wall, " +
                        "while a bullet from one of her pistols lodges itself in his temple. But the damage has been done. " +
                        "You sidestep the first's clumsy gun draw and press your shortsword into their throat as Wesdru points " +
                        "her still-warm gun at the second, but not before one of them clearly makes some sort of signal into-- " +
                        "'You said there would be no cameras!' you fume, glaring at Wesdru. 'There weren't any last week! " +
                        "Something must have changed...' \n\n Before you can respond, a stream of reinforcements surround you and Wesdru. " +
                        "Slitting your hostage's throat, you manage to push the body into the guards closest to you and spin around " +
                        "to stab a redheaded woman in the stomach before the world goes black.\n");
                table.clearChildren();
                table.add(text).grow();
                table.row();
                table.add(button1).width(100).expand();
                button1.setText("Continue");

                count++;
            }
        });

        //SWEET TALK THEM
        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (count == 1) { //if second click
                    game.setScreen(new HallwayScreen(game));
                }

                //game.withWesdru already false

                //changing screen
                backgroundImage = new Texture("2b sweetTalk.jpeg");
                text.setText("You start walking directly into the guards' line of vision before Wesdru can object. " +
                        "'Hello, boys,' you call out calmly, tipping your hat gracefully as you come to a stop in front of them. " +
                        "Wesdru is nowhere to be seen, probably unwilling to risk herself on a lark, you think fondly. " +
                        "The man on the left leans his hands on his shoulder holsters menacingly. '[Your name]. Can't say " +
                        "I'm surprised.' You supress your shock at the name (yours!), schooling your features into an impatient " +
                        "expression. 'Why would you be? I'm here to see Dras.' 'On what business?' the guard responds. " +
                        "'None of yours,' you reply coldly. 'Now step aside before I put a bullet in you for my troubles.' " +
                        "The guards share a look of slight amusement before slowly stepping away from the door, allowing you " +
                        "to push it open without a backwards glance. \n\n\nYou have a feeling they wanted you to get inside...");
                table.clearChildren();
                table.add(text).grow();
                table.row();
                table.add(button2).width(100).expand();
                button2.setText("Continue");

                count++;
            }
        });

        //CLIMB THE BUILDING
        button3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (count == 1) { //if second click
                    game.setScreen(new HallwayScreen(game));
                }

                //changing game variable
                game.withWesdru = true;

                //changing screen
                backgroundImage = new Texture("2c climb.jpeg");
                text.setText("You stand back as Wesdru deftly scales the side of the neighboring building, using holds along the front doors and lights, before reaching blindly onto the roof for a ledge. 'There's a lip if you reach in about a foot,' she yells down to you, before pushing up and out of sight. You attempt to follow the same route, modifying as you go for your lesser height and skill, and finally accepting Wesdru's hand for a boost. Panting slightly, you look over the side of the building, noting the distracted guards and narrow gap. You lock eyes with Wesdru before taking a running leap onto the roof of Dras's warehouse. \n" +
                        "\n\n" +
                        "You land with a sickening thud, wincing when Wesdru's only adds to the possible alert. Thankfully, a glance down confirms the guards brushed the noise off, probably as shipment delivery or... violence of some kind. That didn't bear thinking about. \n" +
                        "\n\n" +
                        "A tap on your back breaks the reverie. You turn to see Wesdru point silently at an airvent, smirking arrogantly. You roll your eyes, hearing her unspoken comment clearly: 'Told you, brat.' Unscrewing the plate with a small dagger, you and Wesdru drop without a backwards glance. ");
                table.clearChildren();
                table.add(text).grow();
                table.row();
                table.add(button3).width(100).expand();
                button3.setText("Continue");

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
}
