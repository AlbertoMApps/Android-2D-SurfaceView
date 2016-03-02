package appexperts.alberto.com.surfaceview;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by alber on 02/03/2016.
 */
public class Thread extends java.lang.Thread { //Thread is a part of code that runs at the same time we do another task..

    private  Panel mPanel;
    private SurfaceHolder mHolder;
    private boolean mRun = false;
    private long mStartTime;
    private long mElapsed = 0 ;

    public Thread (Panel panel){
        mPanel = panel;
        mHolder = panel.getHolder();
    }

    public void setRunning(boolean run){
        mRun = run;
    }

    @Override
    public void run() {
        super.run();
        Canvas canvas = null;
        mStartTime = System.currentTimeMillis();
        while (mRun ){
            canvas = mHolder.lockCanvas();
            if (canvas!=null){
                //we call the methods animate and DoDraw
                mPanel.animate(mElapsed);
                mPanel.doDraw(mElapsed, canvas); //until you dont have all the elements to paint it does not update it
                mElapsed = System.currentTimeMillis() - mStartTime;
                mHolder.unlockCanvasAndPost(canvas); //updated
            }
            mStartTime = System.currentTimeMillis();
        }
    } //run
} //class Thread
