package com.mygdx.game;

public class ScrollingText {
    private String textToDisplay;
    private float speed;
    private int displayUpToThisIndex;
    private float accumulator;


    public ScrollingText (String textToDisplay, float speed) {
        this.textToDisplay = textToDisplay;
        this.speed = speed;
        this.displayUpToThisIndex = 0;
        this.accumulator = 0;

    }

    public void update(float delta) {
        if (displayUpToThisIndex == textToDisplay.length() - 1) {
            return;
        }
        accumulator += delta;
        if (accumulator > speed) {
            displayUpToThisIndex++;
            accumulator = 0;
        }
    }

    public String getTextToDisplay() {
        return this.textToDisplay.substring(0, displayUpToThisIndex);
    }
}
