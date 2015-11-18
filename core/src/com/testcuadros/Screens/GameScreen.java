package com.testcuadros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
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
import com.testcuadros.Graphics.ZoomGestureDetector;
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
    private Stage stageHud;
    private Stage stageBckgrnd;
    final MainGame game;
    final Skin skin;
    public int linesTouched;
    public Table hud;
    Image imgFondo;

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
        this.stageBckgrnd= new Stage();

        imgFondo = new Image(new Texture("stars.png"));
        imgFondo.setFillParent(true);
        stageBckgrnd.addActor(imgFondo);

        createHud();
        createSquares();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stageHud);
        multiplexer.addProcessor(stage);

        ZoomGestureDetector gestureD = new ZoomGestureDetector((OrthographicCamera) stage.getCamera());
        multiplexer.addProcessor(new GestureDetector(gestureD));

        Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stageBckgrnd.draw();
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
        stageHud.draw();

        if (linesTouched >= max * (max + 1) * 2) {
            game.setScreen(new GameOverScreen(this.game, pointsP1, pointsP2, max));
        }
    }

    @Override
    public void resize(int width, int height) {
        stageBckgrnd.getViewport().update(width,height);
        stage.getViewport().update(width, height);
        stageHud.getViewport().update(width,height);
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
        stageBckgrnd.dispose();
        stage.dispose();
        stageHud.dispose();
    }

    private void createHud() {
        stageHud = new Stage();

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
        stageHud.addActor(hud);
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
        float initialposX = squareX, initialposY = squareY;
        lineaB.setSize(size * 0.9f, size * 0.2f);
        lineaB.setPosition(initialposX + 0.05f*size, initialposY - lineaB.getHeight() / 2);

        stage.addActor(lineaB);
        lineaB.addMyListener();

        return lineaB;
    }

    private ActorLinea createLline(float squareX, float squareY) {
        final ActorLinea lineaL = new ActorLinea(this);
        int initialPosX = (int) squareX, initialPosY = (int) squareY;

        lineaL.setRotation(90);
        lineaL.setSize(size * 0.9f, size * 0.2f);
        lineaL.setPosition(initialPosX + lineaL.getHeight() / 2, initialPosY + 0.05f * size);

        stage.addActor(lineaL);

        lineaL.addMyListener();
        return lineaL;

    }

    private ActorLinea createRline(float squareX, float squareY) {

        final ActorLinea lineaR = new ActorLinea(this);
        int initialposX = (int) squareX, initialposY = (int) squareY;


        lineaR.setRotation(90);
        lineaR.setSize(size * 0.9f, size * 0.2f);
        lineaR.setPosition(initialposX + size + lineaR.getHeight() / 2, initialposY + 0.05f*size);

        stage.addActor(lineaR);

        lineaR.addMyListener();
        return lineaR;
    }

    private ActorLinea createTline(float squareX, float squareY) {
        final ActorLinea lineaT = new ActorLinea(this);
        int initialposX = (int) squareX, initialposY = (int) squareY;

        lineaT.setSize(size * 0.9f, size * 0.2f);
        lineaT.setPosition(initialposX + 0.05f*size, initialposY + size - lineaT.getHeight() / 2);
        stage.addActor(lineaT);

        lineaT.addMyListener();

        return lineaT;
    }
}
