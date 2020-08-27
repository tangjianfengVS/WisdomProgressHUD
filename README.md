# WisdomProgressHUD

    一：简述（za shi chinese，为了方便大家阅读，尽量使用中文备注了）
       WisdomProgressHUD 是一个半透明的 HUD 指示器。 WisdomProgressHUD 是android 版SDK，由java编写，保证兼容性问题。 
       全局HUD单列对象，支持属性动态调整, 支持延时调用，无需开发关心释放问题，是一个强大的HUD库，使用简介，操作方便。
       下面对功能进行分析。


       集成方案：

       allprojects {
           repositories {
               google()
               jcenter()
               // 添加支持 jitpack
               maven { url 'https://jitpack.io' }
           }
       }

       dependencies {
           // WisdomProgressHUD writer: tangjianfeng
           implementation 'com.github.tangjianfengVS:WisdomProgressHUD:1.0.0'
       }
       

    二：WisdomProgressHUD 支持类型
        1:  Text      (只有文字没有图片，默认使用)
        2:  Succee    (有文字，有图片，成功提示)
        3:  Error     (有文字，有图片，失败提示)
        4:  Warning   (有文字，有图片，警告提示)
        5:  Loading   (有文字，有图片，加载中提示) 
                      ---（"Loading" 和上面的类型不同，是耗时任务，不会自动消灭任务，需要手动调用 'dismiss'，结束回调任务也不会实现）


    三：WisdomProgressHUD 属性设置
     /* Setting HUDTextSize: long, minSize = 10, maxSize = 18，说明： 提示时候的文字字体pd大小，设置最小值13号pd */
     1：public static int CurrentHUDTextSize = 13;


     2:  public static long CurrentHUDShowTime = 2500;
        说明： 提示时候的界面显示时间，设置最小值1秒


     3:  public interface WisdomHUDFinishHandler {
            /** task end handler task. （任务结束回调） */
            void finish();
        }


     /* UI 主题风格类型，默认未 'WisdomHUDStyle_Black' */
     4:  @WisdomHUDStyleStatus.HUDStyleStatus
         public static int CurrentHUDStyleState = WisdomHUDStyleStatus.WisdomHUDStyle_Black;


    ......后面有待扩展
   

    四：WisdomProgressHUD 使用分类

      1:  start on Application // 加载在 Application 上

      2:  start on ViewGroup   // 加载在 ViewGroup 上

      3:  延迟使用：after start task 。
    

    五：WisdomProgressHUD 具体API，参数
            /**
             *  ---- 【1】： start on Application -----
             *
             *  showState：         task type, defoult value 'Default'.      (任务类型)
             *  text:               show textView title value.               (文字)
             */
            public static void start_onApplication(@WisdomHUDStatus.HUDStatus int showState, Context context, String text){
                WisdomHUDManager.start_onApplication(showState,context,text,null);
            }


            /**
             *  ---- 【2】：start on Application -----
             *
             *  showState：         task type, defoult value 'Default'.      (任务类型)
             *  text:               show textView title value.               (文字)
             *  finishHandler：     task end handler task.                   (任务结束回调)
             */
            public static void start_onApplication(@WisdomHUDStatus.HUDStatus int showState, Context context, String text, WisdomHUDFinishHandler finishHandler){
                WisdomHUDManager.start_onApplication(showState,context,text,finishHandler);
            }


            /**
             *  ---- 【3】：start on ViewGroup -----
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
             *  ---- 【4】：start on ViewGroup -----
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
             *  ----- 【5】：after start task on viewGroup -----
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
             *  ----- 【6】：after start task on application -----
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


            /*  -----【7】：go main looper */
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
             *  ----- 【8】：dismiss（手动释放）
             */
            public static void dismiss() {
                WisdomHUDManager.dismiss();
            }
    
    
        六：WisdomScreenUtils： 提供屏幕尺寸转换处理功能
            /** 
             *   根据手机的分辨率从 dp 的单位 转成为 px(像素)
             */
             public static int dip2px(Context context, float dpValue) { return (int) }

            /** 
             *   根据手机的分辨率从 px(像素) 的单位 转成为 dp 
             */
            public static int px2dip(Context context, float pxValue) { return (int) }

            /**  获取屏幕密度 */
            public static float getScreenDensity(Context context) { return float }

            /**  获取屏幕宽度(像素) */
            public static int getScreenWidthPixels(Context context) { return int }

            /**  获取屏幕宽度(dp) */
            public static float getScreenWidthDp(Context context) { return float }

            /**  获取屏幕高度(像素) */
            public static int getScreenHeightPixels(Context context) { return int }

            /**  获取屏幕高度(dp) */
            public static float getScreenHeightDp(Context context) { return float }

            /**  获取状态栏高度 */
            public static int getStatusHeight(Context context) { return int }

            /**
             *  保存屏幕截图到本地
             *  @param activity
             *  @param strFileName 文件全路径:例如 "/sdcard/screen_shot_20160424.jpg"
             */
            public static void savScreenShot(Activity activity, String strFileName) { }

            /**
             *  截图
             *  也可以调用shell命令去截图  screencap -p test.png
             *  @param activity 截取activity 所在的页面的截图,即使退到后台也是截取这个activity
             */
            private static Bitmap takeShot(Activity activity) { return Bitmap }
 

     七: Android Studio SDK 集成：
            1: app build.gradle 配置 'https://jitpack.io' :
                   allprojects {
		        repositories {
		    	  maven { url 'https://jitpack.io' }
		         }
	           }
	
            2: app build.gradle 配置 'com.github.tangjianfengVS:WisdomProgressHUD:1.0.0' :
                   dependencies {
	                implementation 'com.github.tangjianfengVS:WisdomProgressHUD:1.0.0'
	           }
    
    
    八：结语：
       WisdomProgressHUD Android SDK，界面设计漂亮，并且是一款金典的HUD，并且支持屏蔽任务期间的用户交互。
       WisdomProgressHUD Android SDK，开发中使用方便，性能高效，自动管理生命周期，兼容'Oncreate'方法中调用，非常推荐给大家使用！
      
    
