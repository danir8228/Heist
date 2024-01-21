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

public class EndScreen implements Screen {

    final Heist game;

    private Music backgroundMusic;

    private String[] text;

    private Stage stage;
    private Table table;
    private TextButton button1;
    private TextButton button2;

    public EndScreen(final Heist game) {
        this.game = game;

        //load sound effects and music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Paper Mache.mp3"));
        backgroundMusic.setLooping(true);
    }

    @Override
    public void show() {
        //text
        text = returnText();

        if (text[1].equals("bad")) {
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("It's Got Old.mp3"));
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
                game.END = 0;

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
        if (text[1].equals("good")){
            ScreenUtils.clear(0, 0.2f, 0, 1);
        }
        else { //bad
            ScreenUtils.clear(0.2f, 0, 0, 1);
        }

        game.batch.begin();
        game.font.draw(game.batch, text[0], 20, 400);
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

    public String[] returnText() {
        String[] text = new String[2];

        switch (game.END) {
            case 1: //KILL HER
                if (!game.withWesdru) { //mad but badass
                    text[0] = "You consider playing into her plans, catching her unaware at some later point, but you can't bring yourself to be anything less than ruthless \n" +
                            "in the face of her cruelty. Your eyes flash to Lorena one last time; you see her eyes widen in realization of your decision. She screams at you to \n" +
                            "stop, but it's too late. \n" +
                            "\n" +
                            "The bullet hits Dras in the center of her forehead, freezing her in shock. You see a flicker of surprise and pain in her eyes before they go blank \n" +
                            "and her body slumps to the ground. \n" +
                            "\n" +
                            "There is a moment of complete, unbroken silence before the guards open fire and the world goes black. ";
                    text[1] = "bad";
                }
                else { //romantic
                    text[0] = "Before anyone can react, Lorena--having freed herself from her restraints somehow--lunges at Dras and immediately becomes locked in a desperate \n" +
                            "brawl, her guards afraid to shoot and accidentally hit their boss. You ruthlessly pull your hands from the rope--ignoring the pull of dislocating \n" +
                            "something and sharp burning from the friction--grabbing your discarded gun and \n" +
                            "picking off all the guards in the room.\n" +
                            "\n" +
                            "Freeing Wesdru quickly, you join Lorena, taking advantage of an opening to knock Dras cold with a strong hit from the back of your rifle. Lorena \n" +
                            "looks down at her prone body with obvious murderous intent, eyes blazing with rage. 'Do it,' you whisper. Her eyes widen slightly with surprise, \n" +
                            "but the approval hardens her resolve. You hand her your gun and she shoots Dras in the head without further preamble. \n" +
                            "\n" +
                            "The adrenaline of the moment fills you with urgency-- you hold Lorena's face in your hands and kiss her deeply, breathing her in, completely \n" +
                            "overwhelmed by the feeling of knowing that you could have lost her. She kisses back, clutching at your back and trembling. 'I fucking love you,' \n" +
                            "you whisper softly. She chuckles wetly, pressing her forehead to yours with a deep sigh. 'I missed you,' she replies. \n" +
                            "\n" +
                            "You hear Wesdru freeing a terrified Fregola, but pay it no mind. The remaining guards should have retreated by now, leaving the exits unguarded, or \n" +
                            "they'll have four angry cowboys to deal with. \n" +
                            "\n" +
                            "'No one owns this town,' you direct at the still-transmitting cameras, 'not Dras. not us. And never let anyone tell you otherwise.' ";
                    text[1] = "good";
                }
                break;
            case 2:
                text[0] = "Almost as soon as you're all back in the hall, you hear the thundering of guards behind you. \n" +
                        "\n" +
                        "'She knows!' you yell, breaking into run just as a patrol 20 people strong turns the corner. They start shooting almost immediately, but Wesdru \n" +
                        "has already thrown a smoke bomb, turning the hall into a battlefield. You saw a pair of double doors at the end of the hall before your vision was \n" +
                        "obscured and just keep running for them, hoping Wesdru and Lorena are doing the same. \n" +
                        "\n" +
                        "There's a cry of pain from your right--Lorena--but you can hear that she's still running, so you don't slow down. Soon you percieve the gas \n" +
                        "accumulating; reaching out, you push the doors open with a click and turn to watch as Fregola, Wesdru and Lorena, the latter limping, make it \n" +
                        "out behind you. \n" +
                        "\n" +
                        "Fregola immediately turns to face you and bows awkwardly, before running off through the streets. Wesdru is already halfway across the street; \n" +
                        "she leads the two of you, Lorena supporting herself on your shoulders, into a dilapidated building across from Dras's hold. 'We can lay low here \n" +
                        "for a couple hours. She won't suspect we would stay close, and no one knows Lorena's injured.' \n" +
                        "\n" +
                        "'What a fucking day,' you sigh out, overwhelmed. Lorena breaks into deep laughter at that, unable to breathe after a couple seconds, so contagiously \n" +
                        "that you and Wesdru can't help but join in. \n";
                text[1] = "good";
                break;
            case 3:
                text[0] = "'Lorena, we don't have time,' Wesdru interrupts. You nod. 'She'll be infuriated when she realizes we're gone, and if we're still in the building, \n" +
                        "that does NOT bode well for us.' You look into Lorena's eyes, projecting the love and trust you hold for her. 'I know you don't want anyone to be in \n" +
                        "pain, but Fregola made his decision. He knew who he was getting involved with.' You can see the moment your point hits its mark. 'Fine. But no more \n" +
                        "casualties.' \n" +
                        "\n" +
                        "Without another word, she continues down the hall, you and Wesdru on her heels. After a couple minutes you hear the rush of guards behind you. 'She \n" +
                        "knows!' you yell, breaking into run just as a patrol 20 people strong turns the corner. They start shooting almost immediately, but Wesdru has already\n" +
                        "thrown a smoke bomb, turning the hall into a battlefield. You saw a pair of double doors at the end of the hall before your vision was obscured and \n" +
                        "just keep running for them, hoping Wesdru and Lorena are doing the same. \n" +
                        "\n" +
                        "There's a cry of pain from your right--Lorena--but you can hear that she's still running, so you don't slow down. Soon you percieve the gas \n" +
                        "accumulating; reaching out, you push the doors open with a click and turn to watch as Wesdru and Lorena, the latter limping, make it out behind you. \n" +
                        "\n" +
                        "Wesdru leads the two of you, Lorena supporting herself on your shoulders, into a dilapidated building across from Dras's hold. We can lay low here \n" +
                        "for a couple hours. She won't suspect we would stay close, and no one knows Lorena's injured.' \n" +
                        "\n" +
                        "'What a fucking day,' you sigh out, overwhelmed. Lorena breaks into deep laughter at that, unable to breathe after a couple seconds, so contagiously \n" +
                        "you and Wesdru can't help but join in. ";
                text[1] = "good";
                break;
            case 4:
                if (!game.withWesdru) {
                    text[0] = "Lacking a better option, you slowly lower your gun to the ground, sliding it over to Dras and getting on your knees according to her continued demands. \n" +
                            "\n" +
                            "'The two Sanchezes,' she begins, gesturing at her guards to tie you up. 'Not him. Get rid of him, will you? I'm done with that rat.' Two guards pick \n" +
                            "Fregola up bodily and drag him out of the room, ignoring his increasingly desperate screams of protest, whose end is punctuated by a loud gunshot just\n" +
                            " outside the room, which Dras ignores. The other guards have lowered their guns, focusing on their leader. \n" +
                            "\n" +
                            "'Thought you could burst in and fix everything, huh? Bring me to justice, like you do around town, cowboys? You righteous fucks.' Dras is snarling \n" +
                            "now, gaining speed and so caught completely unaware when you rip your hands out of the poorly tied restraints and lunge at her, knocking her out cold \n" +
                            "with a single precise hit to the head. '*Yes*,' you think. She's not dead, purposely. She deserves to feel the full humilation of her failure.\n" +
                            "\n" +
                            "There's a blur from your peripheral vision, and Lorena-- suddenly, somehow, free from her restraints--rushes the rest of the guards methodically \n" +
                            "before they can attack, a flurry of blows and dodges. You pick up your gun and provide cover, handling the flow of reinforcements through the door. \n" +
                            "\n" +
                            "Soon the silence reigns. Too soon. 'Some must have retreated. Figured their lives were worth more than this,' you say blankly, turning to look at \n" +
                            "Lorena. You can see she's overwhelmed but refusing to process it until you both get to safety. Walking over slowly,  you wrap your arms around her \n" +
                            "gingerly, understanding when she barely returns the pressure before backing away and facing the cameras. \n" +
                            "\n" +
                            "'This town is *ours*,' Lorena growls. 'We live and let live. I hope none of you ever forgets that.' \n" +
                            "\n" +
                            "Direct as always, Lorena shoots the equipment into silence without further comment. \n";
                    text[1] = "good";
                }
                else {
                    text[0] = "Before anyone can react, Lorena--having freed herself from her restraints somehow--lunges at Dras and immediately becomes locked in a desperate \n" +
                            "brawl, her guards afraid to shoot and accidentally hit their boss. You ruthlessly pull your hands from the rope--ignoring the pull of dislocating \n" +
                            "something and sharp burning from the friction--grabbing your discarded gun and picking off all the guards in the room.\n" +
                            "\n" +
                            "Freeing Wesdru quickly, you join Lorena, taking advantage of an opening to knock Dras cold with a strong hit from the back of your rifle. Lorena \n" +
                            "looks down at her prone body with obvious murderous intent, eyes blazing with rage. 'Don't do it,' you whisper. 'Death would be a mercy right now, \n" +
                            "one she doesn't deserve.' Lorena nods curtly, dropping the gun with a dull clang. \n" +
                            "\n" +
                            "The adrenaline of the moment fills you with urgency-- you hold Lorena's face in your hands and kiss her deeply, breathing her in, completely\n" +
                            "overwhelmed by the feeling of knowing that you could have lost her. She kisses back, clutching at your back and trembling. 'I fucking love you,' \n" +
                            "you whisper softly. She chuckles wetly, pressing her forehead to yours with a deep sigh. 'I missed you,' she replies. \n" +
                            "\n" +
                            "You hear Wesdru freeing a terrified Fregola, but pay it no mind. The remaining guards should have retreated by now, leaving the exits unguarded, \n" +
                            "or they'll have four angry cowboys to deal with. \n" +
                            "\n" +
                            "'No one owns this town,' you direct at the still-transmitting cameras, 'not Dras. not us. And never let anyone tell you otherwise.' ";
                    text[1] = "good";
                }
                break;
            case 5:
                if (game.attackedFirst) {
                    text[0] = "'Fuck you,' you intone, leaning as far forward as the restraints will allow. \n" +
                            "\n" +
                            "'Oh, what was that? Can you say it once more for the viewers at home?' Dras teases viciously, mockingly crouching down as if to listen closer. \n" +
                            "\n" +
                            "You take the opportunity to spit directly into her face, hitting her left eye. \n" +
                            "\n" +
                            "She hisses, furious, wiping at her face once with the back of her hand before kicking you sharply in the ribs. You can't help but groan and double \n" +
                            "over, winded. As soon as you manage to back up, she makes direct eye contact, cruel and cold, and unflinchingly shoots Wesdru in the chest. It takes \n" +
                            "a few seconds for her to die, blood pumping out in gushes, futilely. Time slows, the unspeakable brutality numbing your chest. You barely register \n" +
                            "Lorena's cries of distress. \n" +
                            "\n" +
                            "'Nothing to say?' Dras laughs deeply, obviously relishing in her success. 'I must say, it's incredibly satisfying to see y/n Sanchez *shut her \n" +
                            "fucking mouth*.' There's a blur from your peripheral vision, and Lorena-- suddenly, somehow, free from her restraints--launches herself at Dras \n" +
                            "with a guttural cry. She manages to dig the heel of her hand into Dras's windpipe, downing her with a choked off breath and allowing Lorena to \n" +
                            "stomp mercilessly at her chest. You think you see her successfully break a rib before Dras's guards riddle her with bullets, her body slumping to \n" +
                            "the ground, lifeless. \n" +
                            "\n" +
                            "And the world ends. You don't percieve the minutes it takes for Dras to lift herself off the bloodsoaked floor, or even the pain as she shoots \n" +
                            "you in the head. \n";
                    text[1] = "bad";
                }
                else {
                    text[0] = "'Fuck you,' you intone, leaning as far forward as the restraints will allow. \n" +
                            "\n" +
                            "'Oh, what was that? Can you say it once more for the viewers at home?' Dras teases viciously, mockingly crouching down as if to listen closer. \n" +
                            "\n" +
                            "You take the opportunity to spit directly into her face, hitting her left eye. \n" +
                            "\n" +
                            "She hisses, furious, wiping at her face once with the back of her hand before kicking you sharply in the ribs. You can't help but groan and double \n" +
                            "over, winded. As soon as you manage to back up, she makes direct eye contact, cruel and cold, and unflinchingly shoots you in the chest. You can \n" +
                            "feel the blood pumping out in gushes, futilely, but there's no pain. Time slows, the unexpected brutality numbing your other senses. You barely \n" +
                            "register Lorena's screams. \n" +
                            "\n" +
                            "'Nothing to say?' Dras laughs deeply, obviously relishing in her success. 'I must say, it's incredibly satisfying to see y/n Sanchez *shut her \n" +
                            "fucking mouth*.' There's a blur from your peripheral vision, and Lorena-- suddenly, somehow, free from her restraints--launches herself at Dras \n" +
                            "with a guttural cry. She manages to dig the heel of her hand into Dras's windpipe, downing her with a choked off breath and allowing Lorena to \n" +
                            "stomp mercilessly at her chest. You think you see her successfully break a rib before Dras's guards riddle her with bullets, her body slumping to\n" +
                            "the ground, lifeless. \n" +
                            "\n" +
                            "You let go. And the world ends. ";
                    text[1] = "bad";
                }
                break;
            case 6:
                text[0] = "You don't stop to think; you don't have the time. Or at least that's what you tell yourself. All your focus narrows to the door, and the eternity\n" +
                        "it takes for you to arrive and push it open heavily. It then narrows to sneaking your way to you and Wesdru's habitual emergency meeting place, \n" +
                        "in case you get separated. \n" +
                        "\n" +
                        "She arrives within a half-hour, eyes widening at your weakened, bloody state, and surprisingly, covered in blood herself. 'Thank god you're alive,' \n" +
                        "she gasps, holding most of your body weight now, 'I picked a fight to thin her forces inside. The only thing that could make this worse is if you \n" +
                        "hadn't made it out.' The wording makes you fight for consciousness. \n" +
                        "\n" +
                        "'Wait...wha-du-mean,' you slur. Wesdru's eyes fill with tears. 'Lorena's dead. Dras executed her on a live broadcast, for the whole town to see. \n" +
                        "As a warning not to interfere with her rise to power. She wants to be a tyrant,' Wesdru continues, seemingly unaware she's still speaking, 'a \n" +
                        "dictator. And with that stunt? No one will stand against her. She distracted us with the allure of saving--of finding someone we love, and her \n" +
                        "plan worked perfectly. and now...' Wesdru trails off, seemingly overwhelmed by the hardships ahead, but you've stopped processing anything \n" +
                        "beyond...the image of your wife in that jail cell, now the last time you'll ever see her. You simply cannot contain that thought, the agony mixing\n" +
                        "with the sharp pain of your whole body, pulling you under at last. You're not dead. But in that moment, you wish you were. ";
                text[1] = "bad";
                break;
            case 7:
                text[0] = "Suddenly, you remember the grenade you grabbed at the market, hours earlier. It's heavy and cold in your hand, demanding real consideration. \n" +
                        "Fregola yelps when he sees it, obviously terrified but determined not to interact with you if he can help it. The noise draws Wesdru's attention. \n" +
                        "'y/n...you cannot be considering blowing up that room!' She grins widely at you. 'Who am I kidding, with your impulsive streak? Of course you are. \n" +
                        "God help us.' You smirk back, knowing that she's delighted, and that, presented with the opportunity, she would do the same thing. \n" +
                        "\n" +
                        "Without further ado, you manage to time it perfectly, throwing it into the room as it's held open for a particularly large piece of equipment. \n" +
                        "You hear the commotion, yells and enraged commands that could only be from Dras, before the blast silences all. Boots stomp over to examine the \n" +
                        "wreckage, leaving the basement unguarded. You signal Wesdru to stay with Fregola and sneak down the stairs. \n" +
                        "\n" +
                        "Lorena, already free, whips around at the sound of your descent into the room, gun pointed at your head. She sags when she recognizes you, \n" +
                        "throwing herself into your arms. 'I love you so fucking much,' she gasps wetly, your arms around her gentle in light of her bruises. You shift \n" +
                        "so you can look in her eyes, her head in your hands, your thumbs wiping dirt softly from her cheeks. 'Missed you,' you whisper. \n" +
                        "\n" +
                        "It's trivial from there, to join the others and reach the now unguarded exit. ";
                text[1] = "good";
                break;
            case 8:
                if (game.attackedFirst) {
                    text[0] = "'Nothing to say?' Dras laughs deeply, obviously relishing in her success. 'I must say, it's incredibly satisfying to see y/n Sanchez *shut their \n" +
                            "fucking mouth*.' \n" +
                            "\n" +
                            "You lower your head when she begins to sneer, as if disheartened, which covers for your wince as you expertly slide your dislocated wrist out of the\n" +
                            "bonds. Slipping the dagger out of the sheath at your low back, you lunge forward and slash Dras's throat. She slumps to the ground immediately, \n" +
                            "choking for a few seconds before going still. The shock stops you from feeling any sense of satisfaction or disgust. \n" +
                            "\n" +
                            "There's a blur from your peripheral vision, and Lorena-- suddenly, somehow, free from her restraints--launches herself at the incoming guards, \n" +
                            "brazenly rushing their unfocused guns. You quickly free Wesdru and join the fight, picking up a discarded gun to take care of reinforcements. \n" +
                            "Lorena groans as a bullet hits her shoulder, but doesn't let it stop her. \n" +
                            "\n" +
                            "A full minute passes without further attack, only your heartbeat and Wesdru and Lorena's heavy breathing disturbing the silence. 'They must have \n" +
                            "realized she's dead, meaning they're out of a job and dying for nothing,' you intone blankly. 'From the broadcast,' adds Wesdru, bringing all of \n" +
                            "your attention to cameras which are seemingly still transmitting. 'This town is *ours*,' Lorena growls. 'We live and let live. I hope none of you \n" +
                            "ever forgets that.' \n" +
                            "\n" +
                            "Direct as always, Lorena shoots the equipment into silence without further comment. \n";
                    text[1] = "good";
                }
                else {
                    text[0] = "'Nothing to say?' Dras laughs deeply, obviously relishing in her success. 'I must say, it's incredibly satisfying to see y/n Sanchez *shut their \n" +
                            "fucking mouth*.' \n" +
                            "\n" +
                            "You lower your head when she begins to sneer, as if disheartened, which covers for your wince as you expertly slide your dislocated wrist out of \n" +
                            "the bonds. Slipping the dagger out of the sheath at your low back, you lunge forward and slash Dras's throat. She slumps to the ground immediately, \n" +
                            "choking for a few seconds before going still. The shock stops you from feeling any sense of satisfaction or disgust. \n" +
                            "\n" +
                            "There's a blur from your peripheral vision, and Lorena-- suddenly, somehow, free from her restraints--launches herself at the incoming guards, \n" +
                            "brazenly rushing their unfocused guns. You quickly join the fight, picking up a discarded gun to take care of reinforcements. Lorena groans as \n" +
                            "a bullet hits her shoulder, but doesn't let it stop her. \n" +
                            "\n" +
                            "A full minute passes without further attack, only your heartbeat and Lorena's heavy breathing disturbing the silence. 'They must have realized \n" +
                            "she's dead, meaning they're out of a job and dying for nothing,' you intone blankly. 'From the broadcast,' adds Lorena, bringing your attention \n" +
                            "to cameras which are seemingly still transmitting. 'This town is *ours*,' Lorena growls. 'We live and let live. I hope none of you ever forgets that.' \n" +
                            "\n" +
                            "Direct as always, Lorena shoots the equipment into silence without further comment. ";
                    text[1] = "good";
                }
                break;
            case 9:
                text[0] = "There is a door and a doorway to some stairs at the end of the hall, now unguarded. You're willing to bet the dim dank cell you saw on the \n" +
                        "surveillence room monitors would be in an omninous basement.\n" +
                        "\n" +
                        "You beeline for the basement stairs, calling back to Wesdru. 'If my wife has a single scratch on her, I will pull that motherfucker limb from limb.'\n" +
                        "'Can't argue with that,' she replies easily, taking the steps two at a time. 'In fact, I'll help you hold her dow--damn.' The room is indeed dank, \n" +
                        "disgustingly so, but what stops both of you in your tracks is the sight of Lorena, covered in mud and blood, staring at you through iron bars, eyes \n" +
                        "glinting in the light. \n" +
                        "\n" +
                        "She sighs deeply, and the moment is broken. 'Holy shit,' you start shakily, running up to her, 'Are you okay? Are you bleeding?' 'I'll be okay,' she \n" +
                        "replies breathily. Clearing her throat, she continues, smirking knowingly, 'This *is* a stealth mission right? You haven't killed half the building \n" +
                        "as revenge have you?' 'Well, in my defense,' you murmur, smiling deeply and cradling her face in your dirty hands, 'they shot first.' She laughs \n" +
                        "deeply and with pure joy, so contagiously that you and Wesdru can't help but join in. \n" +
                        "\n" +
                        "You fill her in as you unlock the door, but the three of you are at a loss as to Dras's ultimate plan, especially considering it's seemingly already \n" +
                        "failed. 'Let's go ask her then,' Wesdru suggests easily, already running up the stairs. 'Wesdru!' you call up lightheartedly, offering Lorena some \n" +
                        "weapons. \n" +
                        "\n" +
                        "'Let's just leave while we're ahead, I'm sure someone will fill us in,' you decide once you regroup, 'And I have to catch up with my wife.' ";
                text[1] = "good";
                break;
            case 10:
                text[0] = "Dras laughs stiltedly, obviously surprised but unwilling to admit it. 'She'll be joining us soon. If you lower your weapons.' You quirk an eyebrow\n" +
                        "and call her bluff, pushing the blade closer to her neck, where it catches and draws a thin stream of blood. 'You want us alive, or you would have \n" +
                        "killed us already,' you whisper in her ear. 'I have no idea what your stupid fucking plan was, but now, you're going to tell your guards to release \n" +
                        "my wife, and you're going to let me and Wesdru walk out of her like honored guests.' Dras is trembling now, unsure whether to look at you or whatever\n" +
                        "menacing look is on Wesdru's face behind you. \n" +
                        "\n" +
                        "Wesdru, having heard your ultimatum, shoots a couple rounds into the ceiling. 'NOW, YOU FUCKING IDIOT,' she bellows. 'Yes, yes, okay,' Dras murmurs, \n" +
                        "quickly giving her guards your orders to their confusion. They seem even less likely to attack having witnessed your show of power and Dras's fearful \n" +
                        "response.\n" +
                        "\n" +
                        "'You're a joke,' you tell her flatly, confident she won't cross you. 'Don't bother us again, or I won't bother to play nice.' ";
                text[1] = "good";
                break;
            case 11:
                text[1] = "good";
                if (!game.withWesdru) {
                    text[0] = "'*No*,' you hiss emphatically, 'I need us to-- we're making it out. We need to leave--' Distantly, you feel your body start to shake, the \n" +
                            "adrenaline wearing off. Lorena's eyes widen, searching for eye contact. 'y/n. Look at me. I'm alive. We're alive. I understand. We're leaving now, \n" +
                            "I promise.' \n" +
                            "\n" +
                            "Thankfully, the exit is down the next hall, unguarded and--held open by Wesdru. She rushes forward, helping Lorena with your weight. 'You're a \n" +
                            "sight for sore eyes. I was about to rush in, guns blazing, and save y'all! After the mess I made outside, these guards would have made slim \n" +
                            "pickings.' Lorena gasps. 'That was you? I knew something must've been off, for them to abandon their posts like that. We owe you our lives, Wesdru.'\n" +
                            "Wesdru nods, obviously touched. At this point, you've reached the alleyway from which you and Wesdru scoped the building a couple hours ago. \n" +
                            "\n" +
                            "You stop to catch your breaths, but Wesdru anxiously leads you on. 'We can't stop; Dras is furious that you escaped. She was planning to execute \n" +
                            "you on a live broadcast to the whole town--part of her ploy to seize a tyrannical rule--but y'all made her look like an idiot, posturing and then \n" +
                            "completely failing to deliver. She shot Fregola,' she says with a grimace, 'but it obviously wasn't what she intended. She'll have noticed y'all \n" +
                            "aren't in the building by now and dispatched forces to give chase.' \n" +
                            "\n" +
                            "That's the last you hear, before you pass out into peaceful rest. \n";
                }
                else {
                    text[0] = "'I am so damn curious to know what that bitch is planning,' states Wesdru on seeing the utter lack of guards in the hall. 'Well you can stay and\n" +
                            "have a chat with her if you want, but I'm fucking done,' you respond under your breath. Lorena seems to agree, pushing past you and Wesdru and \n" +
                            "walking down the hall calmly. You share a shocked look with Wesdru before the three of you follow her.\n" +
                            "\n" +
                            "'Our best bet is to camouflage with the guards, in case any come out and see us. Most of them don't know what we look like and they don't wear \n" +
                            "uniforms,' Lorena explains when you catch up. 'Remind me why we don't do jobs together anymore?' you ask wistfully, half-rhetorically. She rolls \n" +
                            "her eyes but responds, 'Because no one can afford both of us.' 'I guess we're just that good,' you murmur softly, making intense eye contact. \n" +
                            "\n" +
                            "'Jesus fucking Christ, get a room will you?' Wesdru interrupts, pulling ahead to gesture at the exit you passed before, now unguarded. Fregola \n" +
                            "sprints at it immediately, disappearing into the night. 'Good riddance,' you chuckle. \n" +
                            "\n" +
                            "Cautiously, the three of you make it back to town, stopping in the still-busy market to recoup. 'Thank you so much for putting your life on the \n" +
                            "line for us,' Lorena says to Wesdru emphatically, a hand on her shoulder. 'You both would do the same for me. Besides, I could never, in good \n" +
                            "conscience, see the two Sanchezes seperated.' The comment warms your heart, your arm around Lorena's shoulders giving a little squeeze. \n" +
                            "\n" +
                            "You say your goodbyes, and return peacefully to your home with your wife. ";
                }
            default:
                text[0] = "ERROR";
                text[1] = "good";
        }
        return text;
    }
}
