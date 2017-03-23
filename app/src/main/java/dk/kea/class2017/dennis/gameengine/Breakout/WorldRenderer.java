package dk.kea.class2017.dennis.gameengine.Breakout;

import android.graphics.Bitmap;

import dk.kea.class2017.dennis.gameengine.GameEngine;

/**
 * Created by Dennis on 16/03/2017.
 */

public class WorldRenderer
{
    GameEngine game;
    World world;
    Bitmap ballImage;
    Bitmap paddleImage;
    Bitmap blocksImage;
    int loopSize = 0;
    Block block = null;

    public WorldRenderer(GameEngine game, World world)
    {
        this.game = game;
        this.world = world;
        ballImage = game.loadBitmap("ball.png");
        paddleImage = game.loadBitmap("paddle.png");
        blocksImage = game.loadBitmap("blocks.png");
    }

    public void render()
    {
        game.drawBitmap(ballImage, (int)world.ball.x, (int)world.ball.y);
        game.drawBitmap(paddleImage, (int)world.paddle.x, (int)world.paddle.y);

        loopSize = world.blocks.size();
        for(int i = 0; i < loopSize; i++)
        {
            block = world.blocks.get(i);
            game.drawBitmap(blocksImage, (int)block.x, (int)block.y,
                    0, (int)(block.type*Block.HEIGHT), (int)Block.WIDTH, (int)Block.HEIGHT);
        }
    }
}
