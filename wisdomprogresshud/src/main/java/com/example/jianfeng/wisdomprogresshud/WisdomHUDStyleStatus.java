package com.example.jianfeng.wisdomprogresshud;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class WisdomHUDStyleStatus {

    // 主题风格
    public static final int WisdomHUDStyle_Black = 0; // 黑色

    public static final int WisdomHUDStyle_White = 1; // 白色


    @Documented                             //表示开启Doc文档
    @IntDef({ WisdomHUDStyle_Black,WisdomHUDStyle_White})

    @Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.METHOD,})//表示注解作用范围，参数注解，成员注解，方法注解

    @Retention(RetentionPolicy.SOURCE)      //表示注解所存活的时间,在运行时,而不会存在 .class 文件中

    public @interface HUDStyleStatus {      //接口，定义新的注解类型

    }

    @HUDStyleStatus
    public final int state;

    public WisdomHUDStyleStatus(@HUDStyleStatus int state) {
        this.state = state;
    }
}
