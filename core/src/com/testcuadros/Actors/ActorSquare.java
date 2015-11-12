package com.testcuadros.Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

/**
 * @Author eloi
 * @Date 12/11/2015.
 */
public class ActorSquare extends Actor implements Disposable {

    private ShapeRenderer SR;
    private ShapeRenderer SRLines;

    public ActorSquare() {
        SR = new ShapeRenderer();
        SRLines = new ShapeRenderer();
        setSize(50,50);
        setColor(0, 0, 1, 1);
        setPosition(0,0);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        Color color = getColor();
        SR.setProjectionMatrix(batch.getProjectionMatrix());
        SR.setTransformMatrix(batch.getTransformMatrix());
        SR.begin(ShapeRenderer.ShapeType.Filled);
        SR.setColor(color);
        SR.rect(getX(), getY(), getWidth(), getHeight());
        SR.end();
        SR.begin(ShapeRenderer.ShapeType.Line);
        SR.setColor(0, 0, 0, 1);
        SR.line(getX(), getY(),0, getX() + getWidth(), getY(),0,Color.BLACK,Color.BLACK);
        SR.line(getX(), getY(),0, getX(), getY() + getHeight(),0,Color.BLACK,Color.BLACK);
        SR.line(getX() + getWidth(), getY(),0, getX() + getWidth(),getY()+getHeight(),0,Color.BLACK,Color.BLACK);
        SR.line(getX(), getY() + getHeight(), 0, getX() + getWidth(), getY() + getHeight(), 0,Color.BLACK,Color.BLACK);
        SR.setColor(color);
        SR.end();
        batch.begin();

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    @Override
    public void dispose() {
        SR.dispose();
        SRLines.dispose();
    }
}
