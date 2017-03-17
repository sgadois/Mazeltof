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
    public final int r;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //construct new ball object
    public Ball(Context context, float x, float y, int r) {
        super(context);
        //color hex is [transparency][red][green][blue]
        mPaint.setColor(0xFF00FF00);  //not transparent. color is green
        this.x = x;
        this.y = y;
        this.r = r;  //radius
    }

    //called by invalidate()
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, r, mPaint);
    }
}
