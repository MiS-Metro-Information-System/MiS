package iomis.github.mis_metro_information_system.mis.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by felipe.gutierrez on 06/07/2015.
 */
public class DbManager {
    public static final String EXPEND_TABLE = DbContract.ExpendsEntry.TABLE_NAME;

    public static final String CN_EXPEND_ID = DbContract.ExpendsEntry.COLUMN_EXPEND_ID;
    public static final String CN_EXPEND_DATE = DbContract.ExpendsEntry.COLUMN_DATE;
    public static final String CN_EXPENDS = DbContract.ExpendsEntry.COLUMN_EXPEND;

    public static final String ROUTES_TABLE = DbContract.RoutesEntry.TABLE_NAME;

    public static final String CN_ROUTE_ID = DbContract.RoutesEntry.COLUMN_ROUTE_ID;
    public static final String CN_TIME = DbContract.RoutesEntry.COLUMN_TIME;
    public static final String CN_ROUTE_NAME = DbContract.RoutesEntry.COLUMN_NAME;
    public static final String CN_DESCRIPTION = DbContract.RoutesEntry.COLUMN_DESCRIPTION;
    //This is other comment

    public static final String CREATE_EXPENDS_TABLE = " create table " + EXPEND_TABLE + " ("
            + CN_EXPEND_ID + " INTEGER primary key not null AUTO_INCREMENT,"
            + CN_EXPEND_DATE +" TIMESTAMP not null,"
            + CN_EXPENDS + " DOUBLE );";

    public static final String CREATE_TABLE_ROUTES = " create table "+ ROUTES_TABLE +" ("
            + CN_ROUTE_ID + " INTEGER primary key autoincrement,"
            + CN_TIME + " DOUBLE not null,"
            + CN_ROUTE_NAME + " TEXT not null,"
            + CN_DESCRIPTION + " TEXT );";

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    //This is a comment
    public DbManager(Context context){
                /*
        Data base creation and instance
         */
         dbHelper = new DbHelper(context);
         db = dbHelper.getWritableDatabase();
    }
    public ContentValues generateExpend(int id, String username, double expends){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN_EXPEND_ID, id);
        contentValues.put(CN_EXPEND_DATE, username);
        contentValues.put(CN_EXPENDS, expends);
        return  contentValues;
    }
    public ContentValues generateRoutes(int id, double time, String name, String description){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN_ROUTE_ID, id);
        contentValues.put(CN_TIME, time);
        contentValues.put(CN_ROUTE_NAME, name);
        contentValues.put(CN_DESCRIPTION, description);
        return contentValues;
    }
    /*
    *  CRUD OPERATIONS
     */
    /*
    * INSERTS
     */
    public void insertExpend(int id, String username, double expends){
        // insert (String table, String nullColumnHack, ContentValues values)
        db.insert(EXPEND_TABLE, null, generateExpend(id, username, expends));
    }
    public void insertRoutes(int [] ids, double [] times, String [] names, String [] descriptions){
        for(int i = 0; i < ids.length; i++){
            db.insert(ROUTES_TABLE, null, generateRoutes(ids[i], times[i], names[i], descriptions[i]));
        }
    }
    /*
    * Updates
    */
    public void updateUser(int id, String username, double expends){
        //For more than one value "IN (?, ?)"
        db.update(EXPEND_TABLE, generateExpend(id, username, expends), CN_EXPEND_ID +" =? ", new String[]{Integer.toString(id).trim()});
    }
    /*
    * DELETS
     */
    public void deletUser(String id){
        db.delete(EXPEND_TABLE, CN_EXPEND_ID + " =? ", new String[]{id});
    }
    public void deletROUTE(String id){
        db.delete(ROUTES_TABLE,CN_ROUTE_ID + " =? ", new String[]{id});
    }
    /*
    * GET DATA
     */
    public Cursor getRoutes(){
        String [] columns = new String [] {CN_ROUTE_ID, CN_TIME, CN_ROUTE_NAME, CN_DESCRIPTION };
        return db.query(ROUTES_TABLE, columns, null, null, null, null, null);
    }
    public Cursor getUser(){
        String [] columns = new String[]{CN_EXPEND_ID, CN_EXPEND_DATE, CN_EXPENDS};
        return db.query(EXPEND_TABLE, columns, null, null, null, null, null);
    }
}
