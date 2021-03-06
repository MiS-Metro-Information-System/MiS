package iomis.github.mis_metro_information_system.mis.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by felipe.gutierrez on 06/07/2015.
 */
public class DbHelper extends SQLiteOpenHelper{
    //This is a comment

    public static int SCHEME_VERSION = 1;

    public static final String DB_NAME = "mis.sqlite";


    public DbHelper(Context context){
        super(context, DB_NAME, null ,SCHEME_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbManager.CREATE_EXPENDS_TABLE);
        db.execSQL(DbManager.CREATE_ROUTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.ExpendsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.RoutesEntry.TABLE_NAME);
        onCreate(db);
    }
}
