package dk.kea.class2017.dennis.gameengine;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.List;

/**
 * Created by Dennis on 28/02/2017.
 */

public class MultiTouchHandler implements TouchHandler, View.OnTouchListener
{
    private boolean[] isTouched = new boolean[20];
    private int[] touchX = new int[20];
    private int[] touchY = new int[20];
    private List<TouchEvent> touchEventBuffer;
    private TouchEventPool touchEventPool;
    private Bitmap offscreenSurface;
    private SurfaceView surfaceView;

    public MultiTouchHandler(View v, List<TouchEvent> touchEventBuffer, TouchEventPool touchEventPool,
                             Bitmap offscreenSurface, SurfaceView surfaceView)
    {
        v.setOnTouchListener(this);
        this.touchEventBuffer = touchEventBuffer;
        this.touchEventPool = touchEventPool;
        this.offscreenSurface = offscreenSurface;
        this.surfaceView = surfaceView;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        TouchEvent touchEvent = null;
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        int pointerId = event.getPointerId(pointerIndex);

        switch(action)
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                touchEvent = touchEventPool.obtain();
                touchEvent.type = TouchEvent.TouchEventType.Down;
                touchEvent.pointer = pointerId;
                touchX[pointerId] = (int) event.getX(pointerIndex);
                touchEvent.x = (int)(touchX[pointerId] * (float) offscreenSurface.getWidth() / (float) surfaceView.getWidth());
                touchY[pointerId] = (int) event.getY(pointerIndex);
                touchEvent.y = (int)(touchY[pointerId] * (float) offscreenSurface.getHeight() / (float) surfaceView.getHeight());
                isTouched[pointerId] = true;
                synchronized (touchEventBuffer)
                {
                    touchEventBuffer.add(touchEvent);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                touchEvent = touchEventPool.obtain();
                touchEvent.type = TouchEvent.TouchEventType.Up;
                touchEvent.pointer = pointerId;
                touchX[pointerId] = (int) event.getX(pointerIndex);
                touchEvent.x = (int)(touchX[pointerId] * (float) offscreenSurface.getWidth() / (float) surfaceView.getWidth());
                touchY[pointerId] = (int) event.getY(pointerIndex);
                touchEvent.y = (int)(touchY[pointerId] * (float) offscreenSurface.getHeight() / (float) surfaceView.getHeight());
                isTouched[pointerId] = false;
                synchronized (touchEventBuffer)
                {
                    touchEventBuffer.add(touchEvent);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                synchronized (touchEventBuffer)
                {
                    for (int i = 0; i < pointerCount; i++)
                    {
                        touchEvent = touchEventPool.obtain();
                        touchEvent.type = TouchEvent.TouchEventType.Dragged;
                        pointerIndex = i;
                        pointerId = event.getPointerId(pointerIndex);
                        touchEvent.pointer = pointerId;
                        touchX[pointerId] = (int) event.getX(pointerIndex);
                        touchEvent.x = (int)(touchX[pointerId] * (float) offscreenSurface.getWidth() / (float) surfaceView.getWidth());
                        touchY[pointerId] = (int) event.getY(pointerIndex);
                        touchEvent.y = (int)(touchY[pointerId] * (float) offscreenSurface.getHeight() / (float) surfaceView.getHeight());
                        isTouched[pointerId] = true;
                        synchronized (touchEventBuffer)
                        {
                            touchEventBuffer.add(touchEvent);
                        }
                    }
                }
                break;
        }//end of switch statement

        return true;
    }//end of onTouch() method

    @Override
    public boolean isTouchDown(int pointer)
    {
        return isTouched[pointer];
    }

    @Override
    public int getTouchX(int pointer)
    {
        return touchX[pointer];
    }

    @Override
    public int getTouchY(int pointer)
    {
        return touchY[pointer];
    }
}
