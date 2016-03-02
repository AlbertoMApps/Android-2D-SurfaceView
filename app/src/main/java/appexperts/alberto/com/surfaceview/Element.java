package appexperts.alberto.com.surfaceview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by alber on 02/03/2016.
 */
public class Element {

    private int mX,mY;
    private int speedX, speedY;
    private Bitmap mBitmap;

    public Element(int mX, int mY, Resources res) {
        this.mX = mX;
        this.mY = mY;
        this.mBitmap = BitmapFactory.decodeResource(res, R.drawable.earth);
        Random random = new Random();
        speedX = random.nextInt(10)-3;
        speedY = random.nextInt(10)-3;
    }

    public void doDraw(Canvas canvas){

        canvas.drawBitmap(mBitmap,mX,mY,null);

    }
    public void animate(long elapsedTime){
        mX += speedX *(elapsedTime/20f);
        mY += speedY * (elapsedTime/20f);
        checkBorders();
    }

    //Check if the element is inside the window
    private void checkBorders() {
        if(mX<=0){
            speedX = -speedX;
            mX =0;
        } else if( mX + mBitmap.getWidth() >= Panel.mWidth){
            speedX  = -speedX;
            mX = (int) (Panel.mWidth - mBitmap.getWidth());
        } else if(mY<=0){
            speedY = -speedY;
             mY =0;
        }
         else if( mY + mBitmap.getHeight() >= Panel.mHeight){
            speedY  = -speedY;
            mY = (int) (Panel.mHeight - mBitmap.getHeight());
         }
    }
}
