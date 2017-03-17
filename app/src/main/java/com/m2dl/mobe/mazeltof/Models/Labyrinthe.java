package com.m2dl.mobe.mazeltof.Models;

import android.content.Context;

import android.app.Activity;
import android.graphics.PointF;
import android.view.Display;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;


/**
 * Created by Blue on 17/03/2017.
 */

public class Labyrinthe extends View {

    private boolean[][] wall;
    private boolean[][] hole;

    private Wall[] wallResized;

    private PointF depart;
    private PointF fin;

    private int screenX;
    private int screenY;

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public Labyrinthe(Context context, boolean[][] wall, boolean[][] hole){
        super(context);
        mPaint.setColor(0xFF000000);
        this.wall = wall;
        this.hole = hole;
        depart= new PointF();
        fin = new PointF();
        Display sizeContainer = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        screenX = sizeContainer.getWidth();
        screenY = sizeContainer.getHeight();

    }

    public void setDepart(float xDepart,float yDepart){
        depart.set(xDepart,yDepart);
    }

    public void setFin(float xFin, float yFin){
        fin.set(xFin,yFin);
    }

    public boolean[][] getHole(){
        return this.hole;
    }

    public boolean[][] getWall(){
        return this.wall;
    }

    public void makeWall(){
        Display sizeContainer = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        int screenX = sizeContainer.getWidth();
        int screenY = sizeContainer.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(screenX/2 - 100, screenY/2 - 100, screenX/2 - 100, screenY/2 + 100, mPaint);
        canvas.drawLine(screenX/2 - 100, screenY/2 + 100, screenX/2 + 100, screenY/2 + 100, mPaint);
        canvas.drawLine(screenX/2 + 100, screenY/2 + 100, screenX/2 + 100, screenY/2 - 100, mPaint);
        canvas.drawLine(screenX/2 + 100, screenY/2 - 100, screenX/2 - 100, screenY/2 - 100, mPaint);
    }
}
