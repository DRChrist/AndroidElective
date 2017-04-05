package dk.kea.class2017.dennis.gameengine.Breakout;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;

import java.util.List;

import dk.kea.class2017.dennis.gameengine.GameEngine;
import dk.kea.class2017.dennis.gameengine.Screen;
import dk.kea.class2017.dennis.gameengine.Sound;
import dk.kea.class2017.dennis.gameengine.TouchEvent;

/**
 * Created by Dennis on 16/03/2017.
 */

public class GameScreen extends Screen
{
    enum State
    {
        Paused, Running, GameOver
    }

    State state = State.Paused;
    Bitmap background;
    Bitmap resume;
    Bitmap gameOver;
    Typeface font;
    Sound bounceSound;
    Sound blockSound;
    Sound gameoverSound;
    CollisionListener collisionListener;
    World world;
    WorldRenderer renderer;

    public GameScreen(GameEngine game)
    {
        super(game);
        background = game.loadBitmap("background.png");
        resume = game.loadBitmap("resume.png");
        gameOver = game.loadBitmap("gameover.png");
        font = game.loadFont("font.ttf");
        bounceSound = game.loadSound("bounce.wav");
        blockSound = game.loadSound("blocksplosion.wav");
        gameoverSound = game.loadSound("gameover.wav");
        CollisionListener collisionListener = new MyCollisionListener(bounceSound, bounceSound, blockSound, gameoverSound);
        world = new World(game, collisionListener);
        renderer = new WorldRenderer(game, world);
        game.music.play();
    }

    @Override
    public void update(float deltaTime)
    {
        if(state == State.Paused && game.isTouchDown(0))
        {
            state = State.Running;
            resume();
        }
        if(state == State.GameOver)
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
        } //end of game over check
        if(state == State.Running && game.isTouchDown(0)
                && game.getTouchX(0) > (320-40) && game.getTouchY(0) < 40)
        {
            pause();
        }

        game.drawBitmap(background, 0, 0);

        if(state == State.Running)
        {
            world.update(deltaTime, game.getAccelerometer()[0]);
        }
        game.drawText(font, "Score: " + Integer.toString(world.points), 28, 9, Color.GREEN, 14);
        renderer.render();

        if(world.gameOver) state = State.GameOver;

        if(state == State.Paused)
        {
            game.drawBitmap(resume, 160 - resume.getWidth()/2, 270 - resume.getHeight()/2);
        }
        if(state == State.GameOver)
        {
            game.drawBitmap(gameOver, 160 - gameOver.getWidth()/2, 270 - gameOver.getHeight()/2);
        }

    }

    @Override
    public void pause()
    {
        if(state == State.Running) state = State.Paused;
        game.music.pause();
    }

    @Override
    public void resume()
    {
        game.music.play();
    }

    @Override
    public void dispose()
    {

    }


}
