package dk.kea.class2017.dennis.gameengine.CarScroller;

import java.util.ArrayList;
import java.util.List;

import dk.kea.class2017.dennis.gameengine.GameEngine;

/**
 * Created by Dennis on 06/04/2017.
 */

public class World
{
    public static final int MIN_WIDTH = 0;
    public static final int MAX_WIDTH = 2699;
    public static final int MIN_HEIGHT = 30;
    public static final int MAX_HEIGHT = 290;

    GameEngine game;
    int screenWidth;

    boolean gameOver = false;
    ScrollingBackground scrollingBackground;
    Car car;
    List<Monster> monsters = new ArrayList<>();

    public World(GameEngine game)
    {
        this.game = game;
        screenWidth = game.getFrameBufferWidth();
        scrollingBackground = new ScrollingBackground();
        car = new Car();
    }

    public void update(float deltatime)
    {
        scrollingBackground.scrollx = scrollingBackground.scrollx + 500 * deltatime;
        if(scrollingBackground.scrollx > MAX_WIDTH - screenWidth)
        {
            scrollingBackground.scrollx = 0;
        }
    }
}
