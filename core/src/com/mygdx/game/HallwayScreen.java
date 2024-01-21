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
        if ((!game.withWesdru) && (game.enteredSR) && (game.inHallSecondTime)) {
            ; //accounting for one path where you can't confront Dras so no second button
        }
        else {
            table.add(button2).width(150).expand();
        }

        //only true if Hallway 2 (Second time in hallway) but not always third button
        if (text[3] != null) {
            button3 = new TextButton(text[3], Heist.skin);
            table.add(button3).width(150).expand();

            button3.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (text[3].equals("Escape")) {
                        game.END = 6;
                        game.setScreen(new EndScreen(game));
                    }
                    else { //grenade
                        game.END = 7;
                        game.setScreen(new EndScreen(game));
                    }
                }
            });
        }

        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.attackedFirst) { //ESCAPE
                    game.END = 3;
                    game.setScreen(new EndScreen(game));
                }
                else if (game.inHallSecondTime) { //RESCUE LORENA
                    if ((game.withWesdru) && (game.enteredSR)) {
                        game.END = 9;
                        game.setScreen(new EndScreen(game));
                    }
                    if (count == 1) { //if second click
                        if ((!game.withWesdru) && (!game.enteredSR)) { //mad but badass
                            game.setScreen(new BroadcastScreen(game));
                        }
                        else {
                            game.setScreen(new CapturedScreen(game));
                        }
                    }
                    //changing game variables
                    game.wasCaptured = true; //even tho not captured in one run, that run skips to broadcast so doesn't matter

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
                        if ((game.withWesdru) && (game.enteredSR)) {
                            game.END = 10;
                            game.setScreen(new EndScreen(game));
                        }
                        else {
                            game.setScreen(new BroadcastScreen(game));
                        }
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
                else { //MYSTERY ROOM - also works for fregola END
                    if (count == 1) { //if second click
                        if (game.attackedFirst) {
                            game.END = 2;
                            game.setScreen(new EndScreen(game));
                        }
                        else {
                            game.setScreen(new HallwayScreen(game));
                        }
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
            text[0] = "Lorena wipes her bloody face on her shirtsleeve and shimmies a jacket off one of the prone guards, pulling it on, presumably, to cover the signs of a fight. Putting her hair down completes the look, as her golden yellow spurred boots and stiff black chaps have somehow avoided any obvious stains. Lorena saunters confidently from the doorway past the guards standing by the door, you and Wesdru quickly following her lead. \n" +
                    "\n" +
                    "'Hey! Wait a minute,' one of the guards calls. Your heart freezes in your chest, hand inching toward the gun at your hip. 'Yeah?' Lorena calls back impatiently. 'What happened? Why were you called to the holding cells so urgently?' 'One of the prisoners was acting up. The situation is under control now,' Lorena drones, the guards rapidly losing interest. 'We've been reassigned to guard the entrance. It's down this hall, right?' \n" +
                    "'Yeah, on the right,' they respond, already turned away. You can't help the huge smile that takes over your face as you catch Lorena's satisfied smirk. \n" +
                    "\n" +
                    "She leads the way down the hall, pulling you and Wesdru into halls to avoid further patrols. During one of these asides, while you all wait for the danger to pass, Lorena speaks. 'I overheard my jailers talking about Fregola, while they thought I was asleep.' 'and?' you whisper back, uninterested. 'He's being held by the entrance, possibly tortured. We need to save him, if only to stop him from causing us even more trouble.' *More?* And then it hits you. 'Save a *traitor*? We're barely gonna make it out ourselves!' Lorena completely ignores your response. 'Just trust me. I'll take care of it.' \n";
            text[1] = "Escape";
            text[2] = "Save Fregola";
        }
        //HALLWAY 2 - SECURITY ROOM
        else if ((game.inHallSecondTime) && (!game.withWesdru) && (game.enteredSR)) {  //alone and evil
            text[0] = "You limp back into the hall, leaving bloodstains where you leaned on the doorframe. " +
                    "The adrenaline is keeping you sharp, but you know it won't last long. " +
                    "Adjusting your looted shoulder holster, you head down the hall as quickly as you can, biting your " +
                    "lip to distract from the pain and fear. You dash into a corridor to avoid incoming footsteps, " +
                    "and peak around the corner once you hear them fade in the other direction--*an unguarded exit*. " +
                    "The guards must have been called as reinforcements. You know you won't get this opportunity again, " +
                    "but your mind rages against even the consideration. \n";
            text[1] = "Rescue Lorena";
            text[2] = "Confront Dras"; //obsolete; button won't be there
            text[3] = "Escape";
        }
        else if ((game.inHallSecondTime) && (game.withWesdru) && (game.enteredSR)) { //fun
            text[0] = "You enter the hall deliberately, still lost in murderous thought. You don't hear any patrols yet, but you know there's at least one between you and Lorena's cell; the cells are on the other side of the building, in the basement. And next door to Lorena's cold, curled up body, Dras is just flaunting her influence, *fucking around*-- \n" +
                    "\n" +
                    "'*Breathe*, [y/n],' Wesdru whispers, 'I'm right here.' You make eye contact and take a deep breath, equal parts sarcastic and grateful. 'We can't have you trying to fry me with your nonexistent laser eyes, as much as you're playing the part right now,' she says, nudging you in the shoulder. You chuckle lightly, feeling some of the tension in your shoulders dissolve into calm concentration. 'Save it for the guards,' she finishes, walking purposefully down the hall. \n" +
                    "\n" +
                    "Before she can reach the next junction you drag her behind the nearest doorway, noticing the approaching patrol before she does. The two of you observe the two guards silently, waiting for them to pass, but their rapidly intensifying conversation forces them to loiter directly in your path forward. 'It was my mother's only defense!' the tall one is saying, 'You had no right.' 'I had no choice! We needed the money, Halth.' 'No. You needed the money,' Halth replies bitingly. \n" +
                    "\n" +
                    "'Watch this,' Wesdru murmurs in your ear. She pulls out-- a throwing star, of all weapons, and smirks at you before aiming at the increasingly defensive shorter guard. You wince as it buries itself in their leg, panicked eyes urging their reluctant partner into action. The taller one--Halth--lunges toward the doorway, but Wesdru is prepared. She slits both of their throats before they can make a sound. \n" +
                    "\n" +
                    "'Well that's not what I meant to happen. But hey, no noise!' You stare at her heaving chest, unimpressed. \n" +
                    "\n" +
                    "There are more guards than either of you expect, but you incapacitate them in short order, informally competing to see how flashy you can make the final blow, or how close you can get without them noticing. \n";
            text[1] = "Rescue Lorena";
            text[2] = "Confront Dras";
        }
        //HALLWAY 2 - MYSTERY ROOM
        else if ((game.inHallSecondTime) && (!game.withWesdru)) { //mad but badass
            text[0] = "Thankfully, Fregola seems to have decided aligning himself with you is in his self-interest, and cooperates, slouching passively next to you. 'Hey...can I have a knife? Just a small one, in case we get attacked? They'll kill me, I'm defenseless as it is!' The comment enrages you; the arrogance to think you'll give him a single *inch* after what he did. 'Sure! You can have a knife,' you say pleasantly, before nearly shoving one in his gut. He double-takes, jumping away with a gasp. You drew blood, but left no real damage. Drawing close, you hiss, 'Shut. your. slimy. mouth.' \n" +
                    "\n" +
                    "You lead the way through the building, stopping to look behind every door and hide from moving patrols, noting a guarded exit about halfway through. Fregola follows in silence; you know he won't try anything again. Near the end of the building you start to notice the guards all seem to be heading toward a specific corridor, some entering the most busy door with expensive-looking equipment. You can hear loud discussion and shuffling from within when the door is pushed open, along with clear commands--most likely Dras. Down the hall, a couple guards stand by a staircase leading down, to a basement presumably. With jail cells, presumably. \n" +
                    "\n" +
                    "'Oh no,' Fregola whines, '*please* let's just leave, she's going to kill us when she catches us!'\n";
            text[1] = "Rescue Lorena";
            text[2] = "Confront Dras";
        }
        else if ((game.inHallSecondTime)) { //romantic
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

        if (game.usedLockpick) {
            results = "You don't have time to argue. With a sigh, your group navigates to the entrance hall according to the guard's directions. There are two doors, one marked with cryptic runes and one unmarked. Wesdru gestures at the unmarked door. 'I recognize that door's  markings. I think it's their security room. Fregola must be in this room.' \n" +
                    "\n" +
                    "Lorena casually unlocks the door with your lockpicks, even faster than you could've done it. The seemlessness almost ensures the security personel won't flag you as suspicious. She leads the way into the room, cocking her gun to draw Fregola's attention from where he's slumped on the floor of the small room, tied to the wall and surrounded with boxes of detritus. His eyes widen with surprise, then terror. '*Lorena*, I- Please, uh, I know--' \n" +
                    "\n" +
                    "'I don't want to fucking hear it. You're a lying, disgusting, bastard that I want to think about as little as possible, hence me breaking you out so I don't stay up at night picturing you tortured to death. Which you must know is in the cards for you right now.' Fregola opens and closes his mouth uselessly, trying to salvage his pitiable, pathetic act by turning to you for sympathy. 'Oh don't fucking look at me, I wanted to leave you here to rot,' you chuckle coldly. 'In fact, given the chance I would relish torturing you myself,' you add, mostly serious, although you know Wesdru and Lorena take it an intimidation tactic. \n" +
                    "\n" +
                    "'Get up,' Wesdru barks, cutting Fregola's restraints, 'We're getting you out, but once we cross the threshold, we never want to see you ever again.' \n";
        }
        //HALLWAY 2 - RESCUE LORENA
        else if ((game.inHallSecondTime) && (game.wasCaptured) && (game.enteredSR)) { //alone and evil
            results = "You pull that rage in close, letting it fuel you. There are no other options, you tell yourself. You *will* save your partner. Or die trying. \n" +
                    "\n" +
                    "You storm past the exit, making similar evasive moves to sneak your way to the corridor you saw on the camera feeds. There are five guards total, three by Dras's room and two by the staircase to the jail. Planning, somewhat wishfully, to simply sharpshoot all of them, you manage to bring two down before they're storming your spot, forcing you to combine your shots with dodges and lunges you can barely manage.  \n" +
                    "\n" +
                    "It's made less effective by their obvious efforts to be non-lethal, but they eventually subdue you; one knocks the gun out of your hand while another kicks your bullet wound, sending you crashing to the floor. They're all injured, but they manage to roughly drag you into the basement. You pass out before you cross the threshold. ";
        }
        else if ((game.inHallSecondTime) && (game.wasCaptured) && (!game.withWesdru)) { //mad but badass
            results = "You lean in to Fregola's space, your voice icy. 'I'll only say this once. I'm not leaving this building without my wife. So either follow my lead, or try to make it out on your own.' Fregola seems to consider the second option, warily. 'I promise I won't shoot you in the back,' you add casually, and that seems to tip him over the edge. Still making eye contact, Fregola slowly backs away from you and then breaks into a run. You roll your eyes and half-heartedly wish him luck; he doesn't even have a gun. \n" +
                    "\n" +
                    "Taking a deep breath, you refocus on the guards in the hall. You're alone but unharmed, so you might have a chance. You try to snipe as many as you can--one, two, three, fall to the ground before you have to dive for cover. You're only option now is distracting them and then rushing them. \n" +
                    "\n" +
                    "Letting the guards inch closer, you pull a dagger from your sleeve and throw it at the closest one, darting forward as soon as the screams start to ring out, drawing all the assailants' attention. Out of your left peripheral vision, you see the black blur before you feel it, already knowing it has the force to knock you out. You attempt to dodge the rifle-hit, but it connects with your skull and you know nothing more. ";
        }
        else if ((game.inHallSecondTime) && (game.wasCaptured)) { //romantic
            results = "The thought of Lorena imprisoned in an inhospitable basement fills you with determination. 'Woah there, cowboy,' exclaims Wesdru, pulling you back behind the corner when you start walking into the busy hall. 'If we go now, we'll be captured for sure!' Fregola whines highly at that, tucking himself behind Wesdru's big frame. \n" +
                    "\n" +
                    "After a couple minutes the hall has cleared significantly and you can't wait any longer. You whip into the hall before Wesdru can protest, forcing her to provide cover as you demolish all the guards in your way, throwing knives and pistol-whipping indiscriminately. Panting, you stomp down into the basement, hearing Wesdru and Fregola behind you. \n" +
                    "\n" +
                    "'If my wife has a single scratch on her, I will pull that motherfucker limb from limb.' 'Can't argue with that,' Wesdru replies easily, nudging Fregola to keep up. 'In fact, I'll help you hold her dow--damn.' The room is indeed dank, disgustingly so, but what stops both of you in your tracks is the sight of Lorena, covered in mud and blood, staring at you through iron bars, eyes glinting in the light. \n" +
                    "\n" +
                    "She sighs deeply, and the moment is broken. 'Holy shit,' you start shakily, running up to her, 'Are you okay? Are you bleeding?' 'I'll be okay,' she replies breathily. Clearing her throat, she continues, smirking knowingly, 'This *is* a stealth mission right? You haven't killed half the building as revenge have you?' 'Well, in my defense,' you murmur, smiling deeply and cradling her face in your dirty hands, 'they shot first.' She laughs deeply and with pure joy, warming to your core, inspiring you to press a gentle kiss to her chapped lips. \n";
        }
        //HALLWAY 2 - AMBUSH ROOM
        else if ((game.inHallSecondTime) && (game.enteredSR)) { //fun
            results = "Nearing the intended hall, you notice two guards in front of large doors; you could sneak by them, but you have a sneaking suspicion that... 'Just give me a minute,' you whisper to Wesdru at her alarmed expression considering you are effectively picking a fight. Thankfully, you dispatch them with little effort, using lethal hand-to-hand and slowly lowering them to the floor to minimize the noise. Checking the door, you confirm your theory: it's an exit. \n" +
                    "\n" +
                    "A huge smile dawns on Wesdru's face, all annoyance at the risk forgotten. She jogs over. 'Ok fine, you win,' she begins, ruffling your hair, 'Now we can rely on having a relatively clear escape route.' The grin turns cheeky in an instant. 'I never doubted you, cowboy.' You're already walking away as you respond, 'And once I kill Dras, you'll mean that.' \n" +
                    "\n" +
                    "Your plan obvious, Wesdru pulls her rifle off her back, preparing to cover for you as you charge the guards at Dras's door. It's a bloodbath; you're in your element, dodging inexperienced punches and bullets alike, incapacitating guard after guard furiously. But the slick red running down your arms just replenishes your hunger to confront one person in particular. To end this.\n" +
                    "\n" +
                    "You kick the door in without hesitation, Wesdru cocking her gun loudly behind you. \n" +
                    "\n" +
                    "'Where is she,' you intone, holding a sword to Dras's neck, dripping with warm blood. ";
        }
        else if ((game.inHallSecondTime) && (!game.withWesdru)) { //mad but badass
            results = "You don't even dignify Fregola's bargaining with a response. You refuse to take responsiblity for him; if Dras catches him, he's already ruined her plans enough to justify a swift execution, meaning his only option is to play nice and make it out with you. Sure enough, after you snipe the first guard he's quick to arm himself with the dropped gun and give you some cover. \n" +
                    "\n" +
                    "The guards immediately notice their fallen comrades; you rush them brazenly rather than letting them fire, holstering your gun and unsheathing your swords in one fluid movement. Fregola's a surprisingly good shot, picking off newcomers as you decapitate one guard and stab another through the gut, spinning around to slit a gunman's throat. (Think Kill Bill). You're in your element, and soon the floor glistens with blood, the hall clear. Running on adrenaline, you burst into the room with Fregola on your tail. \n" +
                    "\n" +
                    "Bright blue eyes lock onto yours, knocking the breath from your chest. Dras is holding Lorena's chin up in an uncomfortable position, considering how she's chained to the wall. The angle gives you a clear view of--well, all her injuries, but your eye is instantly drawn to the dark purple ring of bruises on her neck. This, plus the look of contempt on Dras's face, the sheer *relief* you see in Lorena's eyes--it's enough. In fact, it's more than enough. You shoot Dras in the knee, without hesitation. \n";
        }
        else if ((game.inHallSecondTime)) { //romantic
            results = "'Let's deal with that bastard first,' you bite out, still staring at the occupied room. 'I can get behind that,' Wesdru replies, equally cold. Turning to Fregola, she adds, 'Step on our toes, and you're the first to die.' Fregola nods vigorously, eyes wide. \n" +
                    "\n" +
                    "On your count, you and Wesdru start sniping guards in tandem, eventually rushing them rather than letting them fire. You holster your gun and unsheathe your swords in one fluid movement, trusting Wesdru to cover you as you decapitate one guard and stab another through the gut, spinning around to slit a gunman's throat. (Think Kill Bill). You're in your element, and soon the floor glistens with blood, the hall clear. Running on adrenaline, you burst into the room without waiting for Wesdru. \n" +
                    "\n" +
                    "You're disarmed almost immediately, the woman you assume to be Dras standing over you arrogantly. She's tall and muscular, her red hair in tight plaits close to the scalp. Lorena is tied to the back wall, opposite a wall of wires, monitors, and lenses. Recording equipment. Fregola's words come back-- 'make an example'-- and the pieces click: she's planning an execution, live to the whole town, for political gain.  \n" +
                    "\n" +
                    "Dras looms menacingly. 'Thanks for making an appearance. We've been waiting for you.' \n";
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
            results = "You and Wesdru burst through the door, shooting the two guards at the elaborate desk at the far end of the room before they can alert anyone, but not before they shut off the wall of camera feeds with a soft *click*. The bodies slump to the floor lifelessly, making room for you to examine the controls. You wake the small interface screen set into the table by waving your hand over the holographic keyboard, feeling Wesdru settle behind you, looking over your shoulder. She sighs in response to the password prompt that appears. 'Don't worry. I always carry brute-force code on me,' you reply smugly, holding a usb drive. Within a minute of inserting it into the panel, the displays blink on again. 'Guess the password was 0000 or something,' Wesdru says, begrudgingly impressed. \n" +
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
