package com.testcuadros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.testcuadros.MainGame;

/**
 * @Author eloi
 * @Date 10/11/2015.
 */
public class MainMenuScreen implements Screen {

    private Stage stage;
    final MainGame game;
    final Skin skin;

    public MainMenuScreen(final MainGame game) {
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.game = game;
        this.stage = new Stage();
        Image imgFondo = new Image(new Texture("stars.png"));
        imgFondo.setFillParent(true);
        stage.addActor(imgFondo);


        final Button btnNewGame, btnLoadGame, btnSettings, btnQuit;
        btnNewGame = new TextButton("New Game", skin);
        btnLoadGame = new TextButton("Load Game", skin);
        btnSettings = new TextButton("Maybe in a long time..", skin);
        btnQuit = new TextButton("Quit", skin);

        final int w = 300, h = 50, sep = 20;
        Table tblLayout = new Table();
        tblLayout.add(btnNewGame).width(w).height(h).space(sep).row();
        tblLayout.add(btnLoadGame).width(w).height(h).space(sep).row();
        tblLayout.add(btnSettings).width(w).height(h).space(sep).row();
        tblLayout.add(btnQuit).width(w).height(h).space(sep).row();

        btnNewGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.dispose();
                game.setScreen(new NewGameScreen(game));
            }
        });

        btnLoadGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.lastGame!=null) {
                    stage.dispose();
                    Gdx.input.setInputProcessor(game.lastGame.multiplexer);
                    game.lastGame.music.play();
                    game.setScreen(game.lastGame);
                }
            }
        });

        btnQuit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.lastGame != null)
                    game.lastGame.dispose();

                stage.dispose();
                Gdx.app.exit();
            }
        });

        tblLayout.setFillParent(true);
        stage.addActor(tblLayout);
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
