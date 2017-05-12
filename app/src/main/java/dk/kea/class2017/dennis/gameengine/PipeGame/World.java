package dk.kea.class2017.dennis.gameengine.PipeGame;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dk.kea.class2017.dennis.gameengine.GameEngine;
import dk.kea.class2017.dennis.gameengine.TouchEvent;

/**
 * Created by Dennis on 26/04/2017.
 */

public class World
{
    public static final float MIN_X = 0;
    public static final float MAX_X = 1279;
    public static final float MIN_Y = 0;
    public static final float MAX_Y = 479;

    List<Pipe> pipes = new ArrayList<>();
    List<Monster> monsters = new ArrayList<>();
    boolean goingUp = false;
    boolean goingLeft = false;
    boolean gameOver = false;
    boolean gameCompleted = false;
    ScrollingBackground background;
    int screenWidth;

    GameEngine game;

    public World(GameEngine game)
    {
        this.game = game;
        background = new ScrollingBackground();
        screenWidth = game.getFrameBufferWidth();
    }

    public void update(float deltaTime)
    {
        if(pipes.size() == 0)
        {
//            pipes.add(new PipeHorizontal(303, (int) Math.ceil(Math.random() * 457)));
            pipes.add(new PipeHorizontal(1265, 0));
            pipes.add(new PipeHorizontal(1265, 23));
            pipes.add(new PipeHorizontal(1265, 46));
            pipes.add(new PipeHorizontal(1265, 69));
            pipes.add(new PipeHorizontal(1265, 92));
            pipes.add(new PipeHorizontal(1265, 115));
            pipes.add(new PipeHorizontal(1265, 138));
            pipes.add(new PipeHorizontal(1265, 161));
            pipes.add(new PipeHorizontal(1265, 184));
            pipes.add(new PipeHorizontal(1265, 207));
            pipes.add(new PipeHorizontal(1265, 230));
            pipes.add(new PipeHorizontal(1265, 253));
            pipes.add(new PipeHorizontal(1265, 276));
            pipes.add(new PipeHorizontal(1265, 299));
            pipes.add(new PipeHorizontal(1265, 322));
            pipes.add(new PipeHorizontal(1265, 345));
            pipes.add(new PipeHorizontal(1265, 368));
            pipes.add(new PipeHorizontal(1265, 391));
            pipes.add(new PipeHorizontal(1265, 414));
            pipes.add(new PipeHorizontal(1265, 437));
            pipes.add(new PipeHorizontal(1265, 460));
            pipes.add(new PipeHorizontal(0, 240));
        }
        if(monsters.size() == 0) generateMonsters();

        List<TouchEvent> events = game.getTouchEvents();
        for(int i = 0; i < events.size(); i++)
        {
            if(events.get(i).type == TouchEvent.TouchEventType.Up)
            {
                int lastPipeIndex = pipes.size() - 1;
                Pipe lastPipe = pipes.get(lastPipeIndex);
                if(events.get(i).y < lastPipe.y)//if clicking above the pipe
                {
                    if(lastPipe instanceof PipeVertical && goingUp)
                    {
                        pipes.add(new PipeVertical(lastPipe.x, lastPipe.y - 32));
                        checkForCollision();
                        return;
                    }
                    else if(lastPipe instanceof PipeVertical && !goingUp)
                    {
                        if(events.get(i).x < lastPipe.x)
                        {
                            turningLeft(lastPipeIndex, lastPipe);
                        }
                        else
                        {
                            turningRight(lastPipeIndex, lastPipe);
                        }
                    }
                    else
                    {
                        turningUp(lastPipeIndex, lastPipe);
                    }
                }
                if(events.get(i).y > (lastPipe.y + lastPipe.getHEIGHT())) //if clicking below the pipe
                {
                    if(lastPipe instanceof PipeVertical && !goingUp)
                    {
                        pipes.add(new PipeVertical(lastPipe.x, lastPipe.y + 32));
                        checkForCollision();
                        return;
                    }
                    else if(lastPipe instanceof PipeVertical && goingUp)
                    {
                        if(events.get(i).x < lastPipe.x)
                        {
                            turningLeft(lastPipeIndex, lastPipe);
                        }
                        else
                        {
                            turningRight(lastPipeIndex, lastPipe);
                        }
                    }
                    else
                    {
                       turningDown(lastPipeIndex, lastPipe);
                    }
                }
                if(events.get(i).x > lastPipe.x && (events.get(i).y >= lastPipe.y &&
                                events.get(i).y < lastPipe.y + lastPipe.getHEIGHT()))//if clicking right of pipe
                {
                    if(lastPipe instanceof PipeHorizontal && !goingLeft)
                    {
                        pipes.add(new PipeHorizontal(lastPipe.x + lastPipe.getWIDTH(), lastPipe.y));
                        checkForCollision();
                        return;
                    }
                    else if(lastPipe instanceof PipeHorizontal && goingLeft)
                    {
                        if(lastPipe.y > game.getFrameBufferHeight()/2)
                        {
                            turningUp(lastPipeIndex, lastPipe);
                        }
                        else
                        {
                            turningDown(lastPipeIndex, lastPipe);
                        }
                    }
                    else
                    {
                        turningRight(lastPipeIndex, lastPipe);
                    }
                }
                if(events.get(i).x < lastPipe.x && (events.get(i).y >= lastPipe.y &&
                                events.get(i).y < lastPipe.y + lastPipe.getHEIGHT())) //if clicking left of pipe
                {
                    if(lastPipe instanceof PipeHorizontal && goingLeft)
                    {
                        pipes.add(new PipeHorizontal(lastPipe.x - lastPipe.getWIDTH(), lastPipe.y));
                        checkForCollision();
                        return;
                    }
                    else if(lastPipe instanceof PipeHorizontal && !goingLeft)
                    {
                        if(lastPipe.y > game.getFrameBufferHeight()/2)
                        {
                            turningUp(lastPipeIndex, lastPipe);
                        }
                        else
                        {
                            turningDown(lastPipeIndex, lastPipe);
                        }
                    }
                    else
                    {
                        turningLeft(lastPipeIndex, lastPipe);
                    }
                }
            }
        }
        //Moving through the level
        Pipe endPipe = pipes.get(pipes.size() - 1);
        if(endPipe.x > screenWidth/3 && background.scrollx < (MAX_X - screenWidth))
        {
            background.scrollx = background.scrollx + endPipe.getWIDTH() * 2*deltaTime;
            for(Pipe p : pipes)
            {
                p.x = p.x - endPipe.getWIDTH() * 2*deltaTime;
            }
            for(Monster m : monsters)
            {
                m.x = m.x - endPipe.getWIDTH() * 2*deltaTime;
            }
        }
    }

