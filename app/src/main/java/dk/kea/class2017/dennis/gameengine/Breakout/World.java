package dk.kea.class2017.dennis.gameengine.Breakout;

import java.util.ArrayList;
import java.util.List;

import dk.kea.class2017.dennis.gameengine.GameEngine;

/**
 * Created by Dennis on 16/03/2017.
 */

public class World
{
    public static final float MIN_X = 0;
    public static final float MAX_X = 319;
    public static final float MIN_Y = 40;
    public static final float MAX_Y = 479;

    int points = 0;
    int paddleHits = 0;
    int blockAdvance = 0;
    CollisionListener collisionListener;

    boolean gameOver = false;
    Ball ball = new Ball();
    Paddle paddle = new Paddle();
    List<Block> blocks = new ArrayList<>();

    GameEngine game;

    public World(GameEngine game, CollisionListener collisionListener)
    {
        this.game = game;
        this.collisionListener = collisionListener;
        generateBlocks();
    }

    public void update(float deltaTime, float accelX)//method called from update method in GameScreen
    {
        if(blocks.size() == 0)
        {
            generateBlocks();
            ball.x = 160;
            ball.y = 240;
        }
        ball.x = ball.x + ball.vx * deltaTime;
        ball.y = ball.y + ball.vy * deltaTime;
        if(ball.x < MIN_X)
        {
            ball.vx = -ball.vx;
            ball.x = MIN_X;
            collisionListener.collisionWall();
        }
        if(ball.x > MAX_X - Ball.WIDTH)
        {
            ball.vx = -ball.vx;
            ball.x = MAX_X - Ball.WIDTH;
            collisionListener.collisionWall();
        }
        if(ball.y < MIN_Y)
        {
            ball.vy  = -ball.vy;
            ball.y = MIN_Y;
            collisionListener.collisionWall();
        }
        if(ball.y > MAX_Y - Ball.HEIGHT)
        {
            gameOver = true;
            game.music.stop();
            collisionListener.collisionOutOfScreen();
            return;
        }

        if(game.isTouchDown(0))
        {
            if(game.getTouchY(0) > 410)
            {
                paddle.x = game.getTouchX(0) - Paddle.WIDTH / 2;
            }
        }

        paddle.x = paddle.x - accelX * deltaTime * 100; //the last multiplier is a speed constant
        if(paddle.x < MIN_X) paddle.x = MIN_X;
        if(paddle.x + Paddle.WIDTH > MAX_X) paddle.x = MAX_X - Paddle.WIDTH;

        collideBallPaddle();
        collideBallBlocks(deltaTime);
    }

    private void generateBlocks()
    {
        blocks.clear();
        for(int y = 60, type = 0;y < 60 + 8*(Block.HEIGHT+2); y = y + (int)Block.HEIGHT+2, type++)
        {
            for(int x = 14; x < MAX_X - Block.WIDTH; x = x + (int)Block.WIDTH+2)
            {
                blocks.add(new Block(x, y, type));
            }
        }
    }

    private void collideBallPaddle()
    {
        //check left corner of the paddle
        if(collideRects(ball.x, ball.y+Ball.HEIGHT, Ball.WIDTH, 1, paddle.x, paddle.y, 3, Paddle.HEIGHT/2))
        {
            ball.vy = -ball.vy;
            if(ball.vx > 0) ball.vx = -ball.vx;
            ball.y = paddle.y - Ball.HEIGHT - 1;
            collisionListener.collisionPaddle();
            advanceBlocks();
            return;
        }

        //check right corner of the paddle
        if(collideRects(ball.x, ball.y + Ball.HEIGHT, Ball.WIDTH, 1, paddle.x + Paddle.WIDTH, paddle.y, 3, Paddle.HEIGHT/2))
        {
            ball.vy = -ball.vy;
            if(ball.vx < 0) ball.vx = -ball.vx;
            ball.y = paddle.y - Ball.HEIGHT - 1;
            collisionListener.collisionPaddle();
            advanceBlocks();
            return;
        }

        //check middle part of paddle top edge
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, paddle.x, paddle.y, Paddle.WIDTH, 3))
        {
            ball.vy = -ball.vy;
            ball.y = paddle.y - Ball.HEIGHT - 1;
            collisionListener.collisionPaddle();
            advanceBlocks();
//            return;
        }
    }

    private void advanceBlocks()
    {
        paddleHits++;
        if(paddleHits == 10)
        {
            paddleHits = 0;
            blockAdvance = blockAdvance + 10;

            int stop = blocks.size();
            Block block = null;
            for(int i = 0; i < stop; i++)
            {
                block = blocks.get(i);
                block.y = block.y + blockAdvance;
            }
        }
    }


    private boolean collideRects(float x, float y, float width, float height,
                              float x2, float y2, float width2, float height2)
    {
        if(x < (x2 + width2) && (x + width) > x2 && (y + height) > y2 && y < (y2 + height2))
        {
            return true;
        }
        return false;
    }

    private void collideBallBlocks(float deltaTime)
    {
        for(int i = 0; i < blocks.size(); i++)
        {
            Block block = blocks.get(i);
            if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                    block.x, block.y, Block.WIDTH, Block.HEIGHT))
            {
                collisionListener.collisionBlock();
                points = points + 10 - block.type;
                blocks.remove(i);
                i--;
                float oldvx = ball.vx;
                float oldvy = ball.vy;
                reflectBall(ball, block);
                ball.x = ball.x - oldvx * deltaTime * 1.01f;
                ball.y = ball.y - oldvy * deltaTime * 1.01f;
            }
        }
    }

    private void reflectBall(Ball ball, Block block)
    {
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x, block.y, 1, 1)) //check the top left corner of the block
        {
            if(ball.vx > 0) ball.vx = -ball.vx;
            if(ball.vy > 0) ball.vy = -ball.vy;
            return;
        }
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x + Block.WIDTH, block.y, 1, 1)) //check the top right corner of the block
        {
            if(ball.vx < 0) ball.vx = -ball.vx;
            if(ball.vy > 0) ball.vy = -ball.vy;
            return;
        }
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x, block.y + Block.HEIGHT, 1, 1)) // check the bottom left corner of the block
        {
            if(ball.vx > 0) ball.vx = -ball.vx;
            if(ball.vy < 0) ball.vy = -ball.vy;
            return;
        }
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x + Block.WIDTH, block.y + Block.HEIGHT, 1, 1)) //check the bottom right corner of the block
        {
            if(ball.vx < 0) ball.vx = -ball.vx;
            if(ball.vy < 0) ball.vy = -ball.vy;
            return;
        }
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x, block.y, Block.WIDTH, 1)) //check the top edge of the block
        {
            ball.vy = -ball.vy; //should not be possible to get here from negative vy, so no if(vy>0) is needed
            return;
        }
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x, block.y + Block.HEIGHT, Block.WIDTH, 1)) //check the bottom edge of the block
        {
            ball.vy = -ball.vy;
            return;
        }
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x, block.y, 1, Block.HEIGHT)) //check the left edge of the block
        {
            ball.vx = -ball.vx;
            return;
        }
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x + Block.WIDTH, block.y, 1, Block.HEIGHT)) //check the right edge of the block
        {
            ball.vx = -ball.vx;
        }
    }


}
