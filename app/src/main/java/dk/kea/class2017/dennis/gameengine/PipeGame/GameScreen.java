package dk.kea.class2017.dennis.gameengine.PipeGame;

import android.graphics.Bitmap;

import java.util.List;

import dk.kea.class2017.dennis.gameengine.GameEngine;
import dk.kea.class2017.dennis.gameengine.Screen;
import dk.kea.class2017.dennis.gameengine.TouchEvent;

/**
 * Created by Dennis on 26/04/2017.
 */

public class GameScreen extends Screen
{
    enum State
    {
        Paused, Running, GameOver, GameCompleted
    }

    State state = State.Running;
    Bitmap background;
    Bitmap gameOver;
    Bitmap gameCompleted;
    World world;
    WorldRenderer renderer;

    public GameScreen(GameEngine game)
    {
        super(game);
        background = game.loadBitmap("gloomybackground.jpg");
        gameOver = game.loadBitmap("gameover.png");
        gameCompleted = game.loadBitmap("gamecompletedbig.png");
        world = new World(game);
        renderer = new WorldRenderer(game, world);
    }

    @Override
    public void update(float deltaTime)
    {

        if(state == State.GameOver || state == State.GameCompleted)
        {
            List<TouchEvent> events = game.getTouchEvents();
            for(int i = 0; i < events.size(); i++)
            {
                if(events.get(i).type == TouchEvent.TouchEventType.Up)
                {
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }

        if(state == State.Running)
        {
            world.update(deltaTime, game.getAccelerometer()[1], game.getAccelerometer()[0]);
        }
        renderer.render();

        if(world.gameOver) state = State.GameOver;
        if(world.gameCompleted) state = State.GameCompleted;

        if(state == State.GameOver)
        {
            game.drawBitmap(gameOver, 160 - gameOver.getWidth()/2, 270 - gameOver.getHeight()/2);
        }

        if(state == State.GameCompleted)
        {
            game.drawBitmap(gameCompleted, 160 - gameCompleted.getWidth()/2, 270 - gameCompleted.getHeight()/2);
        }
    }

    @Override
    public void pause()
    {
        if(state == State.Running) state = State.Paused;
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
