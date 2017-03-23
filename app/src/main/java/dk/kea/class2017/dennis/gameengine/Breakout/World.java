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

    boolean gameOver = false;
    Ball ball = new Ball();
    Paddle paddle = new Paddle();
    List<Block> blocks = new ArrayList<>();

    GameEngine game;

    public World(GameEngine game)
    {
        this.game = game;
        generateBlocks();
    }

    public void update(float deltaTime, float accelX)//method called from update method in GameScreen
    {
        ball.x = ball.x + ball.vx * deltaTime;
        ball.y = ball.y + ball.vy * deltaTime;
        if(ball.x < MIN_X)
        {
            ball.vx = -ball.vx;
            ball.x = MIN_X;
        }
        if(ball.x > MAX_X - ball.WIDTH)
        {
            ball.vx = -ball.vx;
            ball.x = MAX_X - ball.WIDTH;
        }
        if(ball.y < MIN_Y)
        {
            ball.vy  = -ball.vy;
            ball.y = MIN_Y;
        }
        if(ball.y > MAX_Y - ball.HEIGHT)
        {
            gameOver = true;
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
        collideBallBlocks();
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
        //check for collision with left end of the paddle
        if(ball.x + Ball.WIDTH > paddle.x && ball.x < paddle.x + 3
                && ball.y + Ball.HEIGHT > paddle.y + 2)
        {
            ball.vy = -ball.vy;
            if(ball.vx > 0) ball.vx = -ball.vx;
            ball.y = paddle.y - Ball.HEIGHT;
        }

        //check for collision with right end of the paddle
        if(ball.x + Ball.WIDTH > paddle.x + Paddle.WIDTH - 3 && ball.x < paddle.x + Paddle.WIDTH
                && ball.y + Ball.HEIGHT > paddle.y + 2)
        {
            ball.vy = -ball.vy;
            if(ball.vx < 0) ball.vx = -ball.vx;
            ball.y = paddle.y - Ball.HEIGHT;
        }

        if(ball.x + Ball.WIDTH > paddle.x && ball.x < paddle.x + Paddle.WIDTH
                && ball.y + Ball.HEIGHT > paddle.y)
        {
            ball.vy = -ball.vy;
            ball.y = paddle.y - Ball.HEIGHT - 1;
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

    private void collideBallBlocks()
    {
        for(int i = 0; i < blocks.size(); i++)
        {
            Block block = blocks.get(i);
            if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                    block.x, block.y, Block.WIDTH, Block.HEIGHT))
            {
                blocks.remove(i);
                i--;
            }
        }
    }

}
