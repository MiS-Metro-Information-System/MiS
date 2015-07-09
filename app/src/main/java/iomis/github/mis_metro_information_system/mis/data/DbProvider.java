package iomis.github.mis_metro_information_system.mis.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by esteban.foronda on 06/07/2015.
 */
public class DbProvider extends ContentProvider {
    private DbHelper dbHelper = null;
    private static final int EXPENDS_LIST = 0;
    private static final int EXPENDS_ID = 1;
    private static final int ROUTE_LIST = 10;
    private static final int ROUTE_ID = 11;
    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(DbContract.CONTENT_AUTHORITY, "expends", EXPENDS_LIST);
        URI_MATCHER.addURI(DbContract.CONTENT_AUTHORITY, "expends/#", EXPENDS_ID);
        URI_MATCHER.addURI(DbContract.CONTENT_AUTHORITY, "routes", ROUTE_LIST);
        URI_MATCHER.addURI(DbContract.CONTENT_AUTHORITY, "routes/#", ROUTE_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        boolean useAuthorityUri = false;
        switch (URI_MATCHER.match(uri)) {
            case ROUTE_LIST:
                builder.setTables(DbContract.RoutesEntry.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = DbContract.SORT_DEFAULT_ORDER;
                }
                break;
            case ROUTE_ID:
                builder.setTables(DbContract.RoutesEntry.TABLE_NAME);
                // limit query to one row at most:
                builder.appendWhere(DbContract.RoutesEntry.COLUMN_ROUTE_ID + " = " +
                        uri.getLastPathSegment());
                break;
            case EXPENDS_LIST:
                builder.setTables(DbContract.ExpendsEntry.TABLE_NAME);
                break;
            case EXPENDS_ID:
                builder.setTables(DbContract.ExpendsEntry.TABLE_NAME);
                // limit query to one row at most:
                builder.appendWhere(DbContract.ExpendsEntry.COLUMN_EXPEND_ID +
                        " = " +
                        uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException(
                        "Unsupported URI: " + uri);
        }
        Cursor cursor =
                builder.query(
                        db,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case EXPENDS_LIST:
                return DbContract.ExpendsEntry.CONTENT_TYPE;
            case EXPENDS_ID:
                return DbContract.ExpendsEntry.CONTENT_ITEM_TYPE;
            case ROUTE_LIST:
                return DbContract.RoutesEntry.CONTENT_TYPE;
            case ROUTE_ID:
                return DbContract.RoutesEntry.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;
        Uri returnUri;
        switch (URI_MATCHER.match(uri)) {
            case EXPENDS_LIST:
                id = db.insert(DbContract.ExpendsEntry.TABLE_NAME, null, values);
                if (id > 0) returnUri = DbContract.ExpendsEntry.buildUserUri(id);
                else throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            case ROUTE_LIST:
                id = db.insert(DbContract.RoutesEntry.TABLE_NAME, null, values);
                if (id > 0) returnUri = DbContract.RoutesEntry.buildRouteUri(id);
                else throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI for insertion: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int delCount = 0;
        String idStr;
        String where;
        switch (URI_MATCHER.match(uri)) {
            case EXPENDS_LIST:
                delCount = db.delete(
                        DbContract.ExpendsEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case EXPENDS_ID:
                idStr = uri.getLastPathSegment();
                where = DbContract.ExpendsEntry.COLUMN_EXPEND_ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                delCount = db.delete(
                        DbContract.ExpendsEntry.TABLE_NAME,
                        where,
                        selectionArgs);
                break;
            case ROUTE_LIST:
                delCount = db.delete(
                        DbContract.RoutesEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case ROUTE_ID:
                idStr = uri.getLastPathSegment();
                where = DbContract.RoutesEntry.COLUMN_ROUTE_ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                delCount = db.delete(
                        DbContract.RoutesEntry.TABLE_NAME,
                        where,
                        selectionArgs);
                break;
            default:
                // no support for deleting photos or entities –
                // photos are deleted by a trigger when the item is deleted
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // notify all listeners of changes:
        if (delCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return delCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updateCount = 0;
        String idStr;
        String where;
        switch (URI_MATCHER.match(uri)) {
            case EXPENDS_LIST:
                updateCount = db.update(
                        DbContract.ExpendsEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case EXPENDS_ID:
                idStr = uri.getLastPathSegment();
                where = DbContract.ExpendsEntry.COLUMN_EXPEND_ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                updateCount = db.update(
                        DbContract.ExpendsEntry.TABLE_NAME,
                        values,
                        where,
                        selectionArgs);
                break;
            case ROUTE_LIST:
                updateCount = db.update(
                        DbContract.RoutesEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case ROUTE_ID:
                idStr = uri.getLastPathSegment();
                where = DbContract.RoutesEntry.COLUMN_ROUTE_ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                updateCount = db.update(
                        DbContract.RoutesEntry.TABLE_NAME,
                        values,
                        where,
                        selectionArgs);
                break;

            default:
                // no support for updating photos or entities!
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // notify all listeners of changes:
        if (updateCount > 0) getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }
}
