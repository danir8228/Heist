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

        button1 = new TextButton("Use Lockpick", Heist.skin);
        if (game.hasLockpick) {
            table.add(button1).width(200).expand();
        }

        button2 = new TextButton("Wait", Heist.skin);
        table.add(button2).width(100).expand();

        //LOCKPICK
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (count == 1) { //if second click
                    if (game.attackedFirst) {
                        game.setScreen(new HallwayScreen(game));
                    }
                    else {
                        game.END = 11;
                        game.setScreen(new EndScreen(game));
                    }
                }

                //change game variables
                game.usedLockpick = true;

                textA.setText(returnResults());
                table.clearChildren();
                table.add(textA).grow();
                table.row();
                table.add(button1).width(100).expand();
                button1.setText("Continue");

                count++;
            }
        });

        //WAIT
        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (count == 1) { //if second click
                    game.setScreen(new BroadcastScreen(game));
                }

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
                    "'" + game.name + ", holy f--' she takes a deep shaky breath. 'I thought you were *dead*. You *and* Wesdru--' she gestures at a prone body in the cell next to you. 'I'm fine,' you reassure her evenly. 'This isn't my blood. We...may have decided to fight our way in. I know, I know.' You wave off Lorena's outraged look before she can lecture you, leaning into Wesdru's cell to slap her awake. \n" +
                    "\n" +
                    "She snaps awake with a gasp. 'Fuck! What the hell is going on?!' 'I have no idea,' Lorena whispers with a grimace. 'They only bring food while I'm asleep.'\n" +
                    "\n" +
                    "'Guys,' you interrupt, 'We don't have time to speculate, we have to decide what to do before she makes the decision for us.' \n";
        }
        else if ((!game.withWesdru) && (game.enteredSR)) { //alone and evil
            text = "You come to in a dimly-lit room, piercing blue eyes staring at you through two sets of prison bars. 'Lorena!' you gasp, pushing through the piercing pain in your leg to turn and face her.\n" +
                    "\n" +
                    "'" + game.name + ", holy f--' she takes a deep shaky breath. 'I thought you were *dead*. What the fuck were you thinking, coming here alone! you-you--' You cut off her rambling sharply. 'There was a change of plans. Wesdru stayed behind. Can you fight?' 'Can *you*?' she replies icily. More rage to funnel, you think. 'I'll be fine.' ";
        }
        else { //romantic
            text = "You come to in a dimly-lit room, piercing blue eyes staring at you through two sets of prison bars. 'Lorena!' you gasp, fighting your migraine to throw yourself at your set of bars. 'Don't push yourself, you've been out a couple hours,' she says with a light smile. She's bleeding sluggishly from a cut to her temple and obviously bruised, but not majorly hurt, you inventory quickly. Lorena laughs knowingly. 'I'm fine. You should be worried about Wesdru.' Following her gaze, you notice a prone body in the cell next to yours. You can just barely reach her hand, which you squeeze as hard as you can, until Wesdru snaps awake with a gasp. 'Fuck! What the hell is going on?!' 'I have no idea,' Lorena whispers with a grimace. 'They only bring food while I'm asleep.'\n" +
                    "\n" +
                    "'Guys,' you interrupt, 'We don't have time to speculate, we have to decide what to do before she makes the decision for us.' ";
        }

        return text;
    }

    public String returnResults() {
        String results;

        //USE LOCKPICK
        if ((game.usedLockpick) && (game.attackedFirst)) {
            results = "You remember the lockpick you grabbed at the market, grateful for your forward thinking. Trying to play it off casually in case anyone is watching, you quickly dispatch the heavy iron lock on your door, wincing when the door swings open with a loud creaking sound. You don't waste time, opening Wesdru and Lorena's doors and grabbing your confiscated weapons before you hear guards thundering down the staircase. Lorena immediately empties her gun into the first guard, pistol whipping another one with a sickening crunch. You lose track of her in the chaos, but soon, silence reigns. \n" +
                    "\n" +
                    "Peering around the corner, you see two guards remain in place outside the only other door in the hall. 'It's probably not an exit; we should steer clear,' Wesdru says. 'I think I'll be able to recognize the door we came in through, and this building is relatively small. We'll just have to fight our way through,' you add decisively. 'Oh dear *Lord*,' sighs Lorena, 'remind me why I love my bloodthirsty wife. Just follow my lead.' ";
        }
        else if ((game.usedLockpick) && (!game.withWesdru)) { //alone and evil
            results = "Without further ado, you pull out your lockpicks, quickly dispatching your heavy iron lock before moving to Lorena's. She stares at you agressively all the while, obviously not satisfied with your non-conversation but recognizing there are bigger problems at hand. You share a tight hug before settling into your well-trodden roles. 'There are cameras; she'll know we're out.' 'I shut them off,' you reply, grabbing your confiscated weapons. Lorena nods, leading the way up the stairs. 'Exits?' 'I know of one that might still be unguarded, if we're lucky.' \n" +
                    "\n" +
                    "Thankfully, the guards in the corridor have since dispersed, leaving only bloodstains to mark your vicious fight, which Lorena doesn't acknowledge. You lead the way down the hall briskly, allowing her to pull you into halls to avoid further patrols. During one of these asides, while you both wait for the danger to pass, Lorena speaks. 'I overheard my jailers talking about Fregola, while they thought I was asleep.' 'and?' you whisper back apathetically. 'He's being held by the entrance, possibly tortured. We need to save him, if only to stop him from causing us even more trouble.' *More?* And then it hits you. 'Save a *traitor*? We're barely gonna make it out ourselves!' Lorena completely ignores your response. 'Just lead me to the front, I'll take care of it.' \n";
        }
        else if ((game.usedLockpick)) { //romantic
            results = "The dull clang reminds you of the lockpicks you grabbed at the market. 'Pull on this loop,' you gesture to Lorena, one finger twitching toward the target. It takes her a second in the darkness, but soon there's enough slack for you to slip out of the shodily-tied restraints. You quickly free everyone before efficiently manuvering the lock open.\n" +
                    "\n" +
                    "'We don't have much time, they could come back at any second,' Wesdru murmurs as you carefully re-arm yourselves in complete silence. 'We're a big group,' you reassure, 'we can take them.' 'Does that mean I get a gun?' whispers Fregola, hopefully. None of you deign to respond, taking the stairs in single file.";
        }
        //WAIT
        else if (game.attackedFirst) {
            results = "\n" +
                    "'Dras obviously has some kind of plan for me, and now probably you two too; she'll do everything in her power to stop us from escaping, up to and including killing us,' Lorena deliberates. Wesdru nods. 'If we wait for her to make the first move, we can respond accordingly.' You open your mouth to say more, but stop when you notice Lorena widen her eyes significantly and subtly rub her ear. The signal is clear; someone could be listening. \n" +
                    "\n" +
                    "All three of you pretend to be asleep or unconscious, in case there is no surveillence in the room. After about a half-hour, a long-haired official stomps down the steps, banging on the cell bars obnoxiously, three guards in their wake. 'Wake the fuck up! You're needed in the broadcast room.' The guards unceremoniously tie your hands behind your backs and drag all of you out of your cells, past all your weapons on a counter in the corner of the room. You shift and feel the knife secured to the small of your back is still there. Lorena stumbles on the top step of the staircase into the hall, provoking a burly guard to rougly prod at her with his gun. You manage to stop yourself from retaliating at the last second.\n" +
                    "\n" +
                    "The lead guard pulls out some kind of ID card which, when placed against a gray metallic bump on the wall, unlocks the door with a dull clunk. They push the door open to reveal a tall, muscular woman, her red hair in two tight plaits, surrounded by cameras and equipment, obviously waiting for them. It can only be Dras. 'Well, come on in! The show's about to begin.'";
        }
        else if (!game.withWesdru) { //alone and evil
            results = "'Look, I don't think you could walk right now, much less help me fight our way out of here. Please, let's just wait for whatever Dras has planned. She wants us alive for a reason.' Lorena pleads with you empathetically, but you know she's gearing up for a fight, and you just don't have the energy. 'Fine,' you bite out, noting the faint shock in her face, 'but only because I think it'll be easier to get out if we wait for that motherfucker to fuck up.' \n" +
                    "\n" +
                    "You lapse into silence, ripping the bottom half of your shirt to serve as a makeshift bandage which you hold against the bullet hole in your leg, trying not to breathe heavily and worry Lorena. After a few minutes a patrol stomps down the stairs, unlocking the doors with a dull clank and dragging you and Lorena back up to Dras's technology room.\n" +
                    "\n" +
                    "The lead guard pulls out some kind of ID card which, when placed against a gray metallic bump on the wall, unlocks the door with a dull clunk. They push the door open to reveal a tall, muscular woman, her red hair in two tight plaits, surrounded by cameras and equipment, obviously waiting for them. It can only be Dras. 'Well, come on in! The show's about to begin.'";
        }
        else { //romantic
            results = "Fregola's panicked whimpers fill the room. 'They're going to kill us, they're going to kill us, they're--' 'SHUT UP!' you bellow, patience thinned. You open your mouth to say more, but you realize all you can think of are reassurances that aren't true. 'Let's play into her plan,' Lorena whispers. 'She's obviously got something in mind for me, and now maybe even you, but if I remember correctly, she's never been the best strategist. There will be gaps where we can fight our way out if we need to. None of us are hurt.' \n" +
                    "\n" +
                    "You turn to Wesdru, considering Lorena's words. 'She's got a point. Plus, she probably expects us to fight now and filled the hall with reinforcements. If we wait until she's not expecting it, we can get out before she can react.'\n" +
                    "\n" +
                    "Lorena and Wesdru look at you, waiting for your input. 'I trust you two. And I'd love to get the chance to confront the person who kidnapped my wife.' Your voice hardens at the end in resolve. \n" +
                    "\n" +
                    "In that moment, you hear thundering footsteps down the stairs. 'Don't struggle,' a guard yells while unlocking the cell, 'we have orders to keep you alive, not whole.' None of you make a sound--including Fregola, who seems to have decided complaining won't help--allowing the patrol to walk you to the door you noted earlier. \n" +
                    "\n" +
                    "The lead guard pulls out some kind of ID card which, when placed against a gray metallic bump on the wall, unlocks the door with a dull clunk. They push the door open to reveal a tall, muscular woman, her red hair in two tight plaits, surrounded by cameras and equipment, obviously waiting for them. It can only be Dras. 'Well, come on in! The show's about to begin.'";
        }

        return results;
    }
}
