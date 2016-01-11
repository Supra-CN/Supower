package tw.supra.lib.supower.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;

/**
 * 数据库基类；
 *
 * @author supra
 */
public abstract class LocalData {

    public static final String ENABLE_DB_FOREIGN_KEY = "PRAGMA foreign_keys=ON;";
    private static final String LOG_TAG = LocalData.class.getSimpleName();
    private static final String DB_SUB_FIX = ".db";

    public final int mVersion;
    public final String mName;
    public final Context mCtx;
    private final SQLiteOpenHelper mDbHelper;

    private SQLiteDatabase mTmpDbOnInitializing;

    protected LocalData(Context context, String name, int version) {
        this(context, null, name, version);
    }

    protected LocalData(Context ctx, String prefix, String name, int version) {
        checkName(name);
        checkVersion(version);
        checkContext(ctx);
        mName = name;
        mVersion = version;
        mCtx = ctx.getApplicationContext();
        String dbName = mName.endsWith(DB_SUB_FIX) ? mName : mName + DB_SUB_FIX;
        mDbHelper = new SQLiteOpenHelper(mCtx, dbName, null, mVersion) {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onConfigure(SQLiteDatabase db) {
                super.onConfigure(db);
                mTmpDbOnInitializing = db;
                // setForeignKeyConstraintsEnabled(db, true);
                onDbConfigure(db);
                mTmpDbOnInitializing = null;
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                mTmpDbOnInitializing = db;
                // setForeignKeyConstraintsEnabled(db, true);
                onDbCreate(db);
                mTmpDbOnInitializing = null;
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                mTmpDbOnInitializing = db;
                onDbUpgrade(db, oldVersion, newVersion);
                mTmpDbOnInitializing = null;
            }

            @Override
            public void onOpen(SQLiteDatabase db) {
                super.onOpen(db);
                mTmpDbOnInitializing = db;
                // if (!db.isReadOnly()) {
                // // Enable foreign key constraints
                // db.execSQL(DataDef.ENABLE_DB_FOREIGN_KEY);
                // }
                onDbOpen(db);
                mTmpDbOnInitializing = null;
            }
        };
    }

    private static void checkName(String name) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("the arg 'name' can not be empty");
        }
    }

    private static void checkVersion(int version) {
        if (version < 0) {
            throw new IllegalArgumentException("the arg 'version' must be >= 0");
        }
    }

    private static void checkContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("the arg 'context' can not be empty");
        }
    }

    public abstract void onDbConfigure(SQLiteDatabase db);

    public abstract void onDbOpen(SQLiteDatabase db);

    public abstract void onDbCreate(SQLiteDatabase db);

    public abstract void onDbUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    public void close() {
        mDbHelper.close();
    }

    /**
     * 在SQLiteOpenHelper初始化期间，返回初始化时的临时数据库，避免初始化时死锁；
     *
     * @return 本地数据的db对象
     */
    public SQLiteDatabase getDb() {
        return mTmpDbOnInitializing == null ? mDbHelper.getWritableDatabase() : mTmpDbOnInitializing;
    }

    public SmartPreferences getPreferences(){
        return SmartPreferences.get(mCtx, mName, Context.MODE_PRIVATE);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db, boolean enable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledBySql(db, enable);
        } else {
            setForeignKeyConstraintsEnabledByNative(db, enable);
        }
    }

    private void setForeignKeyConstraintsEnabledBySql(SQLiteDatabase db, boolean enable) {
        db.execSQL(ENABLE_DB_FOREIGN_KEY);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledByNative(SQLiteDatabase db, boolean enable) {
        db.setForeignKeyConstraintsEnabled(true);
    }

}
