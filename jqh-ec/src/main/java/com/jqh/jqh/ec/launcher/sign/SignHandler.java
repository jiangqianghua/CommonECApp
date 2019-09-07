package com.jqh.jqh.ec.launcher.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jqh.jqh.app.AccountManager;
import com.jqh.jqh.ec.launcher.database.DataBaseManager;
import com.jqh.jqh.ec.launcher.database.UserProfile;

/**
 * Created by jiangqianghua on 18/3/26.
 */

public class SignHandler {


    public static void signIn(String response,ISignListener iSignListener){
        try {
            // saveData(response);
            AccountManager.setSignState(true);
            iSignListener.onSignInSuccess();
        }catch (Exception e){
            e.printStackTrace();
            // for test
            iSignListener.onSignInSuccess();
        }
    }

    public static void signUp(String response,ISignListener iSignListener){
        try {
            saveData(response);
            iSignListener.onSignUpSuccess();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void saveData(String response){
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avater = profileJson.getString("avater");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");
        final UserProfile profile = new UserProfile(userId, name, avater, gender, address);
        DataBaseManager.getInstance().getDao().insert(profile);
        AccountManager.setSignState(true);
    }
}
