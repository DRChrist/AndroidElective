package dk.kea.class2017.dennis.gameengine.Breakout;

import android.graphics.Bitmap;

import dk.kea.class2017.dennis.gameengine.GameEngine;
import dk.kea.class2017.dennis.gameengine.Music;
import dk.kea.class2017.dennis.gameengine.Screen;

/**
 * Created by Dennis on 14/03/2017.
 */

public class MainMenuScreen extends Screen
{
    Bitmap mainmenuBackground;
    Bitmap insertCoin;
    Music music;
    long startTime = System.nanoTime();
    float passedTime = 0;

    public MainMenuScreen(GameEngine game)
    {
        super(game);
        mainmenuBackground = game.loadBitmap("mainmenu.png");
        insertCoin = game.loadBitmap("insertcoin.png");
        music = game.loadMusic("music.ogg");
        music.setLooping(true);
        music.play();
    }


    @Override
    public void update(float deltaTime)
    {
        if(game.isTouchDown(0))
        {
            game.setScreen(new GameScreen(game));
            return; //We don't need this object anymore, this tells the OS that we don't need it anymore
        }

        game.drawBitmap(mainmenuBackground, 0, 0);

        passedTime += deltaTime;
        if((passedTime - (int)passedTime) > 0.5f)
        {
            game.drawBitmap(insertCoin, 160 - insertCoin.getWidth()/2, 320);
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
