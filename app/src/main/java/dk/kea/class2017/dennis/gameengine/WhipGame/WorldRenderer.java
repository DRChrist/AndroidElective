package dk.kea.class2017.dennis.gameengine.WhipGame;

import android.graphics.Bitmap;

import dk.kea.class2017.dennis.gameengine.GameEngine;

/**
 * Created by Dennis on 25/04/2017.
 */

public class WorldRenderer
{
    GameEngine game;
    World world;
    Bitmap lineImage;

    public WorldRenderer(GameEngine game, World world)
    {
        this.game = game;
        this.world = world;
        lineImage = game.loadBitmap("whipwhip.png");
    }

    public void render()
    {
        game.drawBitmap(lineImage, world.line.x, world.line.y,
                world.line.spritex, world.line.spritey,
                world.line.WIDTH, world.line.HEIGHT);
    }
}
