package dk.kea.class2017.dennis.gameengine.WhipGame;

import android.graphics.Bitmap;

import dk.kea.class2017.dennis.gameengine.GameEngine;
import dk.kea.class2017.dennis.gameengine.Screen;

/**
 * Created by Dennis on 25/04/2017.
 */

public class MainMenuScreen extends Screen
{
    Bitmap background;
    Bitmap startGame;
    float passedTime = 0;

    public MainMenuScreen(GameEngine game)
    {
        super(game);
        background = game.loadBitmap("whipmainmenu.png");
        startGame = game.loadBitmap("whipstartgame.png");
    }

    @Override
    public void update(float deltaTime)
    {
        if(game.isTouchDown(0))
        {
            game.setScreen(new GameScreen(game));
            return;
        }

        game.drawBitmap(background, 0, 0);

        game.drawBitmap(startGame, 240 - startGame.getWidth(), 160 - startGame.getHeight()/2);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {

    }
}
