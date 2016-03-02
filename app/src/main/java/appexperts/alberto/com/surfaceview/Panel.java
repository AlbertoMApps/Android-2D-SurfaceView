package appexperts.alberto.com.surfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by alber on 02/03/2016.
 */
public class Panel extends SurfaceView implements SurfaceHolder.Callback{
    private Bitmap mBitmap;
    private Thread mThread;
    private int mX, mY;
    private ArrayList<Element> mElements = new ArrayList<Element>();
    public static float mWidth;
    public static float mHeight;
    private Paint paint = new Paint();

    public Panel(Context context) {
        super(context);
        mBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.com);
        getHolder().addCallback(this);
        mThread = new Thread(this);
        paint.setColor(Color.WHITE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(!mThread.isAlive()){
            mThread = new Thread(this);
            mThread.setRunning(true);
            mThread.start();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        if(mThread.isAlive()){
            mThread.setRunning(false);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mX = (int) event.getX() - mBitmap.getWidth()/2;
        mY = (int) event.getY() - mBitmap.getHeight()/2;
        //for the Element classes
        synchronized (mElements) {
            mElements.add(new Element((int) event.getX(), (int) event.getY(), getResources()));
        }
        return super.onTouchEvent(event);
    }

    //draw
    public void doDraw(long mElapsed, Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(mBitmap, mX, mY, null);
        //for the Element classes
        synchronized (mElements) {
            for (Element element : mElements) {
                element.doDraw(canvas);
            }
        }
        canvas.drawText("FPS: " + Math.round( 1000f / mElapsed) + "Elements: " + mElements.size(), 10, 10, paint );
    }

    //animate
    public void animate (long mElapsed){
        synchronized (mElements){
            for (Element element: mElements){
                element.animate(mElapsed);
            }
        }
    }
}
