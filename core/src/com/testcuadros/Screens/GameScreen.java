package com.testcuadros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.testcuadros.Actors.ActorLinea;
import com.testcuadros.Actors.ActorSquare;
import com.testcuadros.MainGame;


/**
 * @Author eloi
 * @Date 10/11/2015.
 */

public class GameScreen implements Screen {

    int max = 3;
    final int width, height, size, xStart, yStart;

    public boolean turn, changeTurn1, changeTurn2, aux;

    public Label labelPointsP1;
    public Label labelPointsP2;
    public Label labelTurn;
    public int pointsP1;
    public int pointsP2;

    private Stage stage;
    final MainGame game;
    final Skin skin;
    public int linesTouched;
    public Table hud;

    public GameScreen(final MainGame game, int max) {
        this.max = max;
        linesTouched = 0;
        changeTurn1 = false;
        changeTurn2 = false;
        aux = false;
        pointsP1 = 0;
        pointsP2 = 0;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        size = height / (max + 2);
        xStart = (width - (max * size)) / 2;
        yStart = size;
        turn = true;

        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.game = game;
        this.stage = new Stage();
        Image imgFondo = new Image(new Texture("stars.png"));
        imgFondo.setFillParent(true);
        stage.addActor(imgFondo);

        createHud();
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
        if (changeTurn1) {
            if (aux) {
                if (changeTurn2) {
                    changeTurn1 = false;
                    changeTurn2 = false;
                    aux = false;
                } else {
                    if (turn)
                        labelTurn.setText("Turn Player 2");
                    else
                        labelTurn.setText("Turn Player 1");
                    turn = !turn;
                    changeTurn1 = false;
                    changeTurn1 = false;
                    aux = false;
                }
            } else {
                aux = true;
            }
        }
        stage.draw();

        if (linesTouched >= max * (max + 1) * 2) {
            game.setScreen(new GameOverScreen(this.game, pointsP1, pointsP2, max));
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

    private void createHud() {

        float cellWidth = width / 6;
        Button btnExit = new TextButton("Exit", skin);
        Label plyr1 = new Label("PLayer 1", skin);
        Label plyr2 = new Label("PLayer 2", skin);
        labelTurn = new Label("Turn Player1", skin);
        labelPointsP1 = new Label("0", skin);
        labelPointsP2 = new Label("0", skin);
        hud = new Table();
        hud.top().setFillParent(true);
        hud.add(plyr1).width(cellWidth).left();
        hud.add(labelPointsP1).width(cellWidth);
        hud.add(plyr2).width(cellWidth);
        hud.add(labelPointsP2).width(cellWidth);
        hud.add(labelTurn).width(cellWidth + cellWidth / 2);
        hud.add(btnExit).right().width(cellWidth / 2);
        hud.setFillParent(true);
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(hud);
    }

    private void createSquares() {
        ActorSquare[][] squares = new ActorSquare[max][max];
        ActorLinea[][][] lines = new ActorLinea[max][max + 1][2];
        for (int y = 0; y < max; y++) {
            for (int x = 0; x < max; x++) {
                final ActorSquare square = new ActorSquare(this);
                square.setPosition(xStart + size * x, yStart + size * y);
                square.setSize(size, size);
                stage.addActor(square);
                squares[x][y] = square;
                lines[x][y][0] = createLline(square.getX(), square.getY());
                lines[x][y][1] = createBline(square.getX(), square.getY());
                if (x == max - 1) {
                    lines[x][max][0] = createRline(square.getX(), square.getY());
                    square.lines[3] = lines[x][max][0];
                }
                if (y == max - 1) {
                    lines[x][max][1] = createTline(square.getX(), square.getY());
                    square.lines[2] = lines[x][max][1];
                }
                if (x != 0) {
                    squares[x - 1][y].lines[3] = lines[x][y][0];
                }
                if (y != 0) {
                    squares[x][y - 1].lines[2] = lines[x][y][1];
                }
                square.lines[0] = lines[x][y][1];
                square.lines[1] = lines[x][y][0];


            }
        }
    }

    private ActorLinea createBline(float squareX, float squareY) {
        final ActorLinea lineaB = new ActorLinea(this);
        int initialposX = (int) squareX, initialposY = (int) squareY;

        lineaB.setPosition(initialposX + lineaB.getHeight() / 2, initialposY - lineaB.getHeight() / 2);
        lineaB.setSize(size - lineaB.getHeight(), lineaB.getHeight());

        stage.addActor(lineaB);

        lineaB.addMyListener();

        return lineaB;
    }

    private ActorLinea createLline(float squareX, float squareY) {
        final ActorLinea lineaL = new ActorLinea(this);
        int initialposX = (int) squareX, initialposY = (int) squareY;

        lineaL.setRotation(90);
        lineaL.setPosition(initialposX + lineaL.getHeight() / 2, initialposY + lineaL.getHeight() / 2);
        lineaL.setSize(size - lineaL.getHeight(), lineaL.getHeight());

        stage.addActor(lineaL);

        lineaL.addMyListener();
        return lineaL;

    }

    private ActorLinea createRline(float squareX, float squareY) {

        final ActorLinea lineaR = new ActorLinea(this);
        int initialposX = (int) squareX, initialposY = (int) squareY;


        lineaR.setRotation(90);
        lineaR.setPosition(initialposX + size + lineaR.getHeight() / 2, initialposY + lineaR.getHeight() / 2);
        lineaR.setSize(size - lineaR.getHeight(), lineaR.getHeight());

        stage.addActor(lineaR);

        lineaR.addMyListener();
        return lineaR;
    }

    private ActorLinea createTline(float squareX, float squareY) {
        final ActorLinea lineaT = new ActorLinea(this);
        int initialposX = (int) squareX, initialposY = (int) squareY;

        lineaT.setPosition(initialposX + lineaT.getHeight() / 2, initialposY + size - lineaT.getHeight() / 2);
        lineaT.setSize(size - lineaT.getHeight(), lineaT.getHeight());
        stage.addActor(lineaT);

        lineaT.addMyListener();

        return lineaT;
    }
}
