package dk.kea.class2017.dennis.gameengine;

/**
 * Created by Dennis on 28/02/2017.
 */

public class TouchEventPool extends Pool<TouchEvent>
{

    @Override
    protected TouchEvent newItem()
    {
        return new TouchEvent();
    }
}
