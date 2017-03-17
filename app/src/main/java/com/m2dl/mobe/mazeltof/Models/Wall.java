package com.m2dl.mobe.mazeltof.Models;

import android.graphics.PointF;

/**
 * Created by Blue on 17/03/2017.
 */

public class Wall {
    private PointF PointD;
    private PointF PointF;

    public void setPointD(android.graphics.PointF pointD) {
        PointD = pointD;
    }

    public void setPointF(android.graphics.PointF pointF) {
        PointF = pointF;
    }

    public android.graphics.PointF getPointD() {

        return PointD;
    }

    public android.graphics.PointF getPointF() {
        return PointF;
    }

    public Wall(float xDepart, float yDepart, float xFin, float yFin){
        this.PointD.set(xDepart,yDepart);
        this.PointF.set(xFin,yFin);

    }
}
