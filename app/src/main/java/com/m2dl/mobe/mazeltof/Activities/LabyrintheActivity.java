package com.m2dl.mobe.mazeltof.Activities;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.m2dl.mobe.mazeltof.Models.Ball;
import com.m2dl.mobe.mazeltof.Models.Labyrinthe;
import com.m2dl.mobe.mazeltof.Models.Wall;
import com.m2dl.mobe.mazeltof.R;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class LabyrintheActivity extends AppCompatActivity {

    private Ball mBallView = null;
    private Handler RedrawHandler = new Handler(); //so redraw occurs in main thread
    private Timer mTmr = null;
    private int jumpCount;
    private TimerTask mTsk = null;
    private int mScrWidth, mScrHeight;
    private android.graphics.PointF mBallPos, mBallSpd;
    private Labyrinthe labyrinthe;
    private int level;

    private int millisecond;
    private int centieme;
    private int second;
    private int minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //set app to full screen and keep screen on
        getWindow().setFlags(0xFFFFFFFF,
                WindowManager.LayoutParams.FLAG_FULLSCREEN| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test_ball);


        millisecond = 0;
        centieme = 0;
        second = 0;
        minute = 0;

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
        mainView.addView(labyrinthe);
        labyrinthe.invalidate();
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
        /*mainView.setOnTouchListener(new android.view.View.OnTouchListener() {
            public boolean onTouch(android.view.View v, android.view.MotionEvent e) {
                //set ball position based on screen touch
                mBallPos.x = e.getX();
                mBallPos.y = e.getY();
                //timer event will redraw ball
                return true;
            }});*/
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
    public void onResume() {
        //create timer to move ball to new position
        mTmr = new Timer();
        mTsk = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        //update time
                        millisecond += 10;
                        if(millisecond == 10) {
                            centieme += 1;
                            millisecond = 0;
                        }
                        if(centieme == 100) {
                            second += 1;
                            centieme = 0;
                        }
                        if(second == 60) {
                            minute += 1;
                            second = 0;
                        }

                        //jump counter
                        if(mBallView.isJump()){
                            jumpCount++;
                            if(jumpCount==100){
                                jumpCount=0;
                                mBallView.fall();
                                ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_RING,ToneGenerator.MAX_VOLUME);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,200);
                            }
                        }

                        TextView timer = (TextView) findViewById(R.id.time);
                        timer.setText(String.format("%02d", minute) + ":" + String.format("%02d", second) + ":" + String.format("%02d", centieme));

                        //move ball based on current speed
                        float tempx = mBallPos.x;
                        float tempy = mBallPos.y;

                        mBallPos.x += mBallSpd.x;
                        mBallPos.y += mBallSpd.y;

                        //if ball is on border, don't move
                       /* if (mBallPos.x > (mScrWidth - mBallView.r / 2))
                            mBallPos.x = (mScrWidth - mBallView.r / 2);
                        if (mBallPos.y > (mScrHeight - mBallView.r / 2))
                            mBallPos.y = (mScrHeight - mBallView.r / 2);
                        if (mBallPos.x < mBallView.r / 2) mBallPos.x = mBallView.r / 2;
                        if (mBallPos.y < mBallView.r / 2) mBallPos.y = mBallView.r / 2;*/

                        //if ball touch wall, don't move
                        for(Wall myWall : labyrinthe.getTrumpWall()) {
                            if (mBallPos.x < min(myWall.getPointD().x, myWall.getPointF().x) +  mBallView.r &&
                                    mBallPos.x > min(myWall.getPointD().x, myWall.getPointF().x) - mBallView.r &&
                                    mBallPos.y > min(myWall.getPointD().y, myWall.getPointF().y) &&
                                    mBallPos.y < max(myWall.getPointD().y, myWall.getPointF().y)) {
                                mBallPos.x = tempx;
                            }
                            if (mBallPos.y < min(myWall.getPointD().y, myWall.getPointF().y) + mBallView.r &&
                                    mBallPos.y > min(myWall.getPointD().y, myWall.getPointF().y) - mBallView.r &&
                                    mBallPos.x > min(myWall.getPointD().x, myWall.getPointF().x) &&
                                    mBallPos.x < max(myWall.getPointD().x, myWall.getPointF().x)) {
                                mBallPos.y = tempy;
                            }
                        }

                        //update ball class instance
                        mBallView.x = mBallPos.x;
                        mBallView.y = mBallPos.y;

                        //redraw ball. Must run in background thread to prevent thread lock.
                        RedrawHandler.post(new Runnable() {
                            public void run() {
                                mBallView.invalidate();
                            }
                        });
                        isWin();
                    }
                });
            }};
        mTmr.schedule(mTsk,10,10); //start timer
        super.onResume();
    }

    @Override
    public void onDestroy() {
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void readLabyrinth(int lvlId,int holeId) {
        Resources res = this.getResources();
        String[] labyrinthRows = res.getStringArray(lvlId);
        String[] holeRows = res.getStringArray(holeId);

        boolean[][] labyrintheArray = new boolean[labyrinthRows.length][labyrinthRows[1].length()];
        boolean[][] holeArray = new boolean[holeRows.length][holeRows[1].length()];

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

        labyrinthe = new Labyrinthe(this, labyrintheArray,holeArray);
    }

    public void isWin(){
        if(mBallPos.x>= mScrWidth || mBallPos.y >= mScrHeight){
            //MURIEL LOL C EST ICI QU ON A GAGNE HAHA
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        if(!mBallView.isJump()){
            jumpCount=0;
            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_RING,ToneGenerator.MAX_VOLUME);
            toneGen1.startTone(ToneGenerator.TONE_CDMA_DIAL_TONE_LITE,200);
        }
        this.mBallView.jump();
        return false;
    }

}
