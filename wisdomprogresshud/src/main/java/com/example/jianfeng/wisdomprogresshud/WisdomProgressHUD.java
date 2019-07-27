package com.example.jianfeng.wisdomprogresshud;

import android.content.Context;
import android.view.ViewGroup;


public class WisdomProgressHUD {

    /*  Only words without pictures. (只有文字没有图片) */
    public static final int Default = 0;

    public static final int Succee = 1;

    public static final int Error = 2;

    public static final int Warning = 3;

    /**
     *  Time-consuming tasks do not automatically disappear. (耗时任务，不会自动消灭任务)
     *  I need to manually call 'dismiss'.                  （需要手动调用 'dismiss'）
     */
    public static final int Loading = 4;



    /*  Setting TextSize: pd, Cannot be less than minimum 13 fonts */
    public static float HUDTextSize = 13;


    /*  Setting showTime: long, Cannot be less than minimum 2500 long */
    public static long HUDShowTime = 2500;


    /** task end handler task. （任务结束回调） */
    public interface FinishHandler {
        void finish();
    }


    /**
     *  ----start show -----
     *
     *  showState：         task type, defoult value 'Default'.      (任务类型)
     *  context ：          'Context'  value.
     *  text:               show textView title value.               (文字)
     */
    public static void start(int showState, Context context, String text){
        WisdomHUDManager.start(showState,context,text);
    }


    /**
     *  ----start show with 'WisdomProgressHUD.FinishHandler'-----
     *
     *  showState：         task type, defoult value 'Default'.      (任务类型)
     *  context ：          'Context' value.
     *  text:               show textView title value.                (文字)
     *  finishHandler：     task end handler task.                    (任务结束回调)
     */
    public static void start(int showState, Context context, String text, WisdomProgressHUD.FinishHandler finishHandler){
        WisdomHUDManager.start(showState,context,text,finishHandler);
    }


    /**
     *  ---- start show OnCreate -----
     *  Use the API when a prompt needs to be loaded in 'OnCreate'.（在 ‘OnCreate’ 中需要加载提示时使用API）
     *
     *  showState：         task type, defoult value 'Default'.       (任务类型)
     *  context ：          'Context'.
     *  text:               show textView title value.                (文字)
     *  ViewGroup：         The 'Context' rootLayout.                 (Context的底层布局)
     */
    public static void startOnCreate(int showState, Context context, String text, ViewGroup rootView){
        WisdomHUDManager.startOnCreate(showState,context,text,rootView);
    }


    /**
     *  ---- start show OnCreate with 'WisdomProgressHUD.FinishHandler' -----
     *  Use the API when a prompt needs to be loaded in 'OnCreate'.（在 ‘OnCreate’ 中需要加载提示时使用API）
     *
     *  showState：         task type, defoult value 'Default'.       (任务类型)
     *  context ：          'Context'.
     *  text:               show textView title value.                (文字)
     *  ViewGroup：         The 'Context' rootLayout.                 (Context的底层布局)
     *  finishHandler：     task end handler task.                    (任务结束回调)
     */
    public static void startOnCreate(int showState, Context context, String text, ViewGroup rootView, WisdomProgressHUD.FinishHandler finishHandler){
        WisdomHUDManager.startOnCreate(showState,context,text,rootView,finishHandler);
    }


    /**
     *  -----  after task -----
     *  delay: after task time value.
     */
    public static void after(final int showState, final Context context, final String text, long delay){

        WisdomHUDTimer.after(delay, new WisdomHUDTimer.WisdomTimerHandler() {
            @Override
            public void afterHandler() {
                WisdomProgressHUD.start(showState,context,text);
            }
        });
    }


    /**
     *  -----  after task -----
     *  delay: after task time value.
     */
    public static void after(final int showState, final Context context, final String text, final long delay, final WisdomProgressHUD.FinishHandler finishHandler){

        WisdomHUDTimer.after(delay, new WisdomHUDTimer.WisdomTimerHandler() {
            @Override
            public void afterHandler() {
                WisdomProgressHUD.start(showState,context,text,finishHandler);
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
