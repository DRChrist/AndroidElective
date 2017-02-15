package dk.kea.class2017.dennis.gameengine;

/**
 * Created by Dennis on 07/02/2017.
 */

public abstract class Screen
{
    protected final GameEngine game;

    public Screen(GameEngine game)
    {
        this.game = game;
    }

    public abstract void update(float deltaTime);

    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();


}
