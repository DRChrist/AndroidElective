package dk.kea.class2017.dennis.gameengine.Breakout;

import dk.kea.class2017.dennis.gameengine.Sound;

/**
 * Created by Dennis on 30/03/2017.
 */

public class MyCollisionListener implements CollisionListener
{
    Sound wallSound;
    Sound paddleSound;
    Sound blockSound;

    public MyCollisionListener(Sound wallSound, Sound paddleSound, Sound blockSound)
    {
        this.wallSound = wallSound;
        this.paddleSound = paddleSound;
        this.blockSound = blockSound;
    }

    @Override
    public void collisionWall()
    {
        wallSound.play(1);
    }

    @Override
    public void collisionPaddle()
    {
        paddleSound.play(1);
    }

    @Override
    public void collisionBlock()
    {
        blockSound.play(1);
    }
}
