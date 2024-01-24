package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.ScreenUtils;
import static com.mygdx.game.Heist.skin;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {

    final Heist game;

    private String textInput;

    private Stage stage;


    public MainMenuScreen(final Heist game) {
        this.game = game; //need instance to call on its methods if necessary
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        TextField textField = new TextField("", skin); // 'skin' is a Skin instance
        textField.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        stage.addActor(textField);

        final StringBuilder enteredText = new StringBuilder(); // Variable to store entered text

        textField.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                System.out.println(enteredText);
                // Check if the Enter key is pressed
                if (character == '\r' || character == '\n') {
                    // Save the entered text when Enter is pressed
                    game.name = enteredText.toString();

                    game.setScreen(new MarketScreen(game));

                    return true;
                }
                else {
                    if (character == '\b' || character == 127) {
                        if (enteredText.length() > 0) {
                            enteredText.setLength(enteredText.length() - 1);
                        }
                    }
                    else {
                        // Append the character to the enteredText variable
                        enteredText.append(character);
                    }
                    return super.keyTyped(event, character);
                }
            }
        });

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0, 0, 1);

        game.batch.begin();
        game.font.draw(game.batch, "Howdy Cowboy!", 100, 150);
        game.font.draw(game.batch, "Enter a name in the text box to start.", 100f, 100f);
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
        stage.dispose();
    }
}
