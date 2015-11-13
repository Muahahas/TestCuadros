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
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.testcuadros.Actors.ActorLinea;
import com.testcuadros.Actors.ActorSquare;
import com.testcuadros.MainGame;


/**
 * @Author eloi
 * @Date 10/11/2015.
 */

public class GameScreen implements Screen {

    final int max = 3;
    final int width, height, size, xStart, yStart;

    private Stage stage;
    final MainGame game;
    final Skin skin;
    private ActorSquare[][] squares;
    private ActorLinea[][][] lines;

    public GameScreen(final MainGame game) {

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        size = height / (max + 2);
        xStart = (width - (max * size)) / 2;
        yStart = size;

        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.game = game;
        this.stage = new Stage();

        createTable();
        createSquares();

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
        boolean finished = true;
        for(int x=0;x<max;x++){
            for(int y=0;y<max+1;y++){
                finished =finished && lines[x][y][0].touched && lines[x][y][1].touched;
            }
        }
        if(finished){
            game.setScreen(new GameOverScreen(this.game));
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

    private void createTable() {

        Button btnExit = new TextButton("Exit", skin);
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
    }

    private void createSquares() {
        squares = new ActorSquare[max][max];
        lines = new ActorLinea[max][max+1][2];
        for (int y = 0; y < max; y++) {
            for (int x = 0; x < max; x++) {
                final ActorSquare square = new ActorSquare();
                square.setPosition(xStart + size * x, yStart + size * y);
                square.setSize(size, size);
                stage.addActor(square);
                squares[x][y] = square;
                lines[x][y][0] = createLline(square.getX(), square.getY());
                lines[x][y][1]= createBline(square.getX(), square.getY());
                if (x == max - 1) {
                    lines[x][max][0]=createRline(square.getX(), square.getY());
                }
                if (y == max - 1) {
                    lines[x][max][1]=createTline(square.getX(), square.getY());
                }

            }
        }
    }

    private ActorLinea createBline(float squareX, float squareY) {
        final ActorLinea lineaB = new ActorLinea();
        int initialposX = (int) squareX, initialposY = (int) squareY;

        lineaB.setPosition(initialposX + lineaB.getHeight() / 2, initialposY - lineaB.getHeight() / 2);
        lineaB.setSize(size - lineaB.getHeight(), lineaB.getHeight());

        stage.addActor(lineaB);

        lineaB.addMyListener();


        return lineaB;
    }

    private ActorLinea createLline(float squareX, float squareY) {
        final ActorLinea lineaL = new ActorLinea();
        int initialposX = (int) squareX, initialposY = (int) squareY;

        lineaL.setRotation(90);
        lineaL.setPosition(initialposX + lineaL.getHeight() / 2, initialposY + lineaL.getHeight() / 2);
        lineaL.setSize(size - lineaL.getHeight(), lineaL.getHeight());

        stage.addActor(lineaL);

        lineaL.addMyListener();
        return lineaL;

    }

    private ActorLinea createRline(float squareX, float squareY) {

        final ActorLinea lineaR = new ActorLinea();
        int initialposX = (int) squareX, initialposY = (int) squareY;


        lineaR.setRotation(90);
        lineaR.setPosition(initialposX + size + lineaR.getHeight() / 2, initialposY + lineaR.getHeight() / 2);
        lineaR.setSize(size - lineaR.getHeight(), lineaR.getHeight());

        stage.addActor(lineaR);

        lineaR.addMyListener();
        return lineaR;
    }

    private ActorLinea createTline(float squareX, float squareY) {
        final ActorLinea lineaT = new ActorLinea();
        int initialposX = (int) squareX, initialposY = (int) squareY;

        lineaT.setPosition(initialposX + lineaT.getHeight() / 2, initialposY + size - lineaT.getHeight() / 2);
        lineaT.setSize(size - lineaT.getHeight(), lineaT.getHeight());
        stage.addActor(lineaT);

        lineaT.addMyListener();

        return lineaT;
    }
}
