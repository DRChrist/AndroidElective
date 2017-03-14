package dk.kea.class2017.dennis.gameengine.Breakout;

import dk.kea.class2017.dennis.gameengine.GameEngine;
import dk.kea.class2017.dennis.gameengine.Screen;

/**
 * Created by Dennis on 14/03/2017.
 */

public class MainMenu extends GameEngine
{
    @Override
    public Screen createStartScreen()
    {
        return new MainMenuScreen(this);
    }
}
