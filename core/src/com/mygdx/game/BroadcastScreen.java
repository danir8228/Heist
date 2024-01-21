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
                if (game.wasCaptured || game.attackedFirst) { //FREE YOUR HANDS
                    game.END = 8;
                    game.setScreen(new EndScreen(game));
                }
                else { //KILL HER
                    game.END = 1;
                    game.setScreen(new EndScreen(game));
                }
            }
        });

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.wasCaptured || game.attackedFirst) { //SPIT IN HER FACE
                    game.END = 5;
                    game.setScreen(new EndScreen(game));
                }
                else { //SPARE HER
                    game.END = 4;
                    game.setScreen(new EndScreen(game));
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
            text[0] = "The room is small and lifeless, one side taken up entirely by bright floor lights and complex equipment. Dras gestures the guards to tie each of you to the wall on metal loops that seem to have been installed there for this express purpose. They seem to relish doing so as roughly as possible, twisting your arms unnaturally and making Wesdru's legs give out with a cry as her knees slam into the cold concrete. Staying quiet, you test the bonds on your hands subtly; the only way to free them would involve dislocating a wrist, potentially both. \n" +
                    "\n" +
                    "You refocus on Dras, who is giving harried orders and repositioning recording equipment. Right as it begins to sink in that her focus isn't on you or your partners, She walks toward you, turning around to face the mass of wires, screens, and lenses. \n" +
                    "\n" +
                    "'So sorry to interrupt,' she projects to a camera, 'but you're gonna want to see this.' A cruel grin grows on her face as she turns back to face you, grabbing Lorena's hair at the nape of her neck to bare her throat. 'Get your filthy hands off her!' you snarl, throwing yourself at Dras futilely, heart pounding. 'Behold, The two Sanchezes... and...their pet.' At this, she backhands Wesdru viciously, the cameras panning to capture the action. 'They need no introduction, of course. Bounty hunters of the highest class, striking fear into the hearts of criminals and lay people alike. Keeping people honest, they say. Enacting *justice*.' Her distaste for the word is evident, her ridicule of the people who say it earnestly written all over her face. 'Well, I think you all need a reminder of who runs this town. Who makes the *just* decisions.' The rage in her tone suddenly surfaces, stony countenance looming over you as she unholsters a pistol and aims it at your your temple. \n" +
                    "\n" +
                    "'NO,' Lorena screams, eyes wide and glinting in the light. You don't react, aware of the hundreds of people watching. You won't give her the satisfaction. \n" +
                    "\n" +
                    "'This is my town. And no one, much less a couple of lowllife cowboys, will change that.'";
            text[1] = "Free your hands";
            text[2] = "Spit in her face";
        }
        else if ((game.wasCaptured) && (!game.withWesdru) && (game.enteredSR)) {
            text[0] = "The room is small and lifeless, one side taken up entirely by bright floor lights and complex equipment. Dras gestures the guards to tie each of you to the wall on metal loops that seem to have been installed there for this express purpose. They seem to relish doing so as roughly as possible, twisting your arms unnaturally and making Lorena's legs give out with a cry as her knees slam into the cold concrete. Staying quiet, you test the bonds on your hands subtly; the only way to free them would involve dislocating a wrist, potentially both. \n" +
                    "\n" +
                    "You refocus on Dras, who is giving harried orders and repositioning recording equipment. Right as it begins to sink in that her focus isn't on you or your partners, She walks toward you, turning around to face the mass of wires, screens, and lenses. \n" +
                    "\n" +
                    "'So sorry to interrupt,' she projects to a camera, 'but you're gonna want to see this.' A cruel grin grows on her face as she turns back to face you, grabbing Lorena's hair at the nape of her neck to bare her throat. 'Get your filthy hands off her!' you snarl, throwing yourself at Dras futilely, heart pounding. 'Behold, The two Sanchezes...' At this, she backhands you viciously, the cameras panning to capture the action, Lorena's turn to scream. 'They need no introduction, of course. Bounty hunters of the highest class, striking fear into the hearts of criminals and lay people alike. Keeping people honest, they say. Enacting *justice*.' Her distaste for the word is evident, her ridicule of the people who say it earnestly written all over her face. 'Well, I think you all need a reminder of who runs this town. Who makes the *just* decisions.' The rage in her tone suddenly surfaces, stony countenance looming over you as she unholsters a pistol and aims it at your your temple. \n" +
                    "\n" +
                    "'NO,' Lorena screams, eyes wide and glinting in the light. You don't react, aware of the hundreds of people watching. You won't give her the satisfaction. \n" +
                    "\n" +
                    "'This is my town. And no one, much less a couple of lowllife cowboys, will change that.'\n";
            text[1] = "Free your hands";
            text[2] = "Spit in her face";
        }
        else if ((game.wasCaptured) && (!game.withWesdru)) {
            text[0] = "You come to in a brightly-lit room, hands tied behind your back uncomfortably, facing a horde of complex recording equipment. Commanding the room is a tall, muscular woman, her red hair in two tight plaits, armed to the teeth. It can only be Dras. You blink the last of the fogginess out of your eyes and turn to face--'Lorena!' you cry, registering the extensive bruising on her face and neck even as she shakes her head at you in a silent plea. 'Awake already? Good thing we've started rolling,' Dras whispers ominously. \n" +
                    "\n" +
                    "'So sorry to interrupt,' she projects to a camera, 'but you're gonna want to see this.' A cruel grin grows on her face as she turns back to face you, grabbing Lorena's hair at the nape of her neck to bare her throat. 'Get your filthy hands off her!' you snarl, throwing yourself at Dras futilely, heart pounding. 'Behold, The two Sanchezes...' At this, she backhands you viciously, the cameras panning to capture the action, Lorena's turn to scream. 'They need no introduction, of course. Bounty hunters of the highest class, striking fear into the hearts of criminals and lay people alike. Keeping people honest, they say. Enacting *justice*.' Her distaste for the word is evident, her ridicule of the people who say it earnestly written all over her face. 'Well, I think you all need a reminder of who runs this town. Who makes the *just* decisions.' The rage in her tone suddenly surfaces, stony countenance looming over you as she unholsters a pistol and aims it at your your temple. \n" +
                    "\n" +
                    "'NO,' Lorena screams, eyes wide and glinting in the light. You don't react, aware of the hundreds of people watching. You won't give her the satisfaction. \n" +
                    "\n" +
                    "'This is my town. And no one, much less a couple of lowllife cowboys, will change that.'\n";
            text[1] = "Free your hands";
            text[2] = "Spit in her face";
        }
        else if ((game.wasCaptured)) {
            text[0] = "The room is small and lifeless, one side taken up entirely by bright floor lights and complex equipment. Dras gestures the guards to tie each of you to the wall on metal loops that seem to have been installed there for this express purpose. 'Not him,' She says, pointing to Fregola. 'Get rid of him, will you? I'm done with that rat.' Two guards pick Fregola up bodily and drag him out of the room, ignoring his increasingly desperate screams of protest, whose end is punctuated by a loud gunshot just outside the room, which Dras ignores. Staying quiet, you test the bonds on your hands subtly; the only way to free them would involve dislocating a wrist, potentially both. \n" +
                    "\n" +
                    "You refocus on Dras, who is giving harried orders and repositioning recording equipment. Right as it begins to sink in that her focus isn't on you or your partners, She walks toward you, turning around to face the mass of wires, screens, and lenses. \n" +
                    "\n" +
                    "'So sorry to interrupt,' she projects to a camera, 'but you're gonna want to see this.' A cruel grin grows on her face as she turns back to face you, grabbing Lorena's hair at the nape of her neck to bare her throat. 'Get your filthy hands off her!' you snarl, throwing yourself at Dras futilely, heart pounding. 'Behold, The two Sanchezes... and...their pet.' At this, she backhands Wesdru viciously, the cameras panning to capture the action. 'They need no introduction, of course. Bounty hunters of the highest class, striking fear into the hearts of criminals and lay people alike. Keeping people honest, they say. Enacting *justice*.' Her distaste for the word is evident, her ridicule of the people who say it earnestly written all over her face. 'Well, I think you all need a reminder of who runs this town. Who makes the *just* decisions.' The rage in her tone suddenly surfaces, stony countenance looming over you as she unholsters a pistol and aims it at your your temple. \n" +
                    "\n" +
                    "'NO,' Lorena screams, eyes wide and glinting in the light. You don't react, aware of the hundreds of people watching. You won't give her the satisfaction. \n" +
                    "\n" +
                    "'This is my town. And no one, much less a couple of lowllife cowboys, will change that.'\n";
            text[1] = "Free your hands";
            text[2] = "Spit in her face";
        }
        //BROADCAST - IF YOU AMBUSH ROOM (NOT CAPTURED)
        else if (!game.withWesdru) {
            text[0] = "'FUCK, FUCK--' Dras crashes to the ground, cursing all the while, although she's conscious enough to signal the guards who've pointed their rifles at you and Fregola--who's already dropped his gun in fear-- not to shoot. She still wants you alive for some reason, which gives you an excellent bargaining position. You still haven't lowered your pistol, although you know there's no chance you make it out alive if you shoot. You make eye contact with Lorena, squaring your jaw to reassure her. \n" +
                    "\n" +
                    "'Sorry folks,' Dras is saying through gritted teeth, 'we did expect some guests on the show, just not quite as *unimpeded*. At this, she throws a murderous look at the guards, who shift uneasily. Her comment prompts you to look around the room and notice the mass of wires and cameras on the far side, the blinking red lights that indicate they're currently transmitting. Fregola's words come back-- 'make an example'-- and the pieces click: she's planning an execution, live to the whole town, for political gain. \n" +
                    "\n" +
                    "Dras turns to you sharply. 'Give me your gun, or your wife will be the first to die. Painfully.' \n";
            text[1] = "Kill her";
            text[2] = "Spare her";
        }
        else {
            text[0] = "Dras's guards quickly tie you to metal rings that seem to have been installed in the wall for this exact purpose, you right next to Lorena. Your relief must be palpable at the sight of her alive, your emotions mirrored on her face. She's covered in dark bruises and miscellaneous wounds, wincing even as she shifts closer to you. 'My love,' she intones, voice wavering, 'Everything's going to be fine--I'm fine, don't look at me like that--' \n" +
                    "\n" +
                    "'The two Sanchezes, at my feet at last,' Dras projects into a camera, interrupting Lorena's assurances and completely ignoring Wesdru and Fregola. 'They need no introduction, of course. Bounty hunters of the highest class, striking fear into the hearts of criminals and lay people alike. Keeping people honest, they say. Enacting *justice*.' Her distaste for the word is evident, her ridicule of the people who say it earnestly written all over her face. 'Well, I think you all need a reminder of who runs this town. Who makes the *just* decisions.' The rage in her tone suddenly surfaces, stony countenance looming over you as she unholsters a pistol and aims it at your your temple. \n" +
                    "\n" +
                    "'NO,' Lorena screams, eyes wide and glinting in the light. You don't react, aware of the hundreds of people watching. You won't give her the satisfaction. \n" +
                    "\n" +
                    "'This is my town. And no one, much less a couple of lowllife cowboys, will change that.'\n";
            text[1] = "Kill her";
            text[2] = "Spare her";
        }

        return text;
    }
}
