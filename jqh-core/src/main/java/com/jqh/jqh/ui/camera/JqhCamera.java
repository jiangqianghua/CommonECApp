package com.jqh.jqh.ui.camera;

import android.net.Uri;

import com.jqh.jqh.deletegates.PermissionCheckerDelegate;
import com.jqh.jqh.utils.file.FileUtil;

/**
 *相机调用
 */
public class JqhCamera {

    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).benginCameraDialog();
    }
}
