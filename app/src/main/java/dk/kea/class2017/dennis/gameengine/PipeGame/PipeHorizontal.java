package dk.kea.class2017.dennis.gameengine.PipeGame;

/**
 * Created by Dennis on 26/04/2017.
 */

public class PipeHorizontal extends Pipe
{
    public final float WIDTH = 32;
    public final float HEIGHT = 22;
    public final int spritex = 128;
    public final int spritey = 261;

    public PipeHorizontal(float x, float y)
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
