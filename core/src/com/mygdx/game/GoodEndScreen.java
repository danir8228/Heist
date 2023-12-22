package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GoodEndScreen implements Screen {

    final Heist game;

    private final Music backgroundMusic;

    private String text;

    private Stage stage;
    private Table table;
    private TextButton button1;
    private TextButton button2;

    public GoodEndScreen (final Heist game) {
        this.game = game;

        //load sound effects and music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Paper Mache.mp3"));
        backgroundMusic.setLooping(true);
    }

    @Override
    public void show() {
        backgroundMusic.play();

        //text
        text = returnText();

        //stage setup
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //table setup
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.pad(30);
        table.padTop(75);

        //button
        button1 = new TextButton("Play again", Heist.skin);
        table.add(button1).width(150).expand().bottom();

        button2 = new TextButton("Quit Game", Heist.skin);
        table.add(button2).width(150).expand().bottom();

        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //reset game variables
                game.hasGrenade = false;
                game.hasLockpick = false;
                game.attackedFirst = false;
                game.withWesdru = false;
                game.enteredSR = false; //security room; false means entered MR (mystery room)
                game.inHallSecondTime = false;
                game.wasCaptured = false;
                game.badEscaped = false;
                game.usedGrenade = false;

                game.setScreen(new MarketScreen(game));
            }
        });

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0.2f, 0, 1);

        game.batch.begin();
        game.font.draw(game.batch, text, 20, 400);
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
        backgroundMusic.dispose();
        stage.dispose();
    }

    public String returnText() {
        String text;

        if (game.badEscaped) {
            text = "You don't stop to think; you don't have the time. Or at least that's what you tell yourself. All your focus narrows to the door, \n" +
                    "and the eternity it takes for you to arrive and push it open heavily. It then narrows to sneaking your way to you and Wesdru's habitual \n" +
                    "emergency meeting place, in case you get separated. \n" +
                    "\n" +
                    "She arrives within a half-hour, eyes widening at your weakened, bloody state, and surprisingly, covered in blood herself. \n" +
                    "'Thank god you're alive,' she gasps, holding most of your body weight now, 'I picked a fight to thin her forces inside. \n" +
                    "The only thing that could make this worse is if you hadn't made it out.' The wording makes you fight for consciousness. \n" +
                    "\n" +
                    "'Wait...wha-du-mean,' you slur. Wesdru's eyes fill with tears. 'Lorena's dead. Dras executed her on a live broadcast, for the whole town to see. \n" +
                    "As a warning not to interfere with her rise to power. She wants to be a tyrant,' Wesdru continues, seemingly unaware she's still speaking, 'a dictator. \n" +
                    "And with that stunt? No one will stand against her. She distracted us with the allure of saving--of finding someone we love, and her plan worked perfectly. \n" +
                    "and now...' Wesdru trails off, seemingly overwhelmed by the hardships ahead, but you've stopped processing anything beyond...the image of your wife in \n" +
                    "that jail cell, now the last time you'll ever see her. You simply cannot contain that thought, the agony mixing with the sharp pain of your whole body, \n" +
                    "pulling you under at last. You're not dead. But in that moment, you wish you were. \n";
        }
        else if (game.usedGrenade) {
            text = "Suddenly, you remember the grenade you grabbed at the market, hours earlier. It's heavy and cold in your hand, demanding real consideration. \n" +
                    "Fregola yelps when he sees it, obviously terrified but determined not to interact with you if he can help it. The noise draws Wesdru's attention. \n" +
                    "'y/n...you cannot be considering blowing up that room!' She grins widely at you. 'Who am I kidding, with your impulsive streak? Of course you are. \n" +
                    "God help us.' You smirk back, knowing that she's delighted, and that, presented with the opportunity, she would do the same thing. \n" +
                    "\n" +
                    "Without further ado, you manage to time it perfectly, throwing it into the room as it's held open for a particularly large piece of equipment. \n" +
                    "You hear the commotion, yells and enraged commands that could only be from Dras, before the blast silences all. Boots stomp over to examine the wreckage, \n" +
                    "leaving the basement unguarded. You signal Wesdru to stay with Fregola and sneak down the stairs. \n" +
                    "\n" +
                    "Lorena, already free, whips around at the sound of your descent into the room, gun pointed at your head. She sags when she recognizes you, \n" +
                    "throwing herself into your arms. 'I love you so fucking much,' she gasps wetly, your arms around her gentle in light of her bruises. \n" +
                    "You shift so you can look in her eyes, her head in your hands, your thumbs wiping dirt softly from her cheeks. 'Missed you,' you whisper. \n" +
                    "\n" +
                    "It's trivial from there, to join the others and reach the now unguarded exit. ";
        }
        else {
            text = "END OF GAME!";
        }


        return text;
    }
}
