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
                if(events.get(i).y < lastPipe.y)
                {
                    if(lastPipe instanceof PipeVertical)
                    {
                        pipes.add(new PipeVertical(lastPipe.x, lastPipe.y - 32));
                    }
                    else
                    {
                        int lastPipex = (int)lastPipe.x;
                        int lastPipey = (int)lastPipe.y;
                        pipes.remove(lastPipeIndex);
                        pipes.add(lastPipeIndex, new PipeCurveNW(lastPipex, lastPipey));
                        lastPipex = (int)pipes.get(lastPipeIndex).x;
                        lastPipey = (int)pipes.get(lastPipeIndex).y;
                        pipes.add(new PipeVertical(lastPipex - 22, lastPipey));

                    }
                }
                if(events.get(i).y > (lastPipe.y + lastPipe.HEIGHT))
                {
                    if(lastPipe instanceof PipeVertical)
                    {
                        pipes.add(new PipeVertical(lastPipe.x, lastPipe.y + 32));
                    }
                    else
                    {
                        int lastPipex = (int)lastPipe.x;
                        int lastPipey = (int)lastPipe.y;
                        pipes.remove(lastPipeIndex);
                        pipes.add(lastPipeIndex, new PipeCurveSW(lastPipex, lastPipey));
                        lastPipe = pipes.get(lastPipeIndex);
                        lastPipex = (int)lastPipe.x;
                        lastPipey = (int)lastPipe.y;
                        pipes.add(new PipeVertical(lastPipex - 22, lastPipey + lastPipe.HEIGHT));
                    }
                }
                if(events.get(i).y == lastPipe.y ||
                        (events.get(i).y > (lastPipe.y) &&
                                events.get(i).y < lastPipe.y + lastPipe.HEIGHT))
                {
                    pipes.add(new PipeHorizontal(lastPipe.x + lastPipe.WIDTH, lastPipe.y));
                }

            }
        }
    }
}
