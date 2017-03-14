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
    Bitmap background;
    Bitmap insertCoin;
    Music music;

    public MainMenuScreen(GameEngine game)
    {
        super(game);
        background = game.loadBitmap("mainmenu.png");
        insertCoin = game.loadBitmap("insertcoin.png");
        music = game.loadMusic("music.ogg");
        music.setLooping(true);
        music.play();
    }


    @Override
    public void update(float deltaTime)
    {
        game.drawBitmap(background, 0, 0);
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
