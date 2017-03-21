package dk.kea.class2017.dennis.gameengine.Breakout;

/**
 * Created by Dennis on 21/03/2017.
 */

public class Paddle
{
    public static final float WIDTH = 56;
    public static final float HEIGHT = 11;
    float x = 160 - WIDTH/2;
    float y = World.MAX_Y - 30;
}
