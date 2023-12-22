This file will contain documentation for all commands available in your game.

My submission includes an html file which is a twine game including all the dialogue and buttons for the game. 
It's not necessarily pretty since it was just a way to organize the dialogue, but it also serves as a cheatsheet for the 
game logic/plot. Simply download the file, go to twinery.org, click "use in your browser", and under "Library" on the 
task bar, click "import" and select the file. It should open it immediately; you can play it or just look through the 
screens that way. 

Every 'passage' in the twine game is a screen in my java game; if the text is dictated by your game 
'file' (boolean variables) then you'll see them written (ex: "!Wesdru & SR:") before the appropriate text. 
There are a few exceptions to this rule; Separate 'passages' that are implemented as one class because making them separate 
passages makes the story much easier to understand in the twine game. 
They are:
- Hallway, Hallway2, and Hallway3
- Some of the END Screens
- Broadcast, Broadcast2

This seems to be a good summary of how the tone of the game changes:
!Wesdru (you chose "Sweet Talk" in the OutsideScreen) && Security Room: alone and evil
Wesdru (you chose "Climb the building" in the OutsideScreen) && Security Room: fun
!Wesdru (you chose "Sweet Talk" in the OutsideScreen) && Mystery Room: mad but badass
Wesdru (you chose "Climb the building" in the OutsideScreen) && Mystery Room: romantic

Other things to note:
*text* is meant to be italics. I didn't manage to implement that.
y/n stands for Your Name, as in [insert your name here]. it's a fanfiction thing :P 
the TBW (To be written) text is a necessary evil considering the game isn't finished. I plan to update it on github.
"//" above some text in the twine game means I 'commented out' this option for now since I didn't have time to implement it. 


# SPOILER ALERT
If your game includes challenges that must be overcome to win, also list them below.

There are a couple different major endings you can get; they're all in the twine file. 
