package com.testcuadros;

import com.badlogic.gdx.Game;
import com.testcuadros.Screens.GameScreen;
import com.testcuadros.Screens.MainMenuScreen;

public class MainGame extends Game {
    public GameScreen lastGame;


    public void create() {
        this.setScreen(new MainMenuScreen(this));
    }


    public void render() {
        super.render();
    }

    public void dispose() {
        super.dispose();
    }
}
