package com.testcuadros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.testcuadros.MainGame;

/**
 * @Author eloi
 * @Date 13/11/2015.
 */
public class GameOverScreen implements Screen {

    private Stage stage;
    final MainGame game;
    final Skin skin;

    public GameOverScreen(final MainGame game, int p1, int p2, final int max) {
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.game = game;
        this.stage = new Stage();
        Image imgFondo = new Image(new Texture("stars.png"));
        imgFondo.setFillParent(true);
        stage.addActor(imgFondo);
        Table tblLayout = new Table();
        Label winnerName;


        Label label = new Label("Game OVER", skin);
        label.setFontScale(2);
        TextButton retry = new TextButton("Retry", skin), menu = new TextButton("Main menu", skin);
        Label pointsWinner;
        Label pointsLoser = new Label("", skin);
        Label loserName = new Label("", skin);

        label.setPosition(0, 0);

        if (p1 > p2) {
            winnerName = new Label("Winner Jugador 1", skin);
            loserName = new Label("2nd Jugador 2", skin);
            pointsWinner = new Label("Points " + Integer.toString(p1), skin);
            pointsLoser = new Label("Points " + Integer.toString(p2), skin);
        } else if (p2 > p1) {
            winnerName = new Label("Winner Jugador 2", skin);
            loserName = new Label("2nd Jugador 1", skin);
            pointsWinner = new Label("Points " + Integer.toString(p2), skin);
            pointsLoser = new Label("Points " + Integer.toString(p1), skin);
        } else {
            winnerName = new Label("Empate", skin);
            pointsWinner = new Label("Points " + Integer.toString(p1), skin);

        }
        winnerName.setPosition(50, 50);
        tblLayout.add(label).colspan(2).row();
        if (p1 != p2) {
            winnerName.setFontScale(1.5f);
            tblLayout.add(winnerName).colspan(2).row();
            pointsWinner.setFontScale(1.4f);
            tblLayout.add(pointsWinner).colspan(2).row();
            tblLayout.add(loserName);
            tblLayout.add(pointsLoser).row();
        } else {
            winnerName.setFontScale(1.5f);
            tblLayout.add(winnerName).colspan(2).row();
            pointsWinner.setFontScale(1.4f);
            tblLayout.add(pointsWinner).colspan(2).row();
        }

        tblLayout.add(retry).padTop(20).height(40);
        tblLayout.add(menu).padTop(20).height(40).row();
        tblLayout.setFillParent(true);
        stage.addActor(tblLayout);
        retry.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.dispose();
                game.setScreen(new GameScreen(game, max));
            }
        });

        menu.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.lastGame != null) {
                    game.lastGame = null;
                }
                stage.dispose();
                game.setScreen(new MainMenuScreen(game));
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
        stage.dispose();

    }
}
