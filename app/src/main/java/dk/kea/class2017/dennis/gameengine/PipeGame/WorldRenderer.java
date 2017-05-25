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
    int screenWidth;
    int screenHeight;
    Bitmap pipeImage;
    Bitmap scrBackImage;
    Bitmap monsterImage;
    int pipeSize = 0;
    int monsterSize = 0;
    Pipe pipe = null;
    Monster monster = null;


    public WorldRenderer(GameEngine game, World world)
    {
        this.game = game;
        this.world = world;
        pipeImage = game.loadBitmap("plumspipes.png");
        scrBackImage = game.loadBitmap("gloomybackground.jpg");
        monsterImage = game.loadBitmap("greenyellowmonster.png");
        screenWidth = game.getFrameBufferWidth();
        screenHeight = game.getFrameBufferHeight();
    }

    public void render()
    {
        game.drawBitmap(scrBackImage, 0, 0, (int)world.background.scrollx,
                0, screenWidth, screenHeight);

        pipeSize = world.pipes.size();
        for(int i = 0; i < pipeSize; i++)
        {
            pipe = world.pipes.get(i);
            game.drawBitmap(pipeImage, (int)pipe.x, (int)pipe.y,
                    pipe.getSpritex(), pipe.getSpritey(), (int)pipe.getWIDTH(), (int)pipe.getHEIGHT());
        }

        monsterSize = world.monsters.size();
        for(int i = 0; i < monsterSize; i++)
        {
            monster = world.monsters.get(i);
            game.drawBitmap(monsterImage, (int)monster.x, (int)monster.y);
        }

    }
}
