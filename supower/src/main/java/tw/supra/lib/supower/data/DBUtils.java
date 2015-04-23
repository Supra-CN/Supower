
package tw.supra.lib.supower.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtils {
    private static final String LOG_TAG = DBUtils.class.getSimpleName();

    public synchronized static boolean checkCursor(Cursor c) {
        return !(null == c || c.isClosed() || c.isBeforeFirst() || c.isAfterLast() || c.getCount() < 1);
    }

    public synchronized static Cursor query(SQLiteDatabase db, String table, String[] columns,
            String selection, String[] selectionnArgs, String groupBy, String orderBy) {
        Cursor cursor = db.query(table, columns, selection, selectionnArgs, groupBy, null, orderBy);
        return cursor;
    }

    public synchronized static Cursor query(SQLiteDatabase db, String tableName, String[] columns,
            String selection, String[] selectionnArgs) {
        Cursor cursor = db.query(tableName, columns, selection, selectionnArgs, null, null, null,
                null);
        return cursor;
    }

    public synchronized static void updateOrInsert(SQLiteDatabase db, String table,
            ContentValues values, String whereClause, String[] whereArgs) {
        int update = db.update(table, values, whereClause, whereArgs);
        if (update < 1) {
            long insert = db.insertWithOnConflict(table, null, values,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    public static String getStrByCol(Cursor c, String col) {
        return c.getString(c.getColumnIndex(col));
    }

    public static int getIntByCol(Cursor c, String col) {
        return c.getInt(c.getColumnIndex(col));
    }

    public static long getLongByCol(Cursor c, String col) {
        return c.getLong(c.getColumnIndex(col));
    }

    // public synchronized static void insertOrReplace(SQLiteDatabase db, String table,
    // ArrayList<ContentValues> dataArray) {
    //
    // Log.i(LOG_TAG, "insert to " + table);
    // for (ContentValues data : dataArray) {
    // long insert = db.insertWithOnConflict(table, null, data,
    // SQLiteDatabase.CONFLICT_REPLACE);
    // Log.i(LOG_TAG, "insert:" + insert);
    // }
    // }

    // public synchronized static void updateOrInsert(SQLiteDatabase db, String
    // table, ArrayList<ContentValues> dataArray){
    // Log.i(LOG_TAG, "insert to " + table);
    // String whereClause = ""
    // for (ContentValues data : dataArray) {
    // int update = db.update(table, data, whereClause, whereArgs)
    // db.updateWithOnConflict(table, values, whereClause, whereArgs,
    // conflictAlgorithm)
    //
    // long insert = db.insertOrThrow(table, null, data);
    // Log.i(LOG_TAG, "insert:" + insert);
    // db.updateWithOnConflict(table, data, null, null,
    // SQLiteDatabase.CONFLICT_REPLACE);
    // }
    // }
    // }
}
