package dk.kea.class2017.dennis.gameengine;

/**
 * Created by Dennis on 07/02/2017.
 */

public class SimpleGame extends GameEngine
{
    @Override
    public Screen createStartScreen()
    {
        return new SimpleScreen(this);
    }
}
