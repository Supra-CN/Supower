package tw.supra.lib.supower.storage;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

/**
 * 数据库操作工具类
 */
public class DBUtils {
    /**
     * log tag
     */
    private static final String LOG_TAG = DBUtils.class.getSimpleName();
    /**
     * 外键支持sql使能语句
     */
    private static final String SQL_ENABLE_DB_FOREIGN_KEY = "PRAGMA foreign_keys=ON;";
    /**
     * 外键支持sql使非语句
     */
    private static final String SQL_DISABLE_DB_FOREIGN_KEY = "PRAGMA foreign_keys=OFF;";


    /**
     * 私有构造方法
     */
    private DBUtils() {
    }


    /**
     * 检查Cursor对象是否有效且非空
     *
     * @param c 目标Cursor对象
     * @return 目标Cursor对象是否有效且非空则为真
     */
    public synchronized static boolean checkCursor(Cursor c) {
        return !(null == c || c.isClosed() || c.isBeforeFirst() || c.isAfterLast() || c.getCount() < 1);
    }

    /**
     * 更新或插入数据;
     * 先尝试更新数据，如果更新的记录行数小于1,则插入该行;
     *
     * @param db          目标DB
     * @param table       the table to update or insert
     * @param values      a map from column names to new column values. null is a
     *                    valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating.
     *                    Passing null will update all rows.
     * @param whereArgs   You may include ?s in the where clause, which
     *                    will be replaced by the values from whereArgs. The values
     *                    will be bound as Strings.
     * @return 影响的行数 the number of rows affected
     */
    public synchronized static int updateOrInsert(SQLiteDatabase db, String table, ContentValues values, String
            whereClause, String[] whereArgs) {
        int update = db.update(table, values, whereClause, whereArgs);
        if (update < 1) {
            long insert = db.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            return (insert < 0) ? 0 : 1;
        }
        return update;
    }

    /**
     * 按列名取Type
     * Returns data type of the given column's value.
     * The preferred type of the column is returned but the data may be converted to other types
     * as documented in the get-type methods such as {@link Cursor#getInt(int)}, {@link Cursor#getFloat(int)}
     * etc.
     * <p>
     * Returned column types are
     * <ul>
     * <li>{@link Cursor#FIELD_TYPE_NULL}</li>
     * <li>{@link Cursor#FIELD_TYPE_INTEGER}</li>
     * <li>{@link Cursor#FIELD_TYPE_FLOAT}</li>
     * <li>{@link Cursor#FIELD_TYPE_STRING}</li>
     * <li>{@link Cursor#FIELD_TYPE_BLOB}</li>
     * </ul>
     * </p>
     *
     * @param c   目标Cursor
     * @param col 列名
     * @return column value type 当前记录在列名处对应的Type
     */
    public static long getTypeByCol(Cursor c, String col) {
        return c.getType(c.getColumnIndex(col));
    }

    /**
     * 按列名取String值
     *
     * @param c   目标Cursor
     * @param col 列名
     * @return 当前记录在列名处对应的String值
     */
    public static String getStrByCol(Cursor c, String col) {
        return c.getString(c.getColumnIndex(col));
    }

    /**
     * 按列名取int值
     *
     * @param c   目标Cursor
     * @param col 列名
     * @return 当前记录在列名处对应的int值
     */
    public static int getIntByCol(Cursor c, String col) {
        return c.getInt(c.getColumnIndex(col));
    }

    /**
     * 按列名取long值
     *
     * @param c   目标Cursor
     * @param col 列名
     * @return 当前记录在列名处对应的long值
     */
    public static long getLongByCol(Cursor c, String col) {
        return c.getLong(c.getColumnIndex(col));
    }

    /**
     * 按列名取short值
     *
     * @param c   目标Cursor
     * @param col 列名
     * @return 当前记录在列名处对应的short值
     */
    public static short getShortByCol(Cursor c, String col) {
        return c.getShort(c.getColumnIndex(col));
    }

    /**
     * 按列名取Blob值
     *
     * @param c   目标Cursor
     * @param col 列名
     * @return 当前记录在列名处对应的Blob值
     */
    public static byte[] getBlobByCol(Cursor c, String col) {
        return c.getBlob(c.getColumnIndex(col));
    }

    /**
     * 按列名取double值
     *
     * @param c   目标Cursor
     * @param col 列名
     * @return 当前记录在列名处对应的double值
     */
    public static double getDoubleByCol(Cursor c, String col) {
        return c.getDouble(c.getColumnIndex(col));
    }

    /**
     * 按列名取float值
     *
     * @param c   目标Cursor
     * @param col 列名
     * @return 当前记录在列名处对应的float值
     */
    public static float getFloatByCol(Cursor c, String col) {
        return c.getFloat(c.getColumnIndex(col));
    }

    /**
     * 为目标数据库设置外键支持
     *
     * @param db     目标数据库对象
     * @param enable 是否支持外键
     */
    public void setForeignKeyConstraintsEnabled(SQLiteDatabase db, boolean enable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledBySql(db, enable);
        } else {
            setForeignKeyConstraintsEnabledByNative(db, enable);
        }
    }

    /**
     * 采用SQL语句为目标数据库设置外键支持
     *
     * @param db     目标数据库对象
     * @param enable 是否支持外键
     */
    private void setForeignKeyConstraintsEnabledBySql(SQLiteDatabase db, boolean enable) {
        db.execSQL(enable ? SQL_ENABLE_DB_FOREIGN_KEY : SQL_DISABLE_DB_FOREIGN_KEY);
    }

    /**
     * 采用JELLY_BEAN提供的方法为目标数据库设置外键支持
     *
     * @param db     目标数据库对象
     * @param enable 是否支持外键
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledByNative(SQLiteDatabase db, boolean enable) {
        db.setForeignKeyConstraintsEnabled(enable);
    }
}