    public void turningUp(int lastPipeIndex, Pipe lastPipe)
    {
        int lastPipex = (int)lastPipe.x;
        int lastPipey = (int)lastPipe.y;
        pipes.remove(lastPipeIndex);
        if(goingLeft)
        {
            pipes.add(lastPipeIndex, new PipeCurveNE(lastPipex + 6, lastPipey - 5));
            lastPipe = pipes.get(lastPipeIndex);
            lastPipex = (int)lastPipe.x;
            lastPipey = (int)lastPipe.y;
            pipes.add(new PipeVertical(lastPipex + lastPipe.getWIDTH() - 26, lastPipey - 32));
        }
        else
        {
            pipes.add(lastPipeIndex, new PipeCurveNW(lastPipex, lastPipey - 5));
            lastPipe = pipes.get(lastPipeIndex);
            lastPipex = (int)lastPipe.x;
            lastPipey = (int)lastPipe.y;
            pipes.add(new PipeVertical(lastPipex + lastPipe.getWIDTH() - 22, lastPipey - 32));
        }
        checkForCollision();
        goingUp = true;
        return;
    }

    public void turningDown(int lastPipeIndex, Pipe lastPipe)
    {
        int lastPipex = (int)lastPipe.x;
        int lastPipey = (int)lastPipe.y;
        pipes.remove(lastPipeIndex);
        if(goingLeft)
        {
            pipes.add(lastPipeIndex, new PipeCurveSE(lastPipex + 5, lastPipey));
            lastPipe = pipes.get(lastPipeIndex);
            lastPipex = (int)lastPipe.x;
            lastPipey = (int)lastPipe.y;
            pipes.add(new PipeVertical(lastPipex + lastPipe.getWIDTH() - 26, lastPipey + lastPipe.getHEIGHT()));
        }
        else
        {
            pipes.add(lastPipeIndex, new PipeCurveSW(lastPipex, lastPipey));
            lastPipe = pipes.get(lastPipeIndex);
            lastPipex = (int)lastPipe.x;
            lastPipey = (int)lastPipe.y;
            pipes.add(new PipeVertical(lastPipex + lastPipe.getWIDTH() - 22, lastPipey + lastPipe.getHEIGHT()));
        }
        checkForCollision();
        goingUp = false;
        return;
    }

