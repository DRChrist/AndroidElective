package dk.kea.class2017.dennis.gameengine.PipeGame;

/**
 * Created by Dennis on 26/04/2017.
 */

public class PipeVertical extends Pipe
{
    public final float WIDTH = 22;
    public final float HEIGHT = 32;
    public final int spritex = 133;
    public final int spritey = 288;

    public PipeVertical(float x, float y)
    {
        super(x, y);
    }

    public float getWIDTH()
    {
        return this.WIDTH;
    }

    public float getHEIGHT()
    {
        return this.HEIGHT;
    }

    public int getSpritex()
    {
        return this.spritex;
    }

    public int getSpritey()
    {
        return this.spritey;
    }
}
