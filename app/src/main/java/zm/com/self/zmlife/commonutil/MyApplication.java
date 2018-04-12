package zm.com.self.zmlife.commonutil;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

import zm.com.self.zmlife.datebasedao.DaoMaster;
import zm.com.self.zmlife.datebasedao.DaoSession;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MyApplication extends Application {
    private DaoSession daoSession;
    private SQLiteDatabase db;
    public static MyApplication instances;
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this)
                .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                          //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
        instances=this;
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper mHelper=new DaoMaster.DevOpenHelper(this,"userrecord.db");
        db=mHelper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(db);
        daoSession=daoMaster.newSession();
    }

    /***
     * 获取DaoSession
     * @return
     */
    public DaoSession getDaoSession(){
        return daoSession;
    }

    public SQLiteDatabase getDb(){
        return db;
    }
}
