package dk.kea.class2017.dennis.gameengine;

/**
 * Created by Dennis on 28/02/2017.
 */

public interface TouchHandler
{
    public boolean isTouchDown(int pointer);
    public int getTouchX(int pointer);
    public int getTouchY(int pointer);
}
