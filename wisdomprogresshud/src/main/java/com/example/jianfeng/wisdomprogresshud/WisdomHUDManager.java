package com.example.jianfeng.wisdomprogresshud;


import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.jianfeng.wisdomprogresshud.WisdomProgressHUD.CurrentHUDShowTime;


public class WisdomHUDManager {

    @WisdomHUDStatus.HUDStatus
    private int showState = WisdomHUDStatus.Text;

    private final long MinShowTime = 1000;

    private static WisdomHUDManager instance = null;

    private WisdomProgressHUD.WisdomHUDFinishHandler finishHandler;

    private WisdomHUDHintView hintView;

    private ViewGroup containerView;

    private Boolean isHistoryAddApplication = false; // 是否添加到Application

    private Timer timer;


    public static WisdomHUDManager shared(){
        synchronized (WisdomHUDManager.class) {
            if (instance == null) {
                instance = new WisdomHUDManager();
            }
        }
        return instance;
    }


    /**
     *  start on Application
     */
    public static void start_onApplication(@WisdomHUDStatus.HUDStatus final int showState,
                                           final Context context,
                                           final String text,
                                           final WisdomProgressHUD.WisdomHUDFinishHandler finishHandler){
        WisdomProgressHUD.goMainLooper(new WisdomProgressHUD.WisdomHUDFinishHandler() {
            @Override
            public void finish() {
                WisdomHUDManager hud = WisdomHUDManager.shared();
                hud.showState = showState;
                hud.finishHandler = finishHandler;
                Context applicationContext = context.getApplicationContext();
                hud.setupUI(applicationContext, context,null, text);
            }
        });
    }


    /**
     *  start on Application
     */
    public static void start(@WisdomHUDStatus.HUDStatus final int showState,
                             final Context context,
                             final ViewGroup containerView,
                             final String text,
                             final WisdomProgressHUD.WisdomHUDFinishHandler finishHandler){
        WisdomProgressHUD.goMainLooper(new WisdomProgressHUD.WisdomHUDFinishHandler() {
            @Override
            public void finish() {
                WisdomHUDManager hud = WisdomHUDManager.shared();
                hud.showState = showState;
                hud.finishHandler = finishHandler;
                hud.setupUI(null, context,containerView, text);
            }
        });
    }


    // applicationContext 与 containerView 不同时存在
    private void setupUI(Context applicationContext, Context context, ViewGroup containerView, String text){
        if (this.containerView != containerView){
            remove();
        }else if((applicationContext != null && !this.isHistoryAddApplication) || (applicationContext == null && this.isHistoryAddApplication)){
            remove();
        }

        this.containerView = containerView;

        if (hintView == null) {
            hintView = new WisdomHUDHintView(context);

            if (this.containerView != null) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                this.containerView.addView(hintView, layoutParams);

                this.isHistoryAddApplication = false;

            }else if(applicationContext != null) {
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                /** 该Type描述的是形成的窗口的层级关系，下面会详细列出它的属性 */
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;

                /** 设置窗口的位置 */
                layoutParams.gravity = Gravity.CENTER;

                /** 不设置这个弹出框的透明遮罩显示为黑色 */
                layoutParams.format = PixelFormat.TRANSLUCENT;

                /** 窗口的宽 */
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;

                /** 窗口的高 */
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                WindowManager mWindowManager = (WindowManager)hintView.getContext().getSystemService(Context.WINDOW_SERVICE);
                mWindowManager.addView(hintView, layoutParams);

                this.isHistoryAddApplication = true;
            }else {

            }
        }

        hintView.update(showState, text);

        startTimer();
    }


    private void startTimer() {
        if (timer != null){
            timer.cancel();
        }

        if (showState == WisdomHUDStatus.Loading){
            return;
        }

        TimerTask currentTask = new TimerTask() {
            @Override
            public void run(){
                try{
                    WisdomProgressHUD.goMainLooper(new WisdomProgressHUD.WisdomHUDFinishHandler() {
                        @Override
                        public void finish() {
                            removeHUD();
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        long time = CurrentHUDShowTime >= MinShowTime? CurrentHUDShowTime:MinShowTime;
        timer = new Timer();
        timer.schedule(currentTask, time);
    }


    private void removeHUD() {
        remove();

        if (finishHandler != null) {
            finishHandler.finish();
            finishHandler = null;
        }
    }


    // 移除
    private void remove(){
        if (this.hintView != null) {
            if (this.containerView != null){
                this.containerView.removeView(this.hintView);
            }else {
                WindowManager mWindowManager = (WindowManager)hintView.getContext().getSystemService(Context.WINDOW_SERVICE);
                mWindowManager.removeView(this.hintView);
            }
        }

        this.hintView = null;
        this.containerView = null;
        this.isHistoryAddApplication = false;
    }


    public static void dismiss() {
        WisdomProgressHUD.goMainLooper(new WisdomProgressHUD.WisdomHUDFinishHandler() {
            @Override
            public void finish() {
                WisdomHUDManager.shared().removeHUD();
            }
        });
    }
}
