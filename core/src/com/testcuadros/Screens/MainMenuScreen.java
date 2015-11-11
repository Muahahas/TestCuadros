package com.testcuadros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.testcuadros.MainGame;

/**
 * Created by eloi on 10/11/2015.
 */
public class MainMenuScreen implements Screen {

    final MainGame game;
    private final float startTime;
    OrthographicCamera camera;

    public MainMenuScreen(final MainGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MainGame.width, MainGame.height);

        startTime = System.nanoTime();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GlyphLayout glyphLayout = new GlyphLayout();
        String welcome_str = "Welcome to nain menu!!! ";
        String tapAnywhere_str = "Tap anywhere to begin!";
        glyphLayout.setText(game.font, welcome_str);
        int str1_size = (int) glyphLayout.width;
        glyphLayout.setText(game.font, tapAnywhere_str);
        int str2_size = (int) glyphLayout.width;


        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, welcome_str, MainGame.width / 2 - str1_size / 2, 250);
        game.font.draw(game.batch, tapAnywhere_str, MainGame.width / 2 - str2_size / 2, 230);
        game.batch.end();

        if (Gdx.input.isTouched() && (System.nanoTime() - startTime) / 1000000000f > 2f) {
            game.setScreen(new GameScreen(game));
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
