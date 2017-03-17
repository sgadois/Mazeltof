package com.m2dl.mobe.mazeltof.Models;

import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.view.Display;

import static android.R.attr.bitmap;

/**
 * Created by Blue on 17/03/2017.
 */

public class Labyrinthe {

    private boolean[][] wall;
    private boolean[][] hole;

    private Wall[] wallResized;

    private PointF depart;
    private PointF fin;

    private Context context;


    public Labyrinthe(boolean[][] wall, boolean[][] hole,Context context, float xDepart, float yDepart, float xFin, float yFin){
        this.wall = wall;
        this.hole = hole;
        this.context = context;
        depart= new PointF();
        fin = new PointF();
        depart.set(xDepart,yDepart);
        fin.set(xFin,yFin);
    }

    public Labyrinthe(boolean[][] wall, boolean[][] hole, Context context){
        this.wall = wall;
        this.hole = hole;
        depart= new PointF();
        fin = new PointF();
        this.context = context;
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


}
