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
 * @Author eloi
 * @Date 10/11/2015.
 */

public class GameScreen implements Screen {

    final int width, height, size, xStart, yStart;

    private Stage stage;
    final MainGame game;
    final Skin skin;
    private ActorSquare[][] squares;

    public GameScreen(final MainGame game) {

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        size = height/5;
        xStart = (width-(3*size))/2;
        yStart = size;

        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.game = game;
        this.stage = new Stage();

        final Button btnExit = new TextButton("Exit", skin);

        Table tblLayout = new Table();
        tblLayout.top();
        tblLayout.add(btnExit).pad(10).height(30).row();
        tblLayout.setFillParent(true);
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(tblLayout);


        squares = new ActorSquare[3][3];
        for(int y=0;y<3;y++){
            for(int x=0;x<3;x++){
                final ActorSquare square = new ActorSquare();
                square.setPosition(xStart+size*x,yStart+size*y);
                square.setSize(size,size);
                stage.addActor(square);
                square.addCaptureListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        square.addAction(Actions.color(Color.WHITE, 0.3f));
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        square.addAction(Actions.color(Color.RED, 0.3f));
                    }
                });
                squares[x][y] = square;

            }
        }

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
        stage.dispose();
    }
}
