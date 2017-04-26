package dk.kea.class2017.dennis.gameengine.PipeGame;

import android.graphics.Bitmap;

import java.util.List;

import dk.kea.class2017.dennis.gameengine.GameEngine;
import dk.kea.class2017.dennis.gameengine.Screen;
import dk.kea.class2017.dennis.gameengine.TouchEvent;

/**
 * Created by Dennis on 26/04/2017.
 */

public class MainMenuScreen extends Screen
{
    Bitmap mainmenuBackground;
    Bitmap startGame;
    float passedTime = 0;

    public MainMenuScreen(GameEngine game)
    {
        super(game);
        mainmenuBackground = game.loadBitmap("gloomybackground.jpg");
        startGame = game.loadBitmap("whipstartgame.png");
    }

    @Override
    public void update(float deltaTime)
    {
        List<TouchEvent> events = game.getTouchEvents();
        for(int i = 0; i < events.size(); i++)
        {
            if(events.get(i).type == TouchEvent.TouchEventType.Up)
            {
                game.setScreen(new GameScreen(game));
                return;
            }
        }

        game.drawBitmap(mainmenuBackground, 0, 0);

        passedTime += deltaTime;
        if((passedTime - (int)passedTime) > 0.5f)
        {
            game.drawBitmap(startGame, 160 - startGame.getWidth()/2, 320);
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
