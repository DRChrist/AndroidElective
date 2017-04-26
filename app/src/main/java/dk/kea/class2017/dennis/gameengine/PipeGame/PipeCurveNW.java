package dk.kea.class2017.dennis.gameengine.PipeGame;

/**
 * Created by Dennis on 26/04/2017.
 */

public class PipeCurveNW extends Pipe
{
    public final float WIDTH = 26;
    public final float HEIGHT = 26;
    public final int spritex = 96;
    public final int spritey = 288;

    public PipeCurveNW(float x, float y)
    {
        super(x, y);
    }

    @Override
    public float getWIDTH()
    {
        return this.WIDTH;
    }

    @Override
    public float getHEIGHT()
    {
        return this.HEIGHT;
    }

    @Override
    public int getSpritex()
    {
        return this.spritex;
    }

    @Override
    public int getSpritey()
    {
        return this.spritey;
    }
}
