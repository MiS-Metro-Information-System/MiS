package iomis.github.mis_metro_information_system.mis.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by felipe.gutierrez on 06/07/2015.
 */
public class DbManager {
    public static final String TABLE_USER = "Users";

    public static final String CN_USER_ID = "_id";
    public static final String CN_USER_NAME = "username";
    public static final String CN_EXPENDS = "expend";

    public static final String TABLE_ROUTES = "Routes";

    public static final String CN_ROUTE_ID = "_id";
    public static final String CN_TIME = "time";
    public static final String CN_ROUTE_NAME = "name";
    public static final String CN_DESCRIPTION = "description";
    //This is other comment

    public static final String CREATE_TABLE_USER = " create table "+TABLE_USER+" ("
            +CN_USER_ID+" integer primary key not null,"
            +CN_USER_NAME+" text not null,"
            +CN_EXPENDS+" double );";
    public static final String CREATE_TABLE_ROUTES = " create table "+TABLE_ROUTES+" ("
            +CN_ROUTE_ID+" integer primary key autoincrement,"
            +CN_TIME+" double not null,"
            +CN_ROUTE_NAME+" text not null,"
            +CN_DESCRIPTION+" text );";

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
    public ContentValues generateUser(int id, String username, double expends){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CN_USER_ID, id);
        contentValues.put(CN_USER_NAME, username);
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
    public void insertUser(int id, String username, double expends){
        // insert (String table, String nullColumnHack, ContentValues values)
        db.insert(TABLE_USER, null, generateUser(id, username, expends));
    }
    public void insertRoutes(int [] ids, double [] times, String [] names, String [] descriptions){
        for(int i = 0; i < ids.length; i++){
            db.insert(TABLE_ROUTES, null, generateRoutes(ids[i], times[i], names[i], descriptions[i]));
        }
    }
    /*
    * Updates
    */
    public void updateUser(int id, String username, double expends){
        //For more than one value "IN (?, ?)"
        db.update(TABLE_USER,generateUser(id, username, expends),CN_USER_ID+" =? ", new String[]{Integer.toString(id).trim()});
    }
    /*
    * DELETS
     */
    public void deletUser(String id){
        db.delete(TABLE_USER, CN_USER_ID + " =? ", new String[]{id});
    }
    public void deletROUTE(String id){
        db.delete(TABLE_ROUTES,CN_ROUTE_ID+" =? ", new String[]{id});
    }
    /*
    * GET DATA
     */
    public Cursor getRoutes(){
        String [] columns = new String [] {CN_ROUTE_ID, CN_TIME, CN_ROUTE_NAME, CN_DESCRIPTION };
        return db.query(TABLE_ROUTES, columns, null, null, null, null, null);
    }
    public Cursor getUser(){
        String [] columns = new String[]{CN_USER_ID, CN_USER_NAME, CN_EXPENDS};
        return db.query(TABLE_USER, columns, null, null, null, null, null);
    }
}
