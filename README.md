# WisdomProgressHUD

    一：简述
       WisdomProgressHUD 是一个半透明的 HUD 指示器。 WisdomProgressHUD 是android 版SDK，由java编写，保证兼容性问题。 
       全局HUD单列对象，支持属性动态调整, 支持延时调用，无需开发关心释放问题，是一个强大的HUD库，使用简介，操作方便。


    二：WisdomProgressHUD 支持类型
        1:  Default   (只有文字没有图片，默认使用)
        2:  Succee    (有文字，有图片，成功提示)
        3:  Error     (有文字，有图片，失败提示)
        4:  Warning   (有文字，有图片，警告提示)
        5:  Loading   (有文字，有图片，加载中提示) 
                      ---（"Loading" 和上面的类型不同，是耗时任务，不会自动消灭任务，需要手动调用 'dismiss'，结束回调任务也不会实现）


    三：WisdomProgressHUD 属性设置
    1:  static float HUDTextSize = 13
        说明： 提示时候的文字字体pd大小，设置最小值13号pd

    2:  static float HUDShowTime = 2500
        说明： 提示时候的界面显示时间，设置最小值1秒
    
    3:  public interface FinishHandler {
            /** task end handler task. （任务结束回调） */
            void finish();
        }
    
    ......后面有待扩展
   
   

    四：WisdomProgressHUD 使用分类
    1:  在 "OnCreate" 中的调用：
        说明： 为了解决 android 机制不允许在 "OnCreate" 方法中向屏幕中添加UI问题，所以 "OnCreate"需要用到HUD，请调用 "startOnCreate" 方法。

    2:  普通使用：
        说明： 不在 "OnCreate" 中使用 "start" 方法。

    3:  延迟使用：
        说明：在当前时间延迟使用功能，调用 "after" 方法。
    

    五：WisdomProgressHUD 具体API，参数

    1:
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
    

    2:
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
    

    3:
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


    4:
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

    
    5:
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


    6:
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


    7:
    /**
     *  dismiss（手动释放）
     */
    public static void dismiss() {
        WisdomHUDManager.dismiss();
    }
   

    六 SDK 集成：
      1: gradle build:
      
        allprojects {
		    repositories {
			  ...
			  maven { url 'https://jitpack.io' }
		   }
	    }
      
       dependencies {
	        implementation 'com.github.tangjianfengVS:WisdomProgressHUD:0.0.1'
	   }
    
    
    七：结语：
       WisdomProgressHUD SDK，方便开发使用，高效性能，生命周期自动管理，推荐给大家使用！！！！
    
