package com.example.kru13.sokoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by kru13 on 12.10.16.
 */
public class SokoView extends View{

    Bitmap[] bmp;

    int lx = 10;
    int ly = 10;

    int width;
    int height;

    int heroX;
    int heroY;

    private int level[] = {1,1,1,1,1,1,1,1,1,0,
            1,0,0,0,0,0,0,0,1,0,
            1,0,2,3,3,2,1,0,1,0,
            1,0,1,3,2,3,2,0,1,0,
            1,0,2,3,3,2,4,0,1,0,
            1,0,1,3,2,3,2,0,1,0,
            1,0,2,3,3,2,1,0,1,0,
            1,0,0,0,0,0,0,0,1,0,
            1,1,1,1,1,1,1,1,1,0,
            0,0,0,0,0,0,0,0,0,0
    };

    public SokoView(Context context) {
        super(context);
        init(context);
    }

    public SokoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SokoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        bmp = new Bitmap[6];

        bmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
        bmp[1] = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
        bmp[2] = BitmapFactory.decodeResource(getResources(), R.drawable.box);
        bmp[3] = BitmapFactory.decodeResource(getResources(), R.drawable.goal);
        bmp[4] = BitmapFactory.decodeResource(getResources(), R.drawable.hero);
        bmp[5] = BitmapFactory.decodeResource(getResources(), R.drawable.boxok);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / ly;
        height = h / lx;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < lx; i++) {
            for (int j = 0; j < ly; j++) {
                canvas.drawBitmap(bmp[level[i*10 + j]], null,
                        new Rect(j*width, i*height,(j+1)*width, (i+1)*height), null);
                if (level[i*10 + j] == 4){
                    heroX = j;
                    heroY = i;

//                    Toast.makeText(getContext(), "heroX " + heroX + " heroY " + heroY, Toast.LENGTH_SHORT).show();
                }
            }
        }
//        Toast.makeText(getContext(), "" + level[0] + " " + level[1] + " " + level[46], Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            {
                double xDOWN = event.getX();
                double yDOWN = event.getY();
                Toast.makeText(getContext(), "xDOWN " + xDOWN + " " + "yDOWN " + yDOWN, Toast.LENGTH_SHORT).show();

                if (xDOWN > 450 && xDOWN > yDOWN){
                        level[heroY*10 + heroX] = 0;
                        level[heroY*10 + heroX+1] = 4;
                        heroX++;
                        invalidate();
                } else if (xDOWN < 450) {
                    level[heroY * 10 + heroX] = 0;
                    level[heroY * 10 + heroX - 1] = 4;
                    heroX++;
                    invalidate();
                } else if (yDOWN > 450 && yDOWN > xDOWN){
                    level[heroY*10 + heroX] = 0;
                    level[heroY*10 + 10 + heroX] = 4;
                    heroY = heroY + 10;
                    invalidate();
                } else if (yDOWN < 450){
                    level[heroY*10 + heroX] = 0;
                    level[heroY*10 - 10 + heroX] = 4;
                    heroY = heroY - 10;
                    invalidate();
                }
                break;
            }

        }
        return super.onTouchEvent(event);
    }
}
