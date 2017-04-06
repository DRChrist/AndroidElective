package dk.kea.class2017.dennis.gameengine.CarScroller;

import android.graphics.Bitmap;

import dk.kea.class2017.dennis.gameengine.GameEngine;
import dk.kea.class2017.dennis.gameengine.Screen;

/**
 * Created by Dennis on 06/04/2017.
 */

public class MainMenuScreen extends Screen
{
    Bitmap background;
    Bitmap startGame;
    float passedTime = 0;

    public MainMenuScreen(GameEngine game)
    {
        super(game);
        background = game.loadBitmap("xcarbackground.png");
        startGame = game.loadBitmap("xstartgame.png");
    }

    @Override
    public void update(float deltaTime)
    {
        if(game.isTouchDown(0))
        {
            game.music.play();
            game.setScreen(new GameScreen(game));
            return; //We don't need this object anymore, this tells the OS that we don't need it anymore
        }

        game.drawBitmap(background, 0, 0);

        passedTime += deltaTime;
        if((passedTime - (int)passedTime) > 0.5f)
        {
            game.drawBitmap(startGame, 240 - startGame.getWidth()/2, 160 - startGame.getHeight()/2);
        }
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
