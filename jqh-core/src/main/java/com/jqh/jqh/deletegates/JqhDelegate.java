package com.jqh.jqh.deletegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by jiangqianghua on 18/2/19.
 */

public abstract class JqhDelegate extends PermissionCheckerDelegate {

    // 获得父类的fragment
    public <T extends  JqhDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }
}