    public void turningRight(int lastPipeIndex, Pipe lastPipe)
    {
        if(goingUp)
        {
            int lastPipex = (int)lastPipe.x;
            int lastPipey = (int)lastPipe.y;
            pipes.remove(lastPipeIndex);
            pipes.add(lastPipeIndex, new PipeCurveSE(lastPipex, lastPipey + 6));
            lastPipe = pipes.get(lastPipeIndex);
            lastPipex = (int)lastPipe.x;
            lastPipey = (int)lastPipe.y;
            pipes.add(new PipeHorizontal(lastPipex + lastPipe.getWIDTH(), lastPipey));
        }
        else
        {
            int lastPipex = (int)lastPipe.x;
            int lastPipey = (int)lastPipe.y;
            pipes.remove(lastPipeIndex);
            pipes.add(lastPipeIndex, new PipeCurveNE(lastPipex, lastPipey));
            lastPipe = pipes.get(lastPipeIndex);
            lastPipex = (int)lastPipe.x;
            lastPipey = (int)lastPipe.y;
            pipes.add(new PipeHorizontal(lastPipex + lastPipe.getWIDTH(), lastPipey + 5));
        }
        checkForCollision();
        goingLeft = false;

    }

    public void turningLeft(int lastPipeIndex, Pipe lastPipe)
    {
        if(goingUp)
        {
            int lastPipex = (int)lastPipe.x;
            int lastPipey = (int)lastPipe.y;
            pipes.remove(lastPipeIndex);
            pipes.add(lastPipeIndex, new PipeCurveSW(lastPipex - 5, lastPipey + 6));
            lastPipe = pipes.get(lastPipeIndex);
            lastPipex = (int)lastPipe.x;
            lastPipey = (int)lastPipe.y;
            pipes.add(new PipeHorizontal(lastPipex - lastPipe.getWIDTH() - 6, lastPipey));
        }
        else
        {
            int lastPipex = (int)lastPipe.x;
            int lastPipey = (int)lastPipe.y;
            pipes.remove(lastPipeIndex);
            pipes.add(lastPipeIndex, new PipeCurveNW(lastPipex - 5, lastPipey));
            lastPipe = pipes.get(lastPipeIndex);
            lastPipex = (int)lastPipe.x;
            lastPipey = (int)lastPipe.y;
            pipes.add(new PipeHorizontal(lastPipex - lastPipe.getWIDTH() - 6, lastPipey + 5));
        }
        checkForCollision();
        goingLeft = true;

    }

    public void checkForCollision()
    {
        Pipe pipe = null;
        Monster monster = null;
        int i;
        Pipe lastPipe = pipes.get(pipes.size() - 1);
        for(i = 0; i < 22; i++)
        {
            pipe = pipes.get(i);
            //check for collision with end-of-level pipes
            if(pipe.x < (lastPipe.x + lastPipe.getWIDTH()) && (pipe.x + pipe.getWIDTH()) > lastPipe.x
                    && (pipe.y + pipe.getHEIGHT()) > lastPipe.y && pipe.y < (lastPipe.y + lastPipe.getHEIGHT()))
            {
                gameCompleted = true;
                return;
            }
        }
        for(i = 22; i < (pipes.size() - 1); i++)
        {
            pipe = pipes.get(i);
            //check for collision with other pipes
            if(pipe.x < (lastPipe.x + lastPipe.getWIDTH()) && (pipe.x + pipe.getWIDTH()) > lastPipe.x
                    && (pipe.y + pipe.getHEIGHT()) > lastPipe.y && pipe.y < (lastPipe.y + lastPipe.getHEIGHT()))
            {
                gameOver = true;
                return;
            }
        }
        //check for collision with sides
        if(lastPipe.x < 0 || lastPipe.x + lastPipe.getWIDTH() > MAX_X
            || lastPipe.y < 0 || lastPipe.y + lastPipe.getHEIGHT() > game.getFrameBufferHeight())
        {
            Log.d("CollisionCheck", "sideCollision********************");
            gameOver = true;
            return;
        }
        //check for collision with monsters
        for(int j = 0; j < (monsters.size() - 1); j++)
        {
            monster = monsters.get(j);
            if(monster.x < (lastPipe.x + lastPipe.getWIDTH()) && (monster.x + monster.WIDTH) > lastPipe.x
                    && (monster.y + monster.HEIGHT) > lastPipe.y && monster.y < (lastPipe.y + lastPipe.getHEIGHT()))
            {
                gameOver = true;
                return;
            }
        }
    }

    public void generateMonsters()
    {
        for(int i = 0; i < 20; i++)
        {
            monsters.add(new Monster((int)Math.ceil(Math.random() * 1260), (int)Math.ceil(Math.random() * 480)));
        }
    }
}