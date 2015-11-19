package com.testcuadros.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * @Author eloi
 * @Date 17/11/2015.
 */
public class ZoomGestureDetector implements GestureDetector.GestureListener {
    OrthographicCamera camera;
    float lastRatioMax;
    float lastRatioMin;

    public ZoomGestureDetector(OrthographicCamera camera) {
        this.lastRatioMax = 1;
        this.lastRatioMin = 1;
        this.camera = camera;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (deltaX > 0) {
            if (camera.viewportWidth / 5 < camera.position.x)
                camera.translate(-deltaX * Gdx.graphics.getDeltaTime()*50, 0);
        } else {
            if (camera.position.x < (camera.viewportWidth / 5) * 4) {
                camera.translate(-deltaX*Gdx.graphics.getDeltaTime()*50, 0);
            }
        }
        if (deltaY > 0) {
            if (camera.position.y < (camera.viewportHeight/5) * 4) {
                camera.translate(0, deltaY*Gdx.graphics.getDeltaTime()*50);
            }
        } else {
            if (camera.viewportHeight / 5 < camera.position.y) {
                camera.translate(0, deltaY*Gdx.graphics.getDeltaTime()*50);
            }
        }
        camera.update();


        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        float ratio = initialDistance / distance;
        if (ratio < 1 && camera.zoom > 0.2 && ratio < lastRatioMax) {
            this.camera.zoom -= (lastRatioMax - ratio) * 5;
            lastRatioMax = ratio;
            lastRatioMin = 1;
        } else if (ratio > 1 && camera.zoom < 1.2 && ratio > lastRatioMin) {
            this.camera.zoom += (ratio - lastRatioMin) * 5;
            lastRatioMax = 1;
            lastRatioMin = ratio;
        }

        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
