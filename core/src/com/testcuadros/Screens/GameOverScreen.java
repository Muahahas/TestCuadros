package com.testcuadros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.testcuadros.MainGame;

/**
 * @Authoreloi
 * @Date 13/11/2015.
 */
public class GameOverScreen implements Screen {

    private Stage stage;
    final MainGame game;
    final Skin skin;

    public GameOverScreen(final MainGame game) {
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.game = game;
        this.stage = new Stage();

        Label label = new Label("Game OVER",skin);
        label.setPosition(0,0);

        stage.addActor(label);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if(Gdx.input.isTouched()){
            game.setScreen(new MainMenuScreen(game));
        }

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);

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
        stage.dispose();

    }
}
