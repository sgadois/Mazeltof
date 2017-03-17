package com.m2dl.mobe.mazeltof.Models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by seb on 17/03/17.
 */

public class Ball extends View {

    public float x;
    public float y;
    public int r;
    private boolean jump;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //construct new ball object
    public Ball(Context context, float x, float y, int r) {
        super(context);
        //color hex is [transparency][red][green][blue]
        mPaint.setColor(0xFF00FF00);  //not transparent. color is green
        this.x = x;
        this.y = y;
        this.r = r;  //radius
        this.jump = false;
    }

    //called by invalidate()
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, r, mPaint);
    }

    public void jump(){
        if(!jump){
            jump=true;
            this.r = this.r*2;
        }
    }

    public void fall(){
        if(jump){
            jump= false;
            this.r = this.r/2;
        }
    }

    public boolean isJump(){
        return this.jump;
    }
}
