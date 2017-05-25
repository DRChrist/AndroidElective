package dk.kea.class2017.dennis.gameengine.PipeGame;

import dk.kea.class2017.dennis.gameengine.GameEngine;
import dk.kea.class2017.dennis.gameengine.Screen;

/**
 * Created by Dennis on 26/04/2017.
 */

public class PipeGame extends GameEngine
{

    @Override
    public Screen createStartScreen()
    {
        music = this.loadMusic("onaqui.wav");
        music.setLooping(true);
        return new MainMenuScreen(this);
    }

    public void onPause()
    {
        music.pause();
        super.onPause();
    }

    public void onResume()
    {
        music.play();
        super.onResume();
    }

    public void onDestroy()
    {
        super.onDestroy();
    }
}
