package com.m2dl.mobe.mazeltof.Models;

import static android.R.attr.bitmap;

/**
 * Created by Blue on 17/03/2017.
 */

public class Labyrinthe {

    private boolean[][] wall;
    private boolean[][] hole;


    private int xDepart;
    private int yDepart;

    private int xFin;
    private int yFin;

    public Labyrinthe(boolean[][] wall, boolean[][] hole,int xDepart, int yDepart, int xFin, int yFin){
        this.wall = wall;
        this.hole = hole;
        this.xDepart = xDepart;
        this.yDepart = yDepart;
        this.xFin = xFin;
        this.yFin = yFin;
    }

    public boolean[][] getHole(){
        return this.hole;
    }

    public boolean[][] getWall(){
        return this.wall;
    }

    public int getxDepart() {
        return xDepart;
    }

    public int getyDepart() {
        return yDepart;
    }

    public int getxFin() {
        return xFin;
    }

    public int getyFin() {
        return yFin;
    }
}
