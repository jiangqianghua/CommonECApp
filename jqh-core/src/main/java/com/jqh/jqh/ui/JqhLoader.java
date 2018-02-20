package com.jqh.jqh.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.jqh.jqh.R;
import com.jqh.jqh.utils.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by jiangqianghua on 18/2/20.
 */

public class JqhLoader {

    private static final int LOAD_SIZE_SCALE = 8 ;
    private static final int LOADER_OFFSET_SCALE = 10 ;

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotateIndicator.name();
    public static void showLoading(Context context , String type){
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoadCreator.create(type,context);
        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogwindow = dialog.getWindow();

        if(dialogwindow != null){
            WindowManager.LayoutParams lp = dialogwindow.getAttributes();
            lp.width = deviceWidth/LOAD_SIZE_SCALE;
            lp.height = deviceHeight/LOAD_SIZE_SCALE;
            lp.height = lp.height+deviceHeight/LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context,Enum<LoaderStyle> style){
        showLoading(context,style.name());
    }
    public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
    }

    public static void stopLoading(){
        for(AppCompatDialog dialog:LOADERS){
            if(dialog != null){
                if(dialog.isShowing()){
                    dialog.cancel();
                   // dialog.dismiss();
                }
            }
        }
    }
}
