package dk.kea.class2017.dennis.gameengine;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis on 02/02/2017.
 */

public abstract class GameEngine extends Activity implements Runnable
{
    private Thread mainLoopThread;
    private State state = State.Paused;
    private List<State> stateChanges = new ArrayList<>();
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Screen screen;
    private Canvas canvas = null;
    Rect src = new Rect();
    Rect dst = new Rect();
    private Bitmap offscreenSurface;

    public abstract Screen createStartScreen();

    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN|
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        surfaceView = new SurfaceView(this);
        setContentView(surfaceView);
        surfaceHolder = surfaceView.getHolder();
        screen = createStartScreen();
        if(surfaceView.getWidth() > surfaceView.getHeight())
        {
            setOffscreenSurface(480, 320);
        }
        else
        {
            setOffscreenSurface(320, 480);
        }
    }

    public void setScreen(Screen screen)
    {
        if(this.screen != null) this.screen.dispose();
        this.screen = screen;
    }

    public Bitmap loadBitmap(String fileName)
    {
        InputStream in = null;
        Bitmap bitmap = null;

        try
        {
            in = getAssets().open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if(bitmap == null)
            {
                throw new RuntimeException("Could not create a bitmap from file " + fileName);
            }
            return bitmap;
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not load the bloody file name: " + fileName + "!");
        }
        finally
        {
            if(in != null)
                try
                {
                    in.close();
                }
                catch(IOException e)
                {
                    Log.e("GameEngine","loadBitmap() failed to close the file" + fileName);
                }
        }
    }

//    public Music loadMusic(String fileName)
//    {
//        return null;
//    }
//
//    public Sound loadSound(String fileName)
//    {
//        return null;
//    }

    public void clearFrameBuffer(int color)
    {
        canvas.drawColor(color);
    }

    public void setOffscreenSurface(int width, int height)
    {
        if(offscreenSurface != null) offscreenSurface.recycle();
        offscreenSurface = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        canvas = new Canvas(offscreenSurface);
    }
    public int getFrameBufferWidth()
    {
        return surfaceView.getWidth();
    }

    public int getFrameBufferHeight()
    {
        return surfaceView.getHeight();
    }

    public void drawBitmap(Bitmap bitmap, int x, int y)
    {
        if(canvas != null) canvas.drawBitmap(bitmap, x, y, null);
    }


    public void drawBitmap(Bitmap bitmap, int x, int y, int srcX,
                           int srcY, int srcWidth, int srcHeight)
    {
        if(canvas == null) return;
        src.left = srcX;
        src.top = srcY;
        src.right = srcX + srcWidth;
        src.bottom = srcY + srcHeight;

        dst.left = x;
        dst.top = y;
        dst.right = x + srcWidth;
        dst.bottom = y + srcHeight;

        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public boolean isKeyPressed(int keyCode)
    {
        return false;
    }

    public boolean isTouchDown(int pointer)
    {
        return false;
    }
    public int getTouchX(int pointer)
    {
        return 0;
    }
    public int getTouchY(int pointer)
    {
        return 0;
    }

    public void run()
    {
        while(true)
        {
            synchronized(stateChanges)
            {
                for(int i = 0; i < stateChanges.size(); i++)
                {
                    state = stateChanges.get(i);
                    if(state == State.Disposed)
                    {
                        if(screen != null)
                        {
                            screen.dispose();
                        }
                        Log.d("GameEngine", "state changed to Disposed");
                        return;
                    }
                    if(state == State.Paused)
                    {
                        if(screen != null)
                        {
                            screen.pause();
                        }
                        Log.d("GameEngine", "state changed to Paused");
                        return;
                    }
                    if(state == State.Resumed)
                    {
                        if(screen != null)
                        {
                            screen.resume();
                        }
                        state = State.Running;
                        Log.d("GameEngine", "state changed to Resumed/Running");
                    }
                    //stateChanges.clear();

                }
                stateChanges.clear();
                if(state == State.Running)
                {
                    if(!surfaceHolder.getSurface().isValid()) continue;
                    Canvas canvas = surfaceHolder.lockCanvas();
                    // we will do all the drawing here
                    if(screen != null) screen.update(0);
                    src.left = 0;
                    src.top = 0;
                    src.right = offscreenSurface.getWidth() - 1;
                    src.bottom = offscreenSurface.getHeight() - 1;
                    dst.left = 0;
                    dst.top = 0;
                    dst.right = surfaceView.getWidth() - 1;
                    dst.bottom = surfaceView.getHeight() - 1;
                    canvas.drawBitmap(offscreenSurface, src, dst, null);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    canvas = null;
                }
            }
        }
    }

    public void onPause()
    {
        super.onPause();
        synchronized(stateChanges)
        {
            if(isFinishing())
            {
                stateChanges.add(stateChanges.size(), State.Disposed);
            }
            else
            {
                stateChanges.add(stateChanges.size(), State.Paused);
            }
        }
    }

    public void onResume()
    {
        super.onResume();
        mainLoopThread = new Thread(this);
        mainLoopThread.start();
        synchronized(stateChanges)
        {
            stateChanges.add(stateChanges.size(), State.Resumed);
        }
    }

}
