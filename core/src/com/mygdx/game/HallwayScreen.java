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

public class HallwayScreen implements Screen {

    final Heist game;

    private Texture backgroundImage;
    private Music backgroundMusic;

    private int count;

    private TextButton button1;
    private TextButton button2;
    private TextButton button3;
    private TextArea textA;

    private Stage stage;
    private Table table;

    private String[] text;

    public HallwayScreen(final Heist game) {
        this.game = game;

        //load images (64x64 pixels)
        backgroundImage = new Texture("3b.png");

        //load sound effects and music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Horology.mp3"));
        backgroundMusic.setLooping(true);

    }

    @Override
    public void show() {
        if (game.inHallSecondTime) {
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Doom City.mp3"));
        }
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

        //text setup
        text = returnText(); //returns array with all text for dialogue and buttons

        textA = new TextArea(text[0], Heist.skin);
        table.add(textA).grow().colspan(3);

        table.row();

        //buttons
        button1 = new TextButton(text[1], Heist.skin);
        table.add(button1).width(150).expand();

        button2 = new TextButton(text[2], Heist.skin);
        table.add(button2).width(150).expand();

        //only true if Hallway 2 (Second time in hallway) but not always
        if (text[3] != null) {
            button3 = new TextButton(text[3], Heist.skin);
            table.add(button3).width(150).expand();

            button3.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (text[3].equals("Escape")) {
                        //game.setScreen(new BadEndScreen(game));
                        game.badEscaped = true;
                        game.setScreen(new GoodEndScreen(game));
                    }
                    else { //grenade
                        game.usedGrenade = true;
                        game.setScreen(new GoodEndScreen(game));
                    }
                }
            });
        }

        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.wasCaptured) { //ESCAPE
                    game.setScreen(new GoodEndScreen(game)); //only option for now
                }
                else if (game.inHallSecondTime) { //RESCUE LORENA
                    if (count == 1) { //if second click
                        game.setScreen(new CapturedScreen(game));
                    }
                    //changing game variables
                    game.wasCaptured = true;

                    //changing screen
                    textA.setText(returnResults());
                    table.clearChildren();
                    table.add(textA).grow();
                    table.row();
                    table.add(button1).width(100).expand();
                    button1.setText("Continue");

                    count++;
                }
                else { //SECURITY ROOM
                    if (count == 1) { //if second click
                        game.setScreen(new HallwayScreen(game));
                    }
                    //changing game variables
                    game.enteredSR = true;

                    //changing screen
                    backgroundImage = new Texture("security room.png");
                    textA.setText(returnResults());
                    table.clearChildren();
                    table.add(textA).grow();
                    table.row();
                    table.add(button1).width(100).expand();
                    button1.setText("Continue");

                    count++;
                }
            }
        });

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.inHallSecondTime) { //CONFRONT DRAS
                    if (count == 1) { //if second click
                        game.setScreen(new BroadcastScreen(game));
                    }

                    //game.wasCaptured already false

                    //changing screen
                    textA.setText(returnResults());
                    table.clearChildren();
                    table.add(textA).grow();
                    table.row();
                    table.add(button2).width(100).expand();
                    button2.setText("Continue");

                    count++;
                }
                else { //MYSTERY ROOM
                    if (count == 1) { //if second click
                        game.setScreen(new HallwayScreen(game));
                    }

                    //game.enteredSR already false

                    //changing screen
                    backgroundImage = new Texture("mystery room.jpeg");
                    textA.setText(returnResults());
                    table.clearChildren();
                    table.add(textA).grow();
                    table.row();
                    table.add(button2).width(100).expand();
                    button2.setText("Continue");

                    count++;
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
        //0:textArea 1:button1 2:button2 3:button3
        String[] text = new String[4];

        if (game.attackedFirst) {
            text[0] = "TBW";
        }
        //HALLWAY 3 - THIRD TIME - AFTER CAPTURED
        else if ((game.wasCaptured) && (!game.withWesdru) && (game.enteredSR)) {
            text[0] = "Thankfully, the guards in the corridor have since dispersed, leaving only bloodstains to mark your vicious fight, which Lorena doesn't acknowledge. You lead the way down the hall briskly, allowing her to pull you into halls to avoid further patrols. During one of these asides, while you both wait for the danger to pass, Lorena speaks. 'I overheard my jailers talking about Fregola, while they thought I was asleep.' 'and?' you whisper back apathetically. 'He's being held by the entrance, possibly tortured. We need to save him, if only to stop him from causing us even more trouble.' *More?* And then it hits you. 'Save a *traitor*? We're barely gonna make it out ourselves!' Lorena completely ignores your response. 'Just lead me to the front, I'll take care of it.' ";
        }
        else if ((game.wasCaptured) && (game.withWesdru) && (game.enteredSR)) {
            text[0] = "TBW";
        }
        else if ((game.wasCaptured) && (!game.withWesdru)) {
            text[0] = "TBW";
        }
        else if ((game.wasCaptured)) {
            text[0] = "TBW";
        }
        //HALLWAY 2 - SECURITY ROOM
        else if ((game.inHallSecondTime) && (!game.withWesdru) && (game.enteredSR)) {
            text[0] = "You limp back into the hall, leaving bloodstains where you leaned on the doorframe. " +
                    "The adrenaline is keeping you sharp, but you know it won't last long. " +
                    "Adjusting your looted shoulder holster, you head down the hall as quickly as you can, biting your " +
                    "lip to distract from the pain and fear. You dash into a corridor to avoid incoming footsteps, " +
                    "and peak around the corner once you hear them fade in the other direction--*an unguarded exit*. " +
                    "The guards must have been called as reinforcements. You know you won't get this opportunity again, " +
                    "but your mind rages against even the consideration. \n";
            text[1] = "Rescue Lorena";
            text[2] = "Confront Dras";
            text[3] = "Escape";
        }
        else if ((game.inHallSecondTime) && (game.withWesdru) && (game.enteredSR)) {
            text[0] = "You enter the hall deliberately, feeling much more centered with the information you've gathered. You don't hear any patrols yet, but you know there's at least one between you and Lorena's cell; the cells are on the other side of the building, in the basement. About halfway between here and there, Dras is just *fucking around*-- '*Breathe*, [y/n],' Wesdru whispers, 'I'm right here.' You make eye contact and take a deep breath, equal parts sarcastic and grateful. ";
            text[1] = "Rescue Lorena";
            text[2] = "Confront Dras";
        }
        //HALLWAY 2 - MYSTERY ROOM
        else if ((game.inHallSecondTime) && (!game.withWesdru)) {
            text[0] = "Thankfully, Fregola seems to have decided aligning himself with you is in his self-interest, and cooperates, slouching passively next to you. 'Hey...can I have a knife? Just a small one, in case we get attacked? They'll kill me, I'm defenseless as it is!' The comment enrages you; the arrogance to think you'll give him a single *inch* after what he did. 'Sure! You can have a knife,' you say pleasantly, before nearly shoving one in his gut. He double-takes, jumping away with a gasp. You drew blood, but left no real damage. Drawing close, you hiss, 'Shut. your. slimy. mouth.' \n" +
                    "\n" +
                    "You lead the way through the building, stopping to look behind every door and hide from moving patrols, noting a guarded exit about halfway through. Fregola follows in silence; you know he won't try anything again. Near the end of the building you start to notice the guards all seem to be heading toward a specific corridor, some entering the most busy door with expensive-looking equipment. You can hear loud discussion and shuffling from within when the door is pushed open, along with clear commands--most likely Dras. Down the hall, a couple guards stand by a staircase leading down, to a basement presumably. With jail cells, presumably. \n" +
                    "\n" +
                    "'Oh no,' Fregola whines, '*please* let's just leave, she's going to kill us when she catches us!'\n";
            text[1] = "Rescue Lorena";
            text[2] = "Confront Dras";
            if (game.hasGrenade) {
                text[3] = "Plant Grenade";
            }
        }
        else if ((game.inHallSecondTime)) {
            text[0] = "Thankfully, Fregola seems to have decided aligning himself with you and Wesdru is in his self-interest, and cooperates. 'Hey...can I have a knife? Just a small one, in case we get attacked? They'll kill me, I'm defenseless as it is!' His clearly completely self-centered decision-making makes you hate him even more; you're glad Wesdru is here to deal with him. 'You're already a morally-depraved rat; don't be an idiot too.' You can't help but chuckle at the exchange.\n" +
                    "\n" +
                    "You lead the way through the building, stopping to look behind every door and hide from moving patrols, noting a guarded exit about halfway through. Near the end of the building you start to notice they all seem to be heading toward a specific corridor, some entering the most guarded door with expensive-looking equipment. You can hear loud discussion and shuffling from within when the door is pushed open. 'Lorena must be held in there,' Wesdru whispers. Thinking she means the occupied room, you're about to disagree--Dras is the most likely occupant--before you notice her gaze, locked on a couple of guards standing in front of a staircase leading down, to a basement presumably. \n";
            text[1] = "Rescue Lorena";
            text[2] = "Confront Dras";
            if (game.hasGrenade) {
                text[3] = "Plant Grenade";
            }
        }
        //HALLWAY 1 -
        else if (!game.withWesdru) {
            text[0] = "The hall is windowless, grey and clinical--not clean, but obviously highly regulated nonetheless. Your heart pumps in your ears as the reality of the situation sinks in. 'I'm alone,' you whisper. You shake your head viciously, berating yourself for the moment of weakness you can't spare. \n" +
                    "\n" +
                    "Between you and junction at the end of the hall, there are two black doors. You quickly walk past, them, intending to look for the holding cells sure to be deeper in the building, before you suddenly hear guards coming from the junction. You stop in your tracks, then head back toward the doors. \n" +
                    "\n" +
                    "The one on the right has a plaque with symbols you think you recognize from an info recon job as 'surveillance room'. The other door, opposite it, has no plaque at all. You need to pick one of the two or be ambushed.\n" +
                    "\n" +
                    "The adrenaline pushes you to action. ";
            text[1] = "Security Camera Room";
            text[2] = "Mystery Room";
        }
        else {
            text[0] = "The hall is windowless, grey and clinical--not clean, but obviously highly regulated nonetheless. Your heart pumps in your ears as the reality of the situation sinks in.  Between you and junction at the end of the hall, there are two black doors. You quickly walk past, them, intending to look for the holding cells sure to be deeper in the building, before you suddenly hear guards coming from the junction. You stop in your tracks, turning to Wesdru anxiously.\n" +
                    "\n" +
                    "'I think I recognize this lettering. It's the surveillance room.' She's pointing at one of the black doors. You try pointing to the other door, opposite it, but Wesdru shakes her head helplessly. You need to pick one of the two or be ambushed.\n" +
                    "\n" +
                    "The adrenaline pushes you to action. \n";
            text[1] = "Security Camera Room";
            text[2] = "Mystery Room";
        }

        return text;
    }

    public String returnResults() {
        String results = "ERROR";

        //RESCUE LORENA
        if ((game.inHallSecondTime) && (game.wasCaptured) && (!game.withWesdru) && (game.enteredSR)) {
            results = "You pull that rage in close, letting it fuel you. There are no other options, you tell yourself. You *will* save your partner. Or die trying. \n" +
                    "\n" +
                    "You storm past the exit, making similar evasive moves to sneak your way to the corridor you saw on the camera feeds. There are five guards total, three by Dras's room and two by the staircase to the jail. Planning, somewhat wishfully, to simply sharpshoot all of them, you manage to bring two down before they're storming your spot, forcing you to combine your shots with dodges and lunges you can barely manage.  \n" +
                    "\n" +
                    "It's made less effective by their obvious efforts to be non-lethal, but they eventually subdue you; one knocks the gun out of your hand while another kicks your bullet wound, sending you crashing to the floor. They're all injured, but they manage to roughly drag you into the basement. You pass out before you cross the threshold. ";
        }
        else if ((game.inHallSecondTime) && (game.wasCaptured) && (game.withWesdru) && (game.enteredSR)) {
            results = "TBW";

        }
        else if ((game.inHallSecondTime) && (game.wasCaptured) && (!game.withWesdru)) {
            results = "TBW";
        }
        else if ((game.inHallSecondTime) && (game.wasCaptured)) {
            results = "TBW";
        }
        //SECURITY ROOM
        else if ((!game.withWesdru) && (game.enteredSR)) {
            results = "You burst through the door, immediately shooting one of the two guards at the elaborate desk at the far end of the room, who slumps to the ground. The second dodges your next round, rolling onto the floor, from which he has a perfect shot into your left leg. You crash to the ground mostly from shock onto your hands and knees with a wet gasp, letting him approach arrogantly. His teeth glint in the dim light. 'The great [y/n]...at my feet,' he smirks, leaning over you to whisper in your ear, 'begging for mercy, just like your pathetic friend before I--' In one clean movement, you unsheathe your sword and skewer him through the gut, relishing the terror that fills his eyes. 'Finish that sentence,' you hiss viciously. 'Say one...more...word about my wife.' Disgusted, you pull your sword out and struggle to your feet, watching him lose consciousness. 'That's what I fucking thought.' \n" +
                    "\n" +
                    "Turning to the desk, you realize he must have managed to shut off the displays before attacking. You sigh, waking the small interface screen set into the table by waving your hand over the holographic keyboard. The password prompt that appears only elicits a chuckle, your hand already reaching for the usb drive you bring to every mission. Within a minute of inserting it into the panel, the displays blink on again.  \n" +
                    "\n" +
                    "You immediately notice the sheer amount of people patrolling the halls, seamlessly interrupting every move you could make, even to exit the building. They seem to be concentrated in one corridor, which only has one room and a staircase to the basement. The basement feed is dim, but shows, unmistakably, a row of jail cells, one of which holds a motionless figure. '*Lorena*,' you breathe. The room in the corridor draws your eye; surrounded by camera and equipment, a tall, muscular woman, her red hair in two tight plaits, commands the room. It can only be Dras. \n" +
                    "\n" +
                    "The new information swirls in your head while you methodically disconnect all the cameras.\n";
        }
        else if ((game.withWesdru) && (game.enteredSR)) {
            results = "You and Wesdru burst through the door, shooting the two guards at the elaborate desk at the far end of the room before they can alert anyone, but not before they shut off the wall of camera feeds with a soft *click*. The bodies slump to the floor lifelessly, making room for you to examine the controls. You wake the small interface screen set into the table by waving your hand over the holographic keyboard, feeling Wesdru settle behind you, looking over your shoulder. She sighs in response to the password prompt that appears. 'Don't worry. I always carry brute-force code on me,' you reply smugly, holding a usb drive. Within a minute of inserting it into the panel, the displays blink on again. 'Guess the password was 0000 or something,' Wesdru says, impressed. \n" +
                    "\n" +
                    "Looking closer at the camera feeds, you see guards patrolling the halls, shipments being unloaded, two people being...indiscrete in a broom closet...and-- your eyes stop at what is unmistakably a dim jail cell holding a motionless figure. '*Lorena*,' you breathe. '[y/n], *look*,' says Wesdru, pointing at a tall muscular woman, her red hair in two tight plaits, surrounded by camera and equipment. 'it's Dras.' \n" +
                    "\n" +
                    "The new information swirls in your head while you methodically disconnect all the cameras.";
        } //MYSTERY ROOM
        else if ((!game.withWesdru)) {
            results = "You burst through the door, gun cocked and gripped tightly, only to be confronted with a quivering figure in heavy metal chains, curled up under weak lamplight. Your heart stops for a second--*Lorena*-- before processing the rest of the scene: they're clearly masculine, seemingly dressed in the taters of a formal suit. 'Please, *Please* don't shoot me, *please*, I'll give you more people, have you caught Lorena Sanchez yet? My people can give her another job-' He looks up, eyes widening at your rage and deadly sword pointed at his now bared throat. You've connected the dots: *another* job. 'Fregola. It's a *pleasure* to meet such a disgusting, incorrigible *bastard* in person. Betraying us to Dras, huh? and so clever of you, to know I'd come after her. Tell me. Why does Dras need us?' Your face cracks into a relentless grin, sword-tip spilling blood over Fregola's quivering adam's apple. \n" +
                    "\n" +
                    "'*[Your name]*,'Fregola breathes. Terrified, he tries to collect himself. 'Dras...is stronger than all of us, it's only a matter of time before- She asked me for information. on you and your partner. She wants to make an example.' \n" +
                    "\n" +
                    "Dras is expecting you, if she doesn't already know you're here. But now she's made things personal. You can't leave Fregola here--he knows too much; you know you could easily kill him, with how mad you are, but he definitely knows more he hasn't shared. Decision made, you shoot off Fregola's cuffs, glaring when he yelps conspicuously, and haul him to his feet. 'Follow me. Don't give me an excuse to kill you.' \n";
        }
        else {
            results = "You burst through the door, Wesdru at your heels, arms at the ready, only to be confronted with a quivering figure in heavy metal chains, curled up under weak lamplight. Your heart stops for a second--*Lorena*-- before processing the rest of the scene: they're clearly masculine, seemingly dressed in the taters of a formal suit. 'Please, *Please* don't shoot me, *please*, I'll give you more people-*Wesdru*?' they look up and breathe out heavily in disbelief. '*Fregola*?' she replies, equally if not more baffled. You recognize the name, and quickly put the dots together, your sword raising to point at Fregola in rage. '*You*. You betrayed us! You sold Lorena out to Dras! You *slimy*, lying, little bastard-'\n" +
                    "\n" +
                    "Wesdru stops you with a hand on your chest, expression passionate yet level. 'Explain,' she commands. 'I-' Fregola collects himself anxiously. 'Dras...is stronger than all of us, it's only a matter of time before- She asked me for information. on you and your partner. She wants to make an example.' 'And you assumed if she kidnapped Lorena I would try to save her,' you bite out, infuriated. You turn around, completely uninterested in Fregola's reply. \n" +
                    "\n" +
                    "Dras is expecting you, if she doesn't already know you're here. But now she's made things personal. You can't leave Fregola here--he knows too much; you know you could easily kill him, with how mad you are, but he definitely knows more he hasn't shared. 'We need to move, *now*,' you say to Wesdru, 'and we have to take him.' Wesdru nods, thankfully having understood your reasoning, and threatens Fregola into submission while you try to clear the buzzing in your head.";
        }

        return results;
    }
}
