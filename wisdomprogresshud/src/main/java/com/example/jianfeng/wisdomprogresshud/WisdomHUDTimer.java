package com.example.jianfeng.wisdomprogresshud;

import android.os.Handler;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;

public class WisdomHUDTimer {

    public interface WisdomTimerHandler{

        void afterHandler();
    }


    static void after(long delay, final WisdomTimerHandler afterHandler ){
        final TimerTask task = new TimerTask() {

            @Override
            public void run() {

                Handler mainThread = new Handler(Looper.getMainLooper());
                mainThread.post(new Runnable() {
                    @Override
                    public void run(){
                        if (afterHandler != null){
                            afterHandler.afterHandler();
                        }
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, delay);
    }

}
