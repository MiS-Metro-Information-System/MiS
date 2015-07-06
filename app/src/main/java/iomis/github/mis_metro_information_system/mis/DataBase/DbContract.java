package iomis.github.mis_metro_information_system.mis.DataBase;

/**
 * Created by esteban.foronda on 06/07/2015.
 */
import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the weather database.
 */
public class DbContract {

    //authority needed to call the content provider
    public static final String CONTENT_AUTHORITY =
            "iomis.github.mis_metro_information_system.mis.data;";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content//" + CONTENT_AUTHORITY);

    public static final String PATH_USER = "user";
    public static final String PATH_ROUTE = "routes";


    /* Inner class that defines the table contents of the location table */
    public static final class RoutesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ROUTE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ROUTE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ROUTE;

        // Table name
        public static final String TABLE_NAME = "routes";
        public static final String COLUMN_ROUTE_ID = "route_id";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";

        public static Uri buildRouteUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class UserEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String TABLE_NAME = "User";
        
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_EXPEND = "expend";
        
        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        /*public static String getLocationSettingFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static long getDateFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(2));
        }

        public static long getStartDateFromUri(Uri uri) {
            String dateString = uri.getQueryParameter(COLUMN_USERNAME);
            if (null != dateString && dateString.length() > 0)
                return Long.parseLong(dateString);
            else
                return 0;
        }*/
    }
}
