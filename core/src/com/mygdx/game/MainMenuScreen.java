package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final Heist game;

    public MainMenuScreen(final Heist game) {
        this.game = game; //need instance to call on its methods if necessary
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0, 0, 1);

        game.batch.begin();
        game.font.draw(game.batch, "Howdy Cowboy!", 100, 150);
        game.font.draw(game.batch, "Click to start.", 100f, 100f);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new MarketScreen(game));
            dispose();
        }
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

    }

    @Override
    public void dispose() {
    }
}
