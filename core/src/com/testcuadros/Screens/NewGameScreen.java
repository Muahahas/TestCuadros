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
 * @Date 17/11/2015.
 */
public class NewGameScreen implements Screen {
    private Stage stage;
    final MainGame game;
    final Skin skin;

    public NewGameScreen(final MainGame game) {
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.game = game;
        this.stage = new Stage();
        Image imgFondo = new Image(new Texture("stars.png"));
        imgFondo.setFillParent(true);
        stage.addActor(imgFondo);


        final Button btn3, btn4, btn5, btn9, btnQuit;
        btn3 = new TextButton("3x3", skin);
        btn4 = new TextButton("4x4", skin);
        btn5 = new TextButton("5x5", skin);
        btn9 = new TextButton("9x9", skin);

        btnQuit = new TextButton("Quit", skin);

        final int w = 300, h = 50, sep = 20;
        Table tblLayout = new Table();
        tblLayout.add(btn3).width(w).height(h).space(sep).row();
        tblLayout.add(btn4).width(w).height(h).space(sep).row();
        tblLayout.add(btn5).width(w).height(h).space(sep).row();
        tblLayout.add(btn9).width(w).height(h).space(sep).row();
        tblLayout.add(btnQuit).width(w).height(h).space(sep).row();

        btn3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, 3));
            }
        });
        btn4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, 4));
            }
        });
        btn5.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, 5));
            }
        });
        btn9.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, 9));
            }
        });

        btnQuit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
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
