package com.example.jianfeng.wisdomprogresshud;


import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;


import java.util.Timer;
import java.util.TimerTask;

import static com.example.jianfeng.wisdomprogresshud.WisdomProgressHUD.Default;
import static com.example.jianfeng.wisdomprogresshud.WisdomProgressHUD.HUDShowTime;
import static com.example.jianfeng.wisdomprogresshud.WisdomProgressHUD.Loading;


public class WisdomHUDManager {

    // 交互状态 - 默认覆盖
    private boolean coverState = true;

    // 默认覆盖色-黑色60%透明
    private int coverColor = 0x66000000;

    private int showState = Default;

    private final long showTime = 2500;

    private static WisdomHUDManager instance = null;

    private WisdomProgressHUD.FinishHandler showHander;

    private Context context;

    private ViewGroup rootView;

    private WisdomHintView baseView;

    private Timer timer;


    public static WisdomHUDManager shared(){
        if (instance == null ){
            instance = new WisdomHUDManager();
        }
        return instance;
    }


    // 开始展示
    public static void start(int showState, Context context,String text){
        WisdomHUDManager hud = WisdomHUDManager.shared();
        hud.showState = showState;
        hud.showHander = null;
        hud.setupUI(context,text);
    }


    // 开始展示, 显示完成会有回调
    public static void start(int showState, Context context,String text, WisdomProgressHUD.FinishHandler finishHander){
        WisdomHUDManager hud = WisdomHUDManager.shared();
        hud.showState = showState;
        if (showState == Loading){
            hud.showHander = null;
        }else {
            hud.showHander = finishHander;
        }
        hud.setupUI(context,text);
    }


    public static void startOnCreate(int showState, Context context, String text, ViewGroup rootView) {
        WisdomHUDManager hud = WisdomHUDManager.shared();
        hud.showState = showState;
        hud.showHander = null;
        hud.onCreateSetupUI(context,text,rootView);
    }


    public static void startOnCreate(int showState, Context context,String text, ViewGroup rootView, WisdomProgressHUD.FinishHandler finishHander){
        WisdomHUDManager hud = WisdomHUDManager.shared();
        hud.showState = showState;
        if (showState == Loading){
            hud.showHander = null;
        }else {
            hud.showHander = finishHander;
        }
        hud.onCreateSetupUI(context,text,rootView);
    }


    public static void dismiss() {
        WisdomHUDManager.shared().removeHUD();
    }


    private void setupUI(Context context, String text){

        if (baseView == null || this.context != context){
            if (baseView != null) {
                WindowManager mWindowManager = (WindowManager)baseView.getContext().getSystemService(Context.WINDOW_SERVICE);
                mWindowManager.removeView(baseView);
            }

            baseView = WisdomHintView.init(context,showState,text);
            WindowManager mWindowManager = (WindowManager)baseView.getContext().getSystemService(Context.WINDOW_SERVICE);
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

            //获取当前Activity中的View中的TOken,来依附Activity，
            //因为设置了该值，纳闷写的这些代码不能出现在onCreate();否则会报错。
            layoutParams.token = baseView.getWindowToken();
            mWindowManager.addView(baseView, layoutParams);
        }else {
            baseView.update(showState,text);
        }

        this.context = context;

        baseView.setBackgroundColor(coverColor);
        baseView.updateUI();
        setTimer();
    }


    private void onCreateSetupUI(Context context, String text, ViewGroup rootView) {
        if (baseView != null) {
            if (rootView != null){
                rootView.removeView(baseView);
            }else {
                WindowManager mWindowManager = (WindowManager)baseView.getContext().getSystemService(Context.WINDOW_SERVICE);
                mWindowManager.removeView(baseView);
            }
        }

        this.context = context;
        this.rootView = rootView;

        baseView = WisdomHintView.init(context,showState,text);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(baseView,layoutParams);

        baseView.setBackgroundColor(coverColor);
        baseView.updateUI();
        setTimer();
    }


    private void setTimer() {
        if (timer != null){
            timer.cancel();
        }

        if (showState == Loading){
            return;
        }

        TimerTask currentTask = new TimerTask() {
            @Override
            public void run(){
                try{

                    Handler mainThread = new Handler(Looper.getMainLooper());
                    mainThread.post(new Runnable() {
                        @Override
                        public void run(){
                            removeHUD();
                        }
                    });

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        long time = HUDShowTime > showTime? HUDShowTime:showTime;
        timer = new Timer();
        timer.schedule(currentTask,time);
    }


    private void removeHUD() {
        context = null;
        if (timer != null){
            timer.cancel();
            timer = null;
        }

        if (baseView != null) {
            if (rootView != null){
                rootView.removeView(baseView);
            }else {
                WindowManager mWindowManager = (WindowManager)baseView.getContext().getSystemService(Context.WINDOW_SERVICE);
                mWindowManager.removeView(baseView);
            }
            rootView = null;
            baseView = null;
        }

        if (showHander != null) {
            showHander.finish();
            showHander = null;
        }
    }

}
