package dk.kea.class2017.dennis.gameengine.Breakout;

/**
 * Created by Dennis on 16/03/2017.
 */

public class World
{
    public static final float MIN_X = 0;
    public static final float MAX_X = 319;
    public static final float MIN_Y = 40;
    public static final float MAX_Y = 479;
    Ball ball = new Ball();

    public void update(float deltaTime)
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
            ball.vy = -ball.vy;
            ball.y = MAX_Y - ball.HEIGHT;
        }
    }
}
