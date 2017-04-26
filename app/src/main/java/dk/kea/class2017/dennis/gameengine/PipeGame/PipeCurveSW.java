package dk.kea.class2017.dennis.gameengine.PipeGame;

/**
 * Created by Dennis on 26/04/2017.
 */

public class PipeCurveSW extends Pipe
{
    public final float WIDTH = 26;
    public final float HEIGHT = 26;
    public final int spritex = 96;
    public final int spritey = 261;

    public PipeCurveSW(float x, float y)
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
