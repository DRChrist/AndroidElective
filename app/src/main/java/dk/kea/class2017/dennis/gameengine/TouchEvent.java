package dk.kea.class2017.dennis.gameengine;

/**
 * Created by Dennis on 28/02/2017.
 */

public class TouchEvent
{
    public enum TouchEventType
    {
        Down,
        Up,
        Dragged
    }

    public TouchEventType type; //the type of the event
    public int x;               //x-coordinate of the event
    public int y;               //y-coordinate of the event
    public int pointer;         //the pointer id (from the android system)
}
