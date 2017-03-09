package dk.kea.class2017.dennis.gameengine;

import android.media.SoundPool;

/**
 * Created by Dennis on 09/03/2017.
 */

public class Sound
{
    int soundId;
    SoundPool soundPool;


    public Sound(SoundPool soundPool, int soundId)
    {
        this.soundPool = soundPool;
        this.soundId = soundId;
    }

    public void play(float volume)
    {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    public void dispose()
    {
        soundPool.unload(soundId);
    }
}
