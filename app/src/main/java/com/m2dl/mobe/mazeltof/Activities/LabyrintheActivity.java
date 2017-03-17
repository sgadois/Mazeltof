package com.m2dl.mobe.mazeltof.Activities;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.m2dl.mobe.mazeltof.Models.Ball;
import com.m2dl.mobe.mazeltof.Models.Labyrinthe;
import com.m2dl.mobe.mazeltof.R;

import java.util.Timer;
import java.util.TimerTask;
import android.graphics.Canvas;
import android.graphics.Paint;

public class LabyrintheActivity extends AppCompatActivity {

    private Ball mBallView = null;
    private Handler RedrawHandler = new Handler(); //so redraw occurs in main thread
    private Timer mTmr = null;
    private TimerTask mTsk = null;
    private int mScrWidth, mScrHeight;
    private android.graphics.PointF mBallPos, mBallSpd;
    private Labyrinthe labyrinthe;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title bar
        //set app to full screen and keep screen on
        getWindow().setFlags(0xFFFFFFFF,
                WindowManager.LayoutParams.FLAG_FULLSCREEN| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test_ball);

        level = 1; //TEMPORAIRE /!\
        switch(level){
            case 1 :
                readLabyrinth(R.array.level1,R.array.hole1);
                break;
            default:
                break;
        }

        //create pointer to main screen
        final RelativeLayout mainView =
                (android.widget.RelativeLayout)findViewById(R.id.main_view);

        //get screen dimensions
        Display display = getWindowManager().getDefaultDisplay();
        mScrWidth = display.getWidth();
        mScrHeight = display.getHeight();
        mBallPos = new android.graphics.PointF();
        mBallSpd = new android.graphics.PointF();

        //create variables for ball position and speed
        mBallPos.x = mScrWidth/2;
        mBallPos.y = mScrHeight/2;
        mBallSpd.x = 0;
        mBallSpd.y = 0;

        //create initial ball
        mBallView = new Ball(this, mBallPos.x, mBallPos.y, 20);

        mainView.addView(mBallView); //add ball to main screen
        mBallView.invalidate(); //call onDraw in BallView

        //listener for accelerometer, use anonymous class for simplicity
        ((SensorManager)getSystemService(Context.SENSOR_SERVICE)).registerListener(
                new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        //set ball speed based on phone tilt (ignore Z axis)
                        mBallSpd.x = -event.values[0];
                        mBallSpd.y = event.values[1];
                        //timer event will redraw ball
                    }
                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {} //ignore
                },
                ((SensorManager)getSystemService(Context.SENSOR_SERVICE))
                        .getSensorList(Sensor.TYPE_ACCELEROMETER).get(0),
                SensorManager.SENSOR_DELAY_NORMAL);

        //listener for touch event
        mainView.setOnTouchListener(new android.view.View.OnTouchListener() {
            public boolean onTouch(android.view.View v, android.view.MotionEvent e) {
                //set ball position based on screen touch
                mBallPos.x = e.getX();
                mBallPos.y = e.getY();
                //timer event will redraw ball
                return true;
            }});
    }

    //listener for menu button on phone
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add("Exit"); //only one menu item
        return super.onCreateOptionsMenu(menu);
    }

    //listener for menu item clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        if (item.getTitle() == "Exit") //user clicked Exit
            finish(); //will call onPause
        return super.onOptionsItemSelected(item);
    }

    //For state flow see http://developer.android.com/reference/android/app/Activity.html
    @Override
    public void onPause() //app moved to background, stop background threads
    {
        mTmr.cancel(); //kill\release timer (our only background thread)
        mTmr = null;
        mTsk = null;
        super.onPause();
    }

    @Override
    public void onResume() //app moved to foreground (also occurs at app startup)
    {
        //create timer to move ball to new position
        mTmr = new Timer();
        mTsk = new TimerTask() {
            public void run() {

                //move ball based on current speed
                mBallPos.x += mBallSpd.x;
                mBallPos.y += mBallSpd.y;

                //if ball is on border, don't move
                if (mBallPos.x > (mScrWidth - mBallView.r/2)) mBallPos.x=(mScrWidth - mBallView.r/2);
                if (mBallPos.y > (mScrHeight - mBallView.r/2)) mBallPos.y=(mScrHeight - mBallView.r/2);
                if (mBallPos.x < mBallView.r/2) mBallPos.x=mBallView.r/2;
                if (mBallPos.y < mBallView.r/2) mBallPos.y=mBallView.r/2;

                //update ball class instance
                mBallView.x = mBallPos.x;
                mBallView.y = mBallPos.y;

                //redraw ball. Must run in background thread to prevent thread lock.
                RedrawHandler.post(new Runnable() {
                    public void run() {
                        mBallView.invalidate();
                    }});
            }}; // TimerTask
        mTmr.schedule(mTsk,10,10); //start timer
        super.onResume();
    }

    @Override
    public void onDestroy() //main thread stopped
    {
        super.onDestroy();
        //wait for threads to exit before clearing app
        System.runFinalizersOnExit(true);
        //remove app from memory
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    //listener for config change.
    //This is called when user tilts phone enough to trigger landscape view
    //we want our app to stay in portrait view, so bypass event
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    private void readLabyrinth(int lvlId,int holeId) {
        Resources res = this.getResources();
        String[] labyrinthRows = res.getStringArray(lvlId);
        String[] holeRows = res.getStringArray(holeId);

        boolean[][] labyrintheArray = new boolean[labyrinthRows[1].length()][labyrinthRows.length];
        boolean[][] holeArray = new boolean[holeRows[1].length()][holeRows.length];
        for(int i = 0; i<labyrinthRows.length;i++){
            for(int j = 0; j<labyrinthRows[i].length();j++){
                labyrintheArray[i][j] = Character.getNumericValue(labyrinthRows[i].charAt(j)) > 0?true:false;
            }
        }

        for(int i = 0; i<holeRows.length;i++){
            for(int j = 0; j<holeRows[i].length();j++){
                holeArray[i][j] = Character.getNumericValue(holeRows[i].charAt(j)) > 0?true:false;
            }
        }
        labyrinthe = new Labyrinthe(labyrintheArray,holeArray, getApplicationContext());
    }

    public void win(){

    }

}
