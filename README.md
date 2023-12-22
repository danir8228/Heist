# CSC120-FinalProject

## Deliverables:
 - Your final codebase 
 - Your revised annotated architecture diagram *
 - Design justification (including a brief discussion of at least one alternative you considered) 
 - A map of your game's layout (if applicable) - twine html file
 - `cheatsheet.md`
 - Completed `rubric.md`

NOTE:
The game is not finished unfortunately... I was trying to add more features to stall from actually writing the game :P but 
it doesn't crash or anything. Just the story is incomplete in a few places. In place of the text, I wrote TBW for "To be Written".

Reflection:
- What was your **overall approach** to tackling this project?
I wanted to use a game library, since it's no use reinventing the wheel by reimplementing functionality to open windows 
and render images but in order to use java I couldn't just use a game engine. I've made a simple runner game in python
using pygame, so I wanted to expand on the knowledge I gained and explore other ideas that I didn't get to implement. 
I just wanted to implement a choose-your-own-adventure, as immersive as I can get it to be. My priority was the functionality,
but I do think I lost the thread a bit at the end; I let myself get distracted by the prospect of shiny new features instead of 
working on the base game whose scope I didn't realize was wider than I was letting myself notice. Biting off more you can 
chew is the classic problem in game dev, but I really think I could've implemented everything if I had been dialed in the past 
week. I'm really disappointed as I wanted you (Johanna and Jordan lol) to get to enjoy the full thing! but nonetheless, I 
worked very hard on what *is* here, and I hope it reflects the huge amount that I have learned in your class! Thanks for being
incredible professors! (PS: I do mean to finish the game over break! I'll come to your office hours next semester and show you :) )

- What **new thing(s)** did you learn / figure out in completing this project?
I'm really proud of everything I learned! I've never used gradle to manage dependencies, and while trying to add some 
functionality I ended up reading through a ton of documentation about maven and other repositories which was incredibly
interesting. I also had a bug for about a week that had to do with threads; I definitely don't really understand what was
wrong or what threads even are but I have a foundational understanding now that I didn't have before, which is the first
step. Next time I encounter a similar problem I have much more experience on which to build, which at the very least will
make me less stressed. I learned about entry points to code, and how complex games usually have a Launcher class. I learned
about the "implements" keyword, which is similar to extends. 

- Is there anything that you wish you had **implemented differently**?
I think potentially there was a better way to implement returnText() so it's not a huge series of if-else statements, but it
does the job! I'd be curious to see how these codebases are usually organized, although I suspect people just use game engines.

- If you had **unlimited time**, what additional features would you implement?
Definitely scrolling text. I also wanted to have a text input box in the main menu screen that would take your name and replace
"y/n" with your actual name. I would make the text box transparent so you can see the image behind it better, and add 
game progress booleans that don't refresh when you start a new run in the same game so your end screen tells you how many
endings you've gotten so far. I don't think I would want to implement going backwards, but I'm sure that would be a fun
challenge.

- What was the most helpful **piece of feedback** you received while working on your project? Who gave it to you?
The most helpful piece of advice I recieved was definitely to create the smallest working version--in my case two screens classes
that create an instance of the other when clicked, bascially toggling between them--to debug existing problems and new features,
slowly increasing the complexity if you can't find the bug immediately (which I did). Jordan suggested it in office hours.
I've heard it before, in fact, done it before, but it's so easy to forget. 

- If you could go back in time and give your past self some **advice** about this project, what hints would you give?
The hardest part will be the writing. You're good at it, but it is not fun in the way that code is; you really have to sit
down and do it, dedicate time to it (not to say it's not fun). Finish a working version a couple days before it's due, so you
can add all the features you want to and feel more accomplished. 

Design Justification:
I couldn't find any clear documentation of best practices for writing visual novel/choose your own adventure style games 
with LibGDX, so I implemented what seemed most intuitive to me. My first attempt was to make a single Screen class and create 
instances at the moment the screen needed to be changed, passing in attributes held in some sort of data structure like 
a graph. The more I thought about it, the more I realized it was probably possible, but it would take me too long to figure
out how to do; I had too many questions. 

Here's a few off the top of my head:
How would I keep the different versions of the same screen? All within one node of the graph? The buttons could be edges, 
yes, but I have no idea how graphs work. What mechanism would manage the screens? or would they also create the next one 
and dispose themselves as in my current implementation, simply looking through the 
graph to find which one to create next? What about changing the game variables? How could I code the logic within the button
listeners in a way that was generic and could be affected with only attributes passed in? (I was considering passing in booleans
for a couple different common options, but there were too many variations I could think of, which would defeat the point of
making one class, which was elegance/efficiency.) 

I decided to follow the strategy used by most of the games I saw: using a separate screen class for each different screen 
in the game, although admittedly they implemented it on smaller scale usually with a Main Menu Screen, a Gameplay Screen,
and an End Screen. I ended up cheating a bit actually by changing the visuals (text and button text, sometimes the background) 
of the screens when you click a button, to fit more of the plot on fewer screens (this is why the music can stay the same!).
This roughly halved the screens needed for the game, although there actually didn't end up being that many to begin with.
The main way in which the plot diverges is not screen-wise, but the different directions the story goes in from the beginning.
I used two additional methods with big if-else chains (returnText() and returnResults() to manage displaying the correct text.
The returnText() returns an array of Strings, with the 0th index being the text to display, and the others being the text to put on the
buttons. returnResults() can simply return a String since the button will always just say "continue".

There are two endScreens, although only one is implemented right now. Basically, endings that are sad and suspenseful will
be sent to BadEndScreen which just has a different color background and more intense music. I wanted the end screen to reflect
the tone of the text. 

I decided to use booleans to keep track of your choices (I've been calling it your 'game file' on all this documentation
although I'm aware that's needlessly confusing; it's kind of too late to change it now.) because it seemed needlessly
complicated to use anything else. Maybe a list of booleans could have made it more streamlined, but that would've been
much less human-readable for really no benefit. 

All the other design elements were inherent to LibGDX games, I think.