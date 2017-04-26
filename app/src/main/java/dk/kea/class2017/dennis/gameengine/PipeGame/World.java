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
    public static final float MAX_X = 319;
    public static final float MIN_Y = 0;
    public static final float MAX_Y = 479;

    List<Pipe> pipes = new ArrayList<>();
    boolean goingUp = false;

    GameEngine game;

    public World(GameEngine game)
    {
        this.game = game;
    }

    public void update(float deltaTime)
    {
        if(pipes.size() == 0)
        {
            pipes.add(new PipeHorizontal(0, 240));
            pipes.add(new PipeHorizontal(32, 240));
        }

        List<TouchEvent> events = game.getTouchEvents();
        for(int i = 0; i < events.size(); i++)
        {
            if(events.get(i).type == TouchEvent.TouchEventType.Up)
            {
                int lastPipeIndex = pipes.size() - 1;
                Pipe lastPipe = pipes.get(lastPipeIndex);
                Log.d("World", "Lastpipeindex = " + lastPipeIndex);
                if(events.get(i).y < lastPipe.y)//if clicking above the pipe
                {
                    if(lastPipe instanceof PipeVertical && goingUp)
                    {
                        pipes.add(new PipeVertical(lastPipe.x, lastPipe.y - 32));
                        return;
                    }
                    else if(lastPipe instanceof PipeVertical && !goingUp)
                    {
                        turningRight(lastPipeIndex, lastPipe);
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
                    }
                    else if(lastPipe instanceof PipeVertical && goingUp)
                    {
                        turningRight(lastPipeIndex, lastPipe);
                    }
                    else
                    {
                       turningDown(lastPipeIndex, lastPipe);
                    }
                }
                if(events.get(i).y == lastPipe.y ||
                        (events.get(i).y > (lastPipe.y) &&
                                events.get(i).y < lastPipe.y + lastPipe.getHEIGHT()))//clicking at same y as pipe
                {
                    if(lastPipe instanceof PipeHorizontal)
                    {
                        pipes.add(new PipeHorizontal(lastPipe.x + lastPipe.getWIDTH(), lastPipe.y));
                    }
                    else
                    {
                        turningRight(lastPipeIndex, lastPipe);
                    }
                }

            }
        }
    }

    public void turningUp(int lastPipeIndex, Pipe lastPipe)
    {
        int lastPipex = (int)lastPipe.x;
        int lastPipey = (int)lastPipe.y;
        pipes.remove(lastPipeIndex);
        pipes.add(lastPipeIndex, new PipeCurveNW(lastPipex, lastPipey - 5));
        lastPipe = pipes.get(lastPipeIndex);
        lastPipex = (int)lastPipe.x;
        lastPipey = (int)lastPipe.y;
        pipes.add(new PipeVertical(lastPipex + lastPipe.getWIDTH() - 22, lastPipey - 32));
        goingUp = true;
        return;
    }

    public void turningDown(int lastPipeIndex, Pipe lastPipe)
    {
        int lastPipex = (int)lastPipe.x;
        int lastPipey = (int)lastPipe.y;
        pipes.remove(lastPipeIndex);
        pipes.add(lastPipeIndex, new PipeCurveSW(lastPipex, lastPipey));
        lastPipe = pipes.get(lastPipeIndex);
        lastPipex = (int)lastPipe.x;
        lastPipey = (int)lastPipe.y;
        pipes.add(new PipeVertical(lastPipex + lastPipe.getWIDTH() - 22, lastPipey + lastPipe.getHEIGHT()));
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
    }
}
