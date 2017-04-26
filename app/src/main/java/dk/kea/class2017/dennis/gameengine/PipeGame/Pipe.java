package dk.kea.class2017.dennis.gameengine.PipeGame;

/**
 * Created by Dennis on 26/04/2017.
 */

public abstract class Pipe
{
    float WIDTH;
    float HEIGHT;
    int spritex;
    int spritey;
    float x;
    float y;

    public Pipe(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getWIDTH()
    {
        return WIDTH;
    }

    public float getHEIGHT()
    {
        return HEIGHT;
    }

    public int getSpritex()
    {
        return spritex;
    }

    public int getSpritey()
    {
        return spritey;
    }
}
