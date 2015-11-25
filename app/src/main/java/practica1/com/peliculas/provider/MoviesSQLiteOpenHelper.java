package practica1.com.peliculas.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import practica1.com.peliculas.BuildConfig;
import practica1.com.peliculas.provider.populars.PopularsColumns;
import practica1.com.peliculas.provider.toprated.TopratedColumns;

public class MoviesSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = MoviesSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;
    private static MoviesSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final MoviesSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_POPULARS = "CREATE TABLE IF NOT EXISTS "
            + PopularsColumns.TABLE_NAME + " ( "
            + PopularsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PopularsColumns.MOVIE_TITLE + " TEXT, "
            + PopularsColumns.MOVIE_RELEASE_DATE + " TEXT, "
            + PopularsColumns.MOVIE_POPULARITY + " TEXT, "
            + PopularsColumns.MOVIE_DESCRIPTION + " TEXT, "
            + PopularsColumns.MOVIE_IMAGE_URL + " TEXT "
            + " );";

    public static final String SQL_CREATE_TABLE_TOPRATED = "CREATE TABLE IF NOT EXISTS "
            + TopratedColumns.TABLE_NAME + " ( "
            + TopratedColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TopratedColumns.MOVIE_TITLE + " TEXT, "
            + TopratedColumns.MOVIE_RELEASE_DATE + " TEXT, "
            + TopratedColumns.MOVIE_POPULARITY + " TEXT, "
            + TopratedColumns.MOVIE_DESCRIPTION + " TEXT, "
            + TopratedColumns.MOVIE_IMAGE_URL + " TEXT "
            + " );";

    // @formatter:on

    public static MoviesSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static MoviesSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static MoviesSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new MoviesSQLiteOpenHelper(context);
    }

    private MoviesSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new MoviesSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static MoviesSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new MoviesSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private MoviesSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new MoviesSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_POPULARS);
        db.execSQL(SQL_CREATE_TABLE_TOPRATED);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
