package com.example.jianfeng.wisdomprogresshud;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;


public class WisdomProgressHUD {

    /* 【1】：UI 主题风格类型，默认未 'WisdomHUDStyle_Black' */
    @WisdomHUDStyleStatus.HUDStyleStatus
    public static int CurrentHUDStyleState = WisdomHUDStyleStatus.WisdomHUDStyle_Black;


    /* 【2】：Setting showTime: long, Cannot be less than minimum 1000 long */
    public static long CurrentHUDShowTime = 2500;


    /* 【3】：Setting HUDTextSize: long, minSize = 10, maxSize = 18 */
    public static int CurrentHUDTextSize = 13;


    /**
     * task end handler task. （任务结束回调）
     */
    public interface WisdomHUDFinishHandler {
        void finish();
    }


    /**
     *  ----start on Application -----
     *
     *  showState：         task type, defoult value 'Default'.      (任务类型)
     *  text:               show textView title value.               (文字)
     */
    public static void start_onApplication(@WisdomHUDStatus.HUDStatus int showState, Context context, String text){
        WisdomHUDManager.start_onApplication(showState,context,text,null);
    }


    /**
     *  ----start on Application -----
     *
     *  showState：         task type, defoult value 'Default'.      (任务类型)
     *  text:               show textView title value.               (文字)
     *  finishHandler：     task end handler task.                   (任务结束回调)
     */
    public static void start_onApplication(@WisdomHUDStatus.HUDStatus int showState, Context context, String text, WisdomHUDFinishHandler finishHandler){
        WisdomHUDManager.start_onApplication(showState,context,text,finishHandler);
    }


    /**
     *  ----start on ViewGroup -----
     *
     *  showState：         task type, defoult value 'Default'.      (任务类型)
     *  context ：          'Context'.
     *  containerView：     The 'Context' subView.                   (布局容器)
     *  text:               show textView title value.               (文字)
     */
    public static void start(@WisdomHUDStatus.HUDStatus int showState, Context context, ViewGroup containerView, String text){
        WisdomHUDManager.start(showState,context,containerView,text,null);
    }


    /**
     *  ----start on ViewGroup -----
     *
     *  showState：         task type, defoult value 'Default'.      (任务类型)
     *  context ：          'Context'.
     *  containerView：     The 'Context' subView.                   (布局容器)
     *  text:               show textView title value.               (文字)
     *  finishHandler：     task end handler task.                   (任务结束回调)
     */
    public static void start(@WisdomHUDStatus.HUDStatus int showState, Context context, ViewGroup containerView, String text, WisdomHUDFinishHandler finishHandler){
        WisdomHUDManager.start(showState,context,containerView,text,finishHandler);
    }


    /**
     *  -----  after start task on viewGroup -----
     *  delay: after task time value.
     */
    public static void after_start(@WisdomHUDStatus.HUDStatus final int showState, final Context context, final ViewGroup containerView, final String text, long delay, final WisdomHUDFinishHandler finishHandler){

        WisdomHUDTimer.after(delay, new WisdomHUDTimer.WisdomTimerHandler() {
            @Override
            public void afterHandler() {
                WisdomHUDManager.start(showState,context,containerView,text,finishHandler);
            }
        });
    }


    /**
     *  -----  after start task on application -----
     *  delay: after task time value.
     */
    public static void after_start_onApplication(@WisdomHUDStatus.HUDStatus final int showState, final Context context, final String text, long delay, final WisdomHUDFinishHandler finishHandler){

        WisdomHUDTimer.after(delay, new WisdomHUDTimer.WisdomTimerHandler() {
            @Override
            public void afterHandler() {
                WisdomHUDManager.start_onApplication(showState,context,text,finishHandler);
            }
        });
    }


    /* go main looper */
    public static void goMainLooper(final WisdomProgressHUD.WisdomHUDFinishHandler finishHandler){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (finishHandler != null){
                    finishHandler.finish();
                }
            }
        });
    }


    /**
     *  dismiss（手动释放）
     */
    public static void dismiss() {
        WisdomHUDManager.dismiss();
    }

}
