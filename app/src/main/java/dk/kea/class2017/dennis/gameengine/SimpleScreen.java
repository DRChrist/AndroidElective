package dk.kea.class2017.dennis.gameengine;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.Random;

/**
 * Created by Dennis on 07/02/2017.
 */

public class SimpleScreen extends Screen
{
    int x = 0;
    int y = 0;
    Bitmap bitmap;

    public SimpleScreen(GameEngine game)
    {
        super(game);
        Log.d("SimpleGame class", "##################################");
        bitmap = game.loadBitmap("bob.png");
    }

    @Override
    public void update(float deltaTime)
    {
//        Log.d("SimpleGame class", "*******************************");
//        if(game.isTouchDown(0))
//        {
//            x = game.getTouchX(0);
//            y = game.getTouchY(0);
//        }
        game.clearFrameBuffer(Color.BLUE);
        game.drawBitmap(bitmap, 10, 10);
        game.drawBitmap(bitmap, 100, 150, 0, 0, 64, 64);
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
