package dk.kea.class2017.dennis.gameengine.CarScroller;

import dk.kea.class2017.dennis.gameengine.GameEngine;
import dk.kea.class2017.dennis.gameengine.Screen;

/**
 * Created by Dennis on 06/04/2017.
 */

public class CarScroller extends GameEngine
{

    @Override
    public Screen createStartScreen()
    {
        music = this.loadMusic("music.ogg");
        music.setLooping(true);
        return new MainMenuScreen(this);
    }

    public void onPause()
    {
        super.onPause();
        music.pause();
    }

    public void onResume()
    {
        super.onResume();
        music.play();
    }

     public void onDestroy()
     {
         super.onDestroy();
         music.stop();
         music.dispose();
     }
}
