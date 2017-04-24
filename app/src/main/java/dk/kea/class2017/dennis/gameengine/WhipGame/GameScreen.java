package dk.kea.class2017.dennis.gameengine.WhipGame;

import android.graphics.Bitmap;
import android.graphics.Color;

import dk.kea.class2017.dennis.gameengine.GameEngine;
import dk.kea.class2017.dennis.gameengine.Screen;

/**
 * Created by Dennis on 25/04/2017.
 */

public class GameScreen extends Screen
{
    enum State
    {
        Paused, Running, GameOver
    }
    State state = State.Running;
    Bitmap background;
    World world;
    WorldRenderer worldRenderer;

    public GameScreen(GameEngine game)
    {
        super(game);
        background = game.loadBitmap("whipgamebackground.png");
        world = new World(game);
        worldRenderer = new WorldRenderer(game, world);
    }

    @Override
    public void update(float deltaTime)
    {
        game.drawBitmap(background, 0, 0);

        if(state == State.Running)
        {
            world.update(deltaTime);
        }
        worldRenderer.render();
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
