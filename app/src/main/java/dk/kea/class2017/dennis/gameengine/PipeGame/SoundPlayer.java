package dk.kea.class2017.dennis.gameengine.PipeGame;

import dk.kea.class2017.dennis.gameengine.Sound;

/**
 * Created by Dennis on 25/05/2017.
 */

public class SoundPlayer
{
    Sound buildPipeSound;
    Sound gameoverSound;

    public SoundPlayer(Sound buildPipeSound, Sound gameoverSound)
    {
        this.buildPipeSound = buildPipeSound;
        this.gameoverSound = gameoverSound;
    }

    public void buildPipe()
    {
        buildPipeSound.play(1);
    }

    public void gameover()
    {
        gameoverSound.play(1);
    }
}
