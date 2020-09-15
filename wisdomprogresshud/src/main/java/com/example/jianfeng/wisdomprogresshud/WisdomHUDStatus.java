package com.example.jianfeng.wisdomprogresshud;


import android.support.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public class WisdomHUDStatus {

    public static final int Text = 0;

    public static final int Succee = 1;

    public static final int Warning = 2;

    public static final int Error = 3;

    /**
     *  Time-consuming tasks do not automatically disappear. (耗时任务，不会自动消灭任务)
     *  I need to manually call 'dismiss'.                  （需要手动调用 'dismiss'）
     */
    public static final int Loading = 4;

    /**
     *  Similar to the Loading.
     *  'Loading' style  not background bar.
     *
     */
    public static final int Loading_Rotate = 5;


    @Documented                             //表示开启Doc文档
    @IntDef({ Text,Succee,Error,Warning,Loading,Loading_Rotate})

    @Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.METHOD,})//表示注解作用范围，参数注解，成员注解，方法注解

    @Retention(RetentionPolicy.SOURCE)      //表示注解所存活的时间,在运行时,而不会存在 .class 文件中

    public @interface HUDStatus {           //接口，定义新的注解类型

    }

    @HUDStatus
    public int state;

    public WisdomHUDStatus(@HUDStatus int state) {
        this.state = state;
    }

}
