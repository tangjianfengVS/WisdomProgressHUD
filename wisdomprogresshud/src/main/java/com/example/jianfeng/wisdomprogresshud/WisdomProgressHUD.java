package com.example.jianfeng.wisdomprogresshud;

import android.content.Context;
import android.view.ViewGroup;


public class WisdomProgressHUD {

    public static final int Default = 0;

    public static final int Succee = 1;

    public static final int Error = 2;

    public static final int Warning = 3;
    
    public static final int Loading = 4;


    /** task end handler task.（任务结束回调） */
    public interface FinishHandler {
        void finish();
    }


    /**
     *  ----start show -----
     *
     *  showState：     task type, defoult value 'Default'.      (任务类型)
     *  context ：      'Context'.
     *  text:           show textView title value.               (文字)
     */
    public static void start(int showState, Context context,String text){
        WisdomHUDManager.start(showState,context,text);
    }


    /**
     *  ----start show with 'WisdomProgressHUD.FinishHandler'-----
     *
     *  showState：     task type, defoult value 'Default'.      (任务类型)
     *  context ：      'Context'.
     *  text:           show textView title value.               (文字)
     *  finishHandler： task end handler task.                   (任务结束回调)
     */
    public static void start(int showState, Context context, String text, WisdomProgressHUD.FinishHandler finishHandler){
        WisdomHUDManager.start(showState,context,text,finishHandler);
    }


    /**
     *  ---- start show OnCreate -----
     *
     *  showState：    task type, defoult value 'Default'.       (任务类型)
     *  context ：     'Context'.
     *  text:          show textView title value.                (文字)
     *  ViewGroup：    The 'Context' rootLayout.                 (Context的底层布局)
     *  remake：       Use the API when a prompt needs to be loaded in 'OnCreate'。
     *                （在 ‘OnCreate’ 中需要加载提示时使用API）
     */
    public static void startOnCreate(int showState, Context context, String text, ViewGroup rootView){
        WisdomHUDManager.startOnCreate(showState,context,text,rootView);
    }


    /**
     *  ---- start show OnCreate with 'WisdomProgressHUD.FinishHandler' -----
     *
     *  showState：      task type, defoult value 'Default'.       (任务类型)
     *  context ：      'Context'.
     *  text:            show textView title value.                (文字)
     *  ViewGroup：      The 'Context' rootLayout.                 (Context的底层布局)
     *  finishHandler：  task end handler task.                    (任务结束回调)
     *  remake：         Use the API when a prompt needs to be loaded in 'OnCreate'。
     *                  （在 ‘OnCreate’ 中需要加载提示时使用API）
     */
    // start show OnCreate with 'WisdomProgressHUD.FinishHandler'
    public static void startOnCreate(int showState, Context context, String text, ViewGroup rootView, WisdomProgressHUD.FinishHandler finishHandler){
        WisdomHUDManager.startOnCreate(showState,context,text,rootView,finishHandler);
    }


    /**
     *  dismiss（手动释放）
     */
    public static void dismiss() {
        WisdomHUDManager.dismiss();
    }

}
