package tw.supra.lib.supower.storage;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by supra on 16-1-27.
 * 用于包装本地数据持久化方案的抽象接口;
 * 目前支持SQLiteDatabase和Preferences两种存储方式
 */
public interface LocalData<P> {
    /**
     * 得到数据的名称
     * @return 数据的名称
     */
    String getName();

    /**
     * 得到数据结构的版本
     * @return 数据结构的版本
     */
    int getVersion();

    /**
     * 获得刻度写的关系型数据库对象
     *
     * @return 本地数据的db对象
     */
    SQLiteDatabase getDb();

    /**
     * 获得建值对结构的数据对象
     *
     * @return 本地建值对结构数据的数据对象
     */
    P getPreferences();

    /**
     * 关闭被打开的本地数据
     */
    void close();

}
