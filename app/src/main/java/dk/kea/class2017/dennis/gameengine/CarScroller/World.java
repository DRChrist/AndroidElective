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
    float playerspritex;
    float playerspritey;

    boolean gameOver = false;
    ScrollingBackground scrollingBackground;
    Player player;
    List<Monster> monsters = new ArrayList<>();

    public World(GameEngine game)
    {
        this.game = game;
        screenWidth = game.getFrameBufferWidth();
        scrollingBackground = new ScrollingBackground();
        player = new Player(30, 160 - Player.HEIGHT/2);
    }

    public void update(float deltatime)
    {
        scrollingBackground.scrollx = scrollingBackground.scrollx + 500 * deltatime;
        if(scrollingBackground.scrollx > MAX_WIDTH - screenWidth)
        {
            scrollingBackground.scrollx = 0;
        }

        playerspritex = playerspritex + 270 * deltatime;
        if(playerspritex > 20)
        {
            playerspritex = 0;
            player.spritex = player.spritex + Player.WIDTH;
            if(player.spritex == 7*Player.WIDTH)
            {
                player.spritex = 0;
                player.spritey = player.spritey + Player.HEIGHT;
                if(player.spritey == 3*Player.HEIGHT)
                {
                    player.spritey = 0;
                    player.spritex = 0;
                }
            }
        }
//        player.spritey = 0;
    }
}
