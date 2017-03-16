package dk.kea.class2017.dennis.gameengine.Breakout;

import android.graphics.Bitmap;

import dk.kea.class2017.dennis.gameengine.GameEngine;
import dk.kea.class2017.dennis.gameengine.Screen;

/**
 * Created by Dennis on 16/03/2017.
 */

public class GameScreen extends Screen
{
    enum State
    {
        Paused, Running, GameOver
    }

    State state = State.Running;
    Bitmap background;
    Bitmap resume;
    Bitmap gameOver;
    World world;
    WorldRenderer renderer;

    public GameScreen(GameEngine game)
    {
        super(game);
        background = game.loadBitmap("background.png");
        resume = game.loadBitmap("resume.png");
        gameOver = game.loadBitmap("gameover.png");
        world = new World();
        renderer = new WorldRenderer(game, world);
    }

    @Override
    public void update(float deltaTime)
    {
        if(state == State.Paused && game.isTouchDown(0))
        {
            state = State.Running;
        }
        if(state == State.GameOver && game.isTouchDown(0))
        {
            game.setScreen(new MainMenuScreen(game));
            return;
        }
        if(state == State.Running && game.isTouchDown(0)
                && game.getTouchX(0) > (320-40) && game.getTouchY(0) < 40)
        {
            pause();
        }

        game.drawBitmap(background, 0, 0);

        if(state == State.Running) world.update(deltaTime);
        renderer.render();


        if(state == State.Paused)
        {
            game.drawBitmap(resume, 160 - resume.getWidth()/2, 240 - resume.getHeight()/2);
        }
        if(state == State.GameOver)
        {
            game.drawBitmap(gameOver, 160 - gameOver.getWidth()/2, 240 - gameOver.getHeight()/2);
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
//        if(state == State.Paused) state = State.Running;
    }

    @Override
    public void dispose()
    {

    }


}