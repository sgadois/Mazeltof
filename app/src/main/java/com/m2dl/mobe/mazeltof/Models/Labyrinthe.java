package com.m2dl.mobe.mazeltof.Models;

import android.graphics.PointF;

import static android.R.attr.bitmap;

/**
 * Created by Blue on 17/03/2017.
 */

public class Labyrinthe {

    private boolean[][] wall;
    private boolean[][] hole;

    private PointF depart;
    private PointF fin;


    public Labyrinthe(boolean[][] wall, boolean[][] hole,float xDepart, float yDepart, float xFin, float yFin){
        this.wall = wall;
        this.hole = hole;
        depart= new PointF();
        fin = new PointF();
        depart.set(xDepart,yDepart);
        fin.set(xFin,yFin);
    }

    public Labyrinthe(boolean[][] wall, boolean[][] hole){
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

    public boolean[][] getHole(){
        return this.hole;
    }

    public boolean[][] getWall(){
        return this.wall;
    }


}
