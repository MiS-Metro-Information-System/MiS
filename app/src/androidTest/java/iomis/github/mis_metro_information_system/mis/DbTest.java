
package iomis.github.mis_metro_information_system.mis;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import junit.framework.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import iomis.github.mis_metro_information_system.mis.data.DbContract;
import iomis.github.mis_metro_information_system.mis.data.DbHelper;
import iomis.github.mis_metro_information_system.mis.data.DbManager;

public class DbTest extends AndroidTestCase {

    public static final String LOG_TAG = DbTest.class.getSimpleName();

    void deleteTheDatabase() {
        mContext.deleteDatabase(DbHelper.DB_NAME);
    }

    public void setUp() {
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(DbContract.ExpendsEntry.TABLE_NAME);
        tableNameHashSet.add(DbContract.RoutesEntry.TABLE_NAME);

        mContext.deleteDatabase(DbHelper.DB_NAME);
        SQLiteDatabase db = new DbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the Routes entry and Expends entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + DbContract.ExpendsEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> locationColumnHashSet = new HashSet<String>();
        locationColumnHashSet.add(DbContract.ExpendsEntry.COLUMN_EXPEND_ID);
        locationColumnHashSet.add(DbContract.ExpendsEntry.COLUMN_EXPEND);
        locationColumnHashSet.add(DbContract.ExpendsEntry.COLUMN_DATE);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            locationColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required Expends entry columns",
                locationColumnHashSet.isEmpty());
        db.close();
    }

    /*
        Students:  Here is where you will build code to test that we can insert and query the
        location database.  We've done a lot of work for you.  You'll want to look in TestUtilities
        where you can uncomment out the "createNorthPoleLocationValues" function.  You can
        also make use of the ValidateCurrentRecord function from within TestUtilities.
    */
    public void testLocationTable() {
        insertRoute();
    }

    /*
        Students:  Here is where you will build code to test that we can insert and query the
        database.  We've done a lot of work for you.  You'll want to look in TestUtilities
        where you can use the "createWeatherValues" function.  You can
        also make use of the testValuesCursor function from within TestUtilities.
     */
    public void testExpendTable() {
        // First insert the location, and then use the locationRowId to insert
        // the weather. Make sure to cover as many failure cases as you can.

        // Instead of rewriting all of the code we've already written in testLocationTable
        // we can move this code to insertRoute and then call insertRoute from both
        // tests. Why move it? We need the code to return the ID of the inserted location
        // and our testLocationTable can only return void because it's a test.

        long locationRowId = insertRoute();

        // Make sure we have a valid row ID.
        assertFalse("Error: Location Not Inserted Correctly", locationRowId == -1L);

        // First step: Get reference to writable database
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second Step (Weather): Create weather values
        ContentValues expendValue = DbManager.generateExpend(1, "Felipe", 30.2);

        // Third Step (Weather): Insert ContentValues into database and get a row ID back
        long expendRowId = db.insert(DbContract.ExpendsEntry.TABLE_NAME, null, expendValue);
        assertTrue(expendRowId != -1);

        // Fourth Step: Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results.
        Cursor expendCursor = db.query(
                DbContract.ExpendsEntry.TABLE_NAME,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order
        );

        // Move the cursor to the first valid database row and check to see if we have any rows
        assertTrue("Error: No Records returned from location query", expendCursor.moveToFirst());

        // Fifth Step: Validate the location Query
        TestUtils.testValuesCursor("testInsertReadDb ExpendEntry failed to validate",
                expendCursor, expendValue);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse("Error: More than one record returned from weather query",
                expendCursor.moveToNext());

        // Sixth Step: Close cursor and database
        expendCursor.close();
        dbHelper.close();
    }

    public long insertRoute() {
        // First step: Get reference to writable database
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second Step: Create ContentValues of what you want to insert
        // (you can use the createNorthPoleLocationValues if you wish)
        ContentValues testValues = DbManager.generateRoutes(1, 2.3,"Niquia", "Primera estacion");

        // Third Step: Insert ContentValues into database and get a row ID back
        long routeRowId;
        routeRowId = db.insert(DbContract.RoutesEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue(routeRowId != -1);

        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // Fourth Step: Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                DbContract.RoutesEntry.TABLE_NAME,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        // Move the cursor to a valid database row and check to see if we got any records back
        // from the query
        assertTrue("Error: No Records returned from location query", cursor.moveToFirst());

        // Fifth Step: Validate data in resulting Cursor with the original ContentValues
        // (you can use the testValuesCursor function in TestUtilities to validate the
        // query if you like)
        TestUtils.testValuesCursor("Error: Location Query Validation Failed",
                cursor, testValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse( "Error: More than one record returned from location query",
                cursor.moveToNext() );

        // Sixth Step: Close Cursor and Database
        cursor.close();
        db.close();
        return routeRowId;
    }
}
