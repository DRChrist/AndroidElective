package dk.kea.class2017.dennis.gameengine.WhipGame;

import android.graphics.Color;

import dk.kea.class2017.dennis.gameengine.GameEngine;

/**
 * Created by Dennis on 25/04/2017.
 */

public class World
{
    public static final float MIN_X = 0;
    public static final float MAX_X = 319;
    public static final float MIN_Y = 0;
    public static final float MAX_Y = 479;

    Line line = new Line();

    GameEngine game;

    public World(GameEngine game)
    {
        this.game = game;
    }

    public void update(float deltaTime)
    {

        line.spritex = 2;
        line.spritey = 2;
        if(game.isTouchDown(0))
        {
            line.x = game.getTouchX(0);
            line.y = game.getTouchY(0);
        }
    }
}
