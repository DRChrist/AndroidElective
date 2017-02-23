package dk.kea.class2017.dennis.gameengine;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;

import java.util.Random;

/**
 * Created by Dennis on 07/02/2017.
 */

public class SimpleScreen extends Screen
{
    int x = 0;
    int y = 0;
    Bitmap bitmap;
    int clearColor = Color.DKGRAY;

    public SimpleScreen(GameEngine game)
    {
        super(game);
        bitmap = game.loadBitmap("bob.png");
    }

    @Override
    public void update(float deltaTime)
    {
        game.clearFrameBuffer(clearColor);

        if(game.isKeyPressed(KeyEvent.KEYCODE_VOLUME_DOWN))
        {
            clearColor = Color.RED;//(int) Math.random() * Integer.MAX_VALUE;
        }
        else
        {
            clearColor = Color.DKGRAY;
        }
//        game.drawBitmap(bitmap, 10, 10);
//        game.drawBitmap(bitmap, 100, 150, 0, 0, 64, 64);
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
