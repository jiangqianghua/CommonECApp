package com.jqh.jqh.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.jqh.jqh.app.Jqh;

/**
 * Created by jiangqianghua on 18/2/20.
 */

public class DimenUtil {

    public static int getScreenWidth(){
        final Resources resources = Jqh.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public  static int getScreenHeight(){
        final Resources resources = Jqh.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
