package com.jqh.jqh.app;

import com.jqh.jqh.utils.storage.JqhPreference;

/**
 * Created by jiangqianghua on 18/3/26.
 */

public class AccountManager {

    private enum SignTag{
        SIGN_TAG
    }

    public static void setSignState(boolean state){
        JqhPreference.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }

    public static boolean isSignIn(){
        return JqhPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker){
        if(isSignIn()){
            checker.onSignIn();
        }
        else{
            checker.noNotSignIn();
        }
    }
}
