package com.m2dl.mobe.mazeltof.Models;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.Display;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Blue on 17/03/2017.
 */

public class Labyrinthe extends View {

    private boolean[][] wall;
    private boolean[][] hole;

    private ArrayList<Wall> wallResized;

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
        wallResized = new ArrayList<>();
        makeWall();
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

    public ArrayList<Wall> getWall(){
        return this.wallResized;
    }

    public void makeWall(){
        for(int j= 0; j<10; j++){
            boolean debuter = false;
            PointF debut = new PointF();
            PointF fin = new PointF();
            for(int i = 0; i<12; i++){
                if(wall[i][j]){
                   if(!debuter){
                       debuter = true;
                       debut=new PointF();
                       debut.set(i*screenX/wall.length, j*screenY/wall[j].length);
                   }
                    if(debuter && i == 12-1){
                        debuter=false;
                        fin = new PointF();
                        fin.set(i*screenX/wall.length, j*screenY/wall[j].length);
                        wallResized.add(new Wall(debut,fin));
                    }
                }
                else{
                    if(debuter) {
                        debuter=false;
                        fin = new PointF();
                        fin.set((i-1)*screenX/wall.length, j*screenY/wall[j].length);
                        wallResized.add(new Wall(debut,fin));
                    }
                }

            }
        }

        for(int i= 0; i<12; i++){
            boolean debuter = false;
            PointF debut = new PointF();
            PointF fin = new PointF();
            for(int j = 0; j<10; j++){
                if(wall[i][j]){
                    if(!debuter){
                        debuter = true;
                        debut=new PointF();
                        debut.set(i*screenX/wall.length, j*screenY/wall[j].length);
                    }
                    if(debuter && j == 10-1){
                        debuter=false;
                        fin = new PointF();
                        fin.set(i*screenX/wall.length, j*screenY/wall[j].length);
                        wallResized.add(new Wall(debut,fin));
                    }
                }
                else{
                    if(debuter) {
                        debuter=false;
                        fin = new PointF();
                        fin.set((i-1)*screenX/wall.length, j*screenY/wall[j].length);
                        wallResized.add(new Wall(debut,fin));
                    }
                }

            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Wall myWall : getTrumpWall()) {
            canvas.drawLine(myWall.getPointD().x, myWall.getPointD().y, myWall.getPointF().x, myWall.getPointF().y, mPaint);
        }
    }

    public List<Wall> getTrumpWall() {
        List<Wall> trumpWall = new ArrayList<Wall>();
        PointF d = new PointF();
        PointF f = new PointF();
        d.set(screenX/2 - 200, screenY/2 - 200);
        f.set(screenX/2 - 200, screenY/2 + 200);
        trumpWall.add(new Wall(d, f));

        d = new PointF();
        f = new PointF();
        d.set(screenX/2 - 200, screenY/2 + 200);
        f.set(screenX/2 + 200, screenY/2 + 200);
        trumpWall.add(new Wall(d, f));

        d = new PointF();
        f = new PointF();
        d.set(screenX/2 + 200, screenY/2 + 200);
        f.set(screenX/2 + 200, screenY/2 - 200);
        trumpWall.add(new Wall(d, f));

        d = new PointF();
        f = new PointF();
        d.set(screenX/2 + 200, screenY/2 - 200);
        f.set(screenX/2 - 200, screenY/2 - 200);
        trumpWall.add(new Wall(d, f));

        return trumpWall;
    }
}
