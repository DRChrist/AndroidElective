package dk.kea.class2017.dennis.gameengine.Breakout;

import java.util.ArrayList;
import java.util.List;

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

    public void update(float deltaTime, float accelX)
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

        paddle.x = paddle.x - accelX * deltaTime * 100; //the last multiplier is a speed constant
        if(paddle.x < MIN_X) paddle.x = MIN_X;
        if(paddle.x + Paddle.WIDTH > MAX_X) paddle.x = MAX_X - Paddle.WIDTH;

        collideBallPaddle();
    }

    private void generateBlocks()
    {
        blocks.clear();
        for(int y = 60, type = 0;y < 60 + 8*Block.HEIGHT; y = y + (int)Block.HEIGHT, type++)
        {
            for(int x = 20; x < MAX_X - Block.WIDTH; x = x + (int)Block.WIDTH)
            {
                blocks.add(new Block(x, y, type));
            }
        }
    }

    private void collideBallPaddle()
    {
        if(ball.x + Ball.WIDTH > paddle.x && ball.x < paddle.x + Paddle.WIDTH
                && ball.y + Ball.HEIGHT > paddle.y)
        {
            ball.vy = -ball.vy;
            ball.y = paddle.y - Ball.HEIGHT - 1;
        }
    }
}
