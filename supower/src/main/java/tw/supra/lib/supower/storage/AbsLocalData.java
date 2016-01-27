package tw.supra.lib.supower.storage;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

/**
 * 本地数据虚基类；
 * <p/>
 * 包装SQLite和SharedPreferences两种本地数据持久化方案，按数据名称统一维护管理这两种数据的生命周期流程并简化他们的存取操作;
 * - 采用SmartPreferences包装来实现简单键值对数据的高效读写和自动存取异步IO;
 * - 简化业务子类对SQLite数据库生命周期的维护成本，原则上仅关心数据库的打开、配置和升级过程，屏蔽数据库降级操作;
 * - 维护SQLiteOpenHelper初始化时的状态;避免出现SQLite DB在初始化时被锁，导致数据库初始化或升级失败的问题;
 * <p/>
 * 具体业务可继承该类定义和实现所需的数据结构
 *
 * @author supra
 */
public abstract class AbsLocalData implements LocalData<SmartPreferences> {


    // =================================================================================================================
    // 静态常量定义
    // =================================================================================================================

    /**
     * log tag
     */
    private static final String LOG_TAG = AbsLocalData.class.getSimpleName();
    /**
     * db name sub fix
     */
    private static final String DB_SUB_FIX = ".db";


    // =================================================================================================================
    // 成员定义
    // =================================================================================================================

    /**
     * 数据结构版本号
     */
    private final int mVersion;

    /**
     * 数据名
     */
    private final String mName;

    /**
     * 上下文
     */
    private final Context mCtx;

    /**
     * SQLiteOpenHelper成员弱引用
     */
    private WeakReference<SQLiteOpenHelper> mWeakDbHelper;

    /**
     * SQLiteOpenHelper 初始化期间的临时DB成员对象
     */
    private SQLiteDatabase mTmpDbOnInitializing;

    /**
     * 本地数据对象构造函数
     *
     * @param ctx     上下文
     * @param name    数据名
     * @param version 数据结构版本
     */
    public AbsLocalData(Context ctx, String name, int version) {
        checkName(name);
        checkVersion(version);
        checkContext(ctx);
        mName = name;
        mVersion = version;
        mCtx = ctx.getApplicationContext();
    }


    // =================================================================================================================
    // 静态方法
    // =================================================================================================================

    /**
     * 检查数据名是否合法
     *
     * @param name 数据名
     */
    private static void checkName(String name) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("the arg 'name' can not be empty");
        }
    }

    /**
     * 检查数据结构版本号是否合法
     *
     * @param version 数据结构版本号
     */
    private static void checkVersion(int version) {
        if (version < 0) {
            throw new IllegalArgumentException("the arg 'version' must be >= 0");
        }
    }

    /**
     * 检查上下文引用是否合法
     *
     * @param context 上下文引用
     */
    private static void checkContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("the arg 'context' can not be empty");
        }
    }


    // =================================================================================================================
    // 成员方法定义
    // =================================================================================================================

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public int getVersion() {
        return mVersion;
    }

    /**
     * 在SQLiteOpenHelper初始化期间，返回初始化时的临时数据库，避免初始化时死锁；
     * {@link LocalData#getDb()}
     *
     * @return 本地数据的db对象
     */
    @Override
    public SQLiteDatabase getDb() {
        return mTmpDbOnInitializing == null ? getSQLiteOpenHelper().getWritableDatabase() : mTmpDbOnInitializing;
    }

    @Override
    public SmartPreferences getPreferences() {
        return SmartPreferences.get(mCtx, mName, Context.MODE_PRIVATE);
    }

    @Override
    public void close() {
        if (null != mWeakDbHelper && null != mWeakDbHelper.get()) {
            mWeakDbHelper.get().close();
        }
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    protected abstract void onDbCreate(SQLiteDatabase db);

    /**
     * Called when the database connection is being configured, to enable features
     * such as write-ahead logging or foreign key support.
     * <p>
     * This method is called before {@link #onDbCreate}, {@link #onDbUpgrade}, or {@link #onDbOpen} are called.
     * It should not modify
     * the database except to configure the database connection as required.
     * </p><p>
     * This method should only call methods that configure the parameters of the
     * database connection, such as {@link SQLiteDatabase#enableWriteAheadLogging}
     * {@link SQLiteDatabase#setForeignKeyConstraintsEnabled},
     * {@link SQLiteDatabase#setLocale}, {@link SQLiteDatabase#setMaximumSize},
     * or executing PRAGMA statements.
     * </p>
     *
     * @param db The database.
     */
    protected abstract void onDbConfigure(SQLiteDatabase db);

    /**
     * Called when the database has been opened.
     * <p>
     * This method is called after the database connection has been configured
     * and after the database schema has been created, upgraded or downgraded as necessary.
     * If the database connection must be configured in some way before the schema
     * is created or upgraded, do it in {@link #onDbConfigure} instead.
     * </p>
     *
     * @param db The database.
     */
    protected abstract void onDbOpen(SQLiteDatabase db);


    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    protected abstract void onDbUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    /**
     * 懒构造方式得到数据对应的SQLiteOpenHelper对象
     *
     * @return 数据对应的SQLiteOpenHelper对象
     */
    private SQLiteOpenHelper getSQLiteOpenHelper() {
        if (null == mWeakDbHelper || null == mWeakDbHelper.get()) {
            mWeakDbHelper = new WeakReference<>(createSQLiteOpenHelper());
        }
        return mWeakDbHelper.get();
    }

    /**
     * 构建数据对应的SQLiteOpenHelper对象
     *
     * @return 数据对应的SQLiteOpenHelper对象
     */
    private SQLiteOpenHelper createSQLiteOpenHelper() {
        String dbName = mName.endsWith(DB_SUB_FIX) ? mName : mName + DB_SUB_FIX;
        return new SQLiteOpenHelper(mCtx, dbName, null, mVersion) {

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

}