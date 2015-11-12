package com.testcuadros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.testcuadros.Actors.ActorSquare;
import com.testcuadros.MainGame;


/**
 * @Authoreloi
 * @Date 10/11/2015.
 */

public class GameScreen implements Screen {
    private Stage stage;
    final MainGame game;
    final Skin skin;
    private Actor square;

    public GameScreen(final MainGame game) {

        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        square = new ActorSquare();
        final Button btnExit = new TextButton("Exit", skin);

        Table tblLayout = new Table();
        tblLayout.top().debug();
        tblLayout.add(btnExit).pad(10).height(30).row();
        tblLayout.setFillParent(true);
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(tblLayout);

        stage.addActor(square);
        square.addCaptureListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                square.addAction(Actions.color(Color.WHITE, 0.5f));
                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                square.addAction(Actions.color(Color.RED, 1));
            }
        });

        Gdx.input.setInputProcessor(stage);

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

    }
}
