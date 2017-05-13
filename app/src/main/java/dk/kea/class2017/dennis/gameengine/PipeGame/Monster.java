package dk.kea.class2017.dennis.gameengine.PipeGame;

/**
 * Created by Dennis on 12/05/2017.
 */

public class Monster
{
    public static final int WIDTH = 29;
    public static final int HEIGHT = 25;
    public float x;
    public float y;
    public float v = 1;

    public Monster(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
}
