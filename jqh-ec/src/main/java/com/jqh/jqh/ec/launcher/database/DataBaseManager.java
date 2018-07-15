package com.jqh.jqh.ec.launcher.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by jiangqianghua on 18/3/25.
 */

public class DataBaseManager {

    private DaoSession mDaoSession = null ;

    private UserProfileDao mDao = null ;

    private DataBaseManager(){

    }
    public DataBaseManager init(Context context){
        initDao(context);
        return this;
    }

    private static final class Holder{
        private static final DataBaseManager INSTANCE = new DataBaseManager();
    }

    public static DataBaseManager getInstance(){
        return Holder.INSTANCE;
    }
    private void initDao(Context context){
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context,"fast_ec.db");
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mDao = mDaoSession.getUserProfileDao();
    }

    public UserProfileDao getDao() {
        return mDao;
    }
}
