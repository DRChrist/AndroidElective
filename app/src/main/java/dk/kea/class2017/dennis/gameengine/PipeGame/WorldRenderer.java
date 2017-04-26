package dk.kea.class2017.dennis.gameengine.PipeGame;

import android.graphics.Bitmap;
import android.util.Log;

import dk.kea.class2017.dennis.gameengine.GameEngine;

/**
 * Created by Dennis on 26/04/2017.
 */

public class WorldRenderer
{
    GameEngine game;
    World world;
    Bitmap pipeImage;
    int loopSize = 0;
    Pipe pipe = null;


    public WorldRenderer(GameEngine game, World world)
    {
        this.game = game;
        this.world = world;
        pipeImage = game.loadBitmap("plumspipes.png");
    }

    public void render()
    {
        loopSize = world.pipes.size();
        for(int i = 0; i < loopSize; i++)
        {
            pipe = world.pipes.get(i);
            game.drawBitmap(pipeImage, (int)pipe.x, (int)pipe.y,
                    pipe.getSpritex(), pipe.getSpritey(), (int)pipe.getWIDTH(), (int)pipe.getHEIGHT());
        }
    }
}
