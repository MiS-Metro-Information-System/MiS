
package iomis.github.mis_metro_information_system.mis;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;
import android.util.Log;

import junit.framework.Test;

import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;

import iomis.github.mis_metro_information_system.mis.data.DbContract;
import iomis.github.mis_metro_information_system.mis.data.DbHelper;
import iomis.github.mis_metro_information_system.mis.data.DbManager;
import iomis.github.mis_metro_information_system.mis.data.DbProvider;

public class ProviderTest extends AndroidTestCase {

    public static final String LOG_TAG = ProviderTest.class.getSimpleName();

    public void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                DbContract.RoutesEntry.CONTENT_URI,
                null,
                null
        );
        mContext.getContentResolver().delete(
                DbContract.ExpendsEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                DbContract.RoutesEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Routes table during delete", 0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(
                DbContract.ExpendsEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Expends table during delete", 0, cursor.getCount());
        cursor.close();
    }

    /*
        Student: Refactor this function to use the deleteAllRecordsFromProvider functionality once
        you have implemented delete functionality there.
     */
    public void deleteAllRecords() {
        deleteAllRecordsFromProvider();
    }

    // Since we want each test to start with a clean slate, run deleteAllRecords
    // in setUp (called by the test runner before each test).
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // DbProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                DbProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: DbProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + DbContract.CONTENT_AUTHORITY,
                    providerInfo.authority, DbContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: DbProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }

    /*
            This test doesn't touch the database.  It verifies that the ContentProvider returns
            the correct type for each type of URI that it can handle.
            Students: Uncomment this test to verify that your implementation of GetType is
            functioning correctly.
         */
    public void testGetType() {
        // content://com.example.android.sunshine.app/weather/
        String type = mContext.getContentResolver().getType(DbContract.RoutesEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
        assertEquals("Error: the RoutesEntry CONTENT_URI should return RoutesEntry.CONTENT_TYPE",
                DbContract.RoutesEntry.CONTENT_TYPE, type);
        // content://com.example.android.sunshine.app/location/
        type = mContext.getContentResolver().getType(DbContract.ExpendsEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.android.sunshine.app/location
        assertEquals("Error: the ExpendsEntry CONTENT_URI should return LocationEntry.CONTENT_TYPE",
                DbContract.ExpendsEntry.CONTENT_TYPE, type);
    }


    /*
        This test uses the database directly to insert and then uses the ContentProvider to
        read out the data.  Uncomment this test to see if the basic weather query functionality
        given in the ContentProvider is working correctly.
     */
    public void testBasicRoutesQuery() {
        // insert our test records into the database
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = DbManager.generateRoutes(1, 2.3, "Niquia", "Primera estacion");
        long routeRowId = db.insert(DbContract.RoutesEntry.TABLE_NAME, null, testValues);
        assertTrue("Unable to Insert RouteEntry into the Database", routeRowId != -1);

        db.close();

        // Test the basic content provider query
        Cursor weatherCursor = mContext.getContentResolver().query(
                DbContract.RoutesEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        // Make sure we get the correct cursor out of the database
        TestUtils.testValuesCursor("testBasicRoutesQuery", weatherCursor, testValues);
    }

    /*
        This test uses the database directly to insert and then uses the ContentProvider to
        read out the data.  Uncomment this test to see if your location queries are
        performing correctly.
     */
    public void testBasicExpendQuery() {
        // insert our test records into the database
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        java.sql.Date currentTimestamp = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        ContentValues testValues = DbManager.generateExpend(1, currentTimestamp.toString(), 300.2);
        long expendRowId = db.insert(DbContract.ExpendsEntry.TABLE_NAME, null, testValues);

        // Test the basic content provider query
        Cursor expendCursor = mContext.getContentResolver().query(
                DbContract.ExpendsEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        TestUtils.testValuesCursor("testBasicExpendQuery, expend query", expendCursor, testValues);

        // Has the NotificationUri been set correctly? --- we can only test this easily against API
        // level 19 or greater because getNotificationUri was added in API level 19.
        if ( Build.VERSION.SDK_INT >= 19 ) {
            assertEquals("Error: Location Query did not properly set NotificationUri",
                    expendCursor.getNotificationUri(), DbContract.ExpendsEntry.CONTENT_URI);
        }
    }

    public void testUpdateRoutes() {
        // Create a new map of values, where column names are the keys
        ContentValues values = DbManager.generateRoutes(1, 2.3, "Niquia", "Primera estacion");

        Uri locationUri = mContext.getContentResolver().
                insert(DbContract.RoutesEntry.CONTENT_URI, values);
        long locationRowId = ContentUris.parseId(locationUri);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);
        Log.d(LOG_TAG, "New row id: " + locationRowId);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(DbContract.RoutesEntry.COLUMN_NAME, "Bello");
        updatedValues.put(DbContract.RoutesEntry.COLUMN_DESCRIPTION, "Segunda estacion");

        Cursor locationCursor = mContext.getContentResolver()
                .query(DbContract.RoutesEntry.CONTENT_URI, null, null, null, null);

        TestUtils.TestContentObserver tco = TestUtils.getTestContentObserver();
        locationCursor.registerContentObserver(tco);

        int count = mContext.getContentResolver().update(
                DbContract.RoutesEntry.CONTENT_URI, updatedValues,
                DbContract.RoutesEntry.COLUMN_ROUTE_ID + "= ?",
                new String[] { Long.toString(locationRowId)});
        assertEquals(count, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        //
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();

        locationCursor.unregisterContentObserver(tco);
        locationCursor.close();

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                DbContract.RoutesEntry.CONTENT_URI,
                null,   // projection
                DbContract.RoutesEntry.COLUMN_ROUTE_ID + " = " + locationRowId,
                null,   // Values for the "where" clause
                null    // sort order
        );

        TestUtils.testValuesCursor("testUpdateLocation.  Error validating location entry update.",
                cursor, updatedValues);

        cursor.close();
    }

    public void testDeleteRecords() {
        ContentValues routeValues = DbManager.generateRoutes(2, 2.3, "Niquia", "Primera estacion");

        Uri routeUri = mContext.getContentResolver().
                insert(DbContract.RoutesEntry.CONTENT_URI, routeValues);

        java.sql.Date currentTimestamp = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        ContentValues expendValues = DbManager.generateExpend(1, currentTimestamp.toString(), 300.2);

        Uri expendUri = mContext.getContentResolver().
                insert(DbContract.ExpendsEntry.CONTENT_URI, expendValues);


        // Register a content observer for our location delete.
        TestUtils.TestContentObserver locationObserver = TestUtils.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(DbContract.RoutesEntry.CONTENT_URI, true, locationObserver);

        // Register a content observer for our weather delete.
        TestUtils.TestContentObserver weatherObserver = TestUtils.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(DbContract.ExpendsEntry.CONTENT_URI, true, weatherObserver);

        deleteAllRecordsFromProvider();

        // Students: If either of these fail, you most-likely are not calling the
        // getContext().getContentResolver().notifyChange(uri, null); in the ContentProvider
        // delete.  (only if the insertReadProvider is succeeding)
        locationObserver.waitForNotificationOrFail();
        weatherObserver.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(locationObserver);
        mContext.getContentResolver().unregisterContentObserver(weatherObserver);
    }

}
