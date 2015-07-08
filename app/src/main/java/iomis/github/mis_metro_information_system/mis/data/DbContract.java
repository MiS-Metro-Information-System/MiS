package iomis.github.mis_metro_information_system.mis.data;

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
            "iomis.github.mis_metro_information_system.mis";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_EXPENDS = "expends";
    public static final String PATH_ROUTES = "routes";
    public static final String SORT_DEFAULT_ORDER = "ASC";


    /* Inner class that defines the table contents of the location table */
    public static final class RoutesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ROUTES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ROUTES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ROUTES;

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

    public static final class ExpendsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EXPENDS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EXPENDS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EXPENDS;

        public static final String TABLE_NAME = "Expends";

        public static final String COLUMN_EXPEND_ID = "expend_id";
        public static final String COLUMN_EXPEND = "expend";
        public static final String COLUMN_DATE = "date";

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
