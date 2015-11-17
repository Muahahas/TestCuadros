package com.testcuadros.Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;
import com.testcuadros.Screens.GameScreen;

/**
 * @Author eloi
 * @Date 12/11/2015.
 */
public class ActorLinea extends Actor implements Disposable {
    private Texture line;
    private TextureRegion miLine;
    public boolean touched = false;
    private boolean firstTouch=true;
    private GameScreen screen;

    public ActorLinea(GameScreen screen) {
        this.screen = screen;

        line = new Texture("blue-laser.png");
        miLine = new TextureRegion(line);
        setPosition(0, 0);
        setSize(line.getWidth(), line.getHeight());
        setColor(getColor().r, getColor().g, getColor().b, 0);

    }

    @Override
    public void dispose() {
        line.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color col = getColor();
        batch.setColor(col.r, col.g, col.b, col.a * parentAlpha);
        batch.draw(miLine, getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(),
                getRotation());
        setVisible(isVisible());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void addMyListener() {
        this.addCaptureListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Color color = getColor();
                if (firstTouch){
                    touched = true;
                    screen.linesTouched++;
                    if(screen.turn) {
                        addAction(Actions.color(new Color(0, color.g, 0, 1f)));
                        screen.pointsP1++;
                        screen.labelPointsP1.setText(Integer.toString(screen.pointsP1));
                        screen.changeTurn1=true;

                    }else{
                        addAction(Actions.color(new Color(color.r, 0, 0, 1f)));
                        screen.pointsP2++;
                        screen.labelPointsP2.setText(Integer.toString(screen.pointsP2));
                        screen.changeTurn1=true;

                    }
                }
                firstTouch=false;



                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //removeCaptureListener(this);
            }
        });
    }


}
