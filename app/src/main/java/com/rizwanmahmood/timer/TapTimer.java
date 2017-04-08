package com.rizwanmahmood.timer;


import java.util.Timer;
import java.util.TimerTask;

public class TapTimer {
    private long timePassed;
    private Timer timer;
    private TimerTask timerTask;

    public TapTimer() {
        timePassed = 0;
    }

    public void start(){
        timePassed = 0;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                timePassed++;
            }
        };
        //run timerTask every millisecond
        timer.scheduleAtFixedRate(timerTask, 1, 1);

    }

    public long stop(){
        timer.cancel();
        timer.purge();
        timerTask.cancel();
        return timePassed;
    }
}
