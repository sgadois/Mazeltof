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

    private Context context;

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Labyrinthe(Context context, boolean[][] wall, boolean[][] hole, float xDepart, float yDepart, float xFin, float yFin){
        super(context);
        mPaint.setColor(0x00000000);
        this.wall = wall;
        this.hole = hole;
        this.context = context;
        depart= new PointF();
        fin = new PointF();
        depart.set(xDepart,yDepart);
        fin.set(xFin,yFin);
    }

    public Labyrinthe(Context context, boolean[][] wall, boolean[][] hole){
        super(context);
        mPaint.setColor(0x00000000);
        this.wall = wall;
        this.hole = hole;
        depart= new PointF();
        fin = new PointF();
    }

    public void setDepart(float xDepart,float yDepart){
        depart.set(xDepart,yDepart);
    }

    public void setFin(float xFin, float yFin){
        fin.set(xFin,yFin);
    }

    public void setContext(Context context){
        this.context = context;
    }

    public boolean[][] getHole(){
        return this.hole;
    }

    public boolean[][] getWall(){
        return this.wall;
    }

    public void makeWall(){
        Activity activity = (Activity)this.context;
        Display sizeContainer = activity.getWindowManager().getDefaultDisplay();
        int screenX = sizeContainer.getWidth();
        int screenY = sizeContainer.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(10, 20, 30, 20, mPaint);
    }
}
