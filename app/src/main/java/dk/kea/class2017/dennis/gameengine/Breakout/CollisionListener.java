package dk.kea.class2017.dennis.gameengine.Breakout;

/**
 * Created by Dennis on 30/03/2017.
 */

public interface CollisionListener
{
    public void collisionWall();
    public void collisionPaddle();
    public void collisionBlock();
}
