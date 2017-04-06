package dk.kea.class2017.dennis.gameengine.CarScroller;

import android.graphics.Bitmap;

import dk.kea.class2017.dennis.gameengine.GameEngine;

/**
 * Created by Dennis on 06/04/2017.
 */

public class WorldRenderer
{
    GameEngine game;
    World world;
    int screenWidth;
    int screenHeight;
    Bitmap scrBackImage;
    Bitmap carImage;
    Bitmap monsterImage;


    public WorldRenderer(GameEngine game, World world)
    {
        this.game = game;
        this.world = world;
        screenWidth = game.getFrameBufferWidth();
        screenHeight = game.getFrameBufferHeight();
        scrBackImage = game.loadBitmap("xcarbackground.png");
        carImage = game.loadBitmap("xbluecar2.png");
        monsterImage = game.loadBitmap("xyellowmonster.png");
    }


    public void render()
    {
        game.drawBitmap(scrBackImage, 0, 0, (int)world.scrollingBackground.scrollx,
                0, screenWidth, screenHeight);
        game.drawBitmap(carImage, 30, 160);
    }
}
