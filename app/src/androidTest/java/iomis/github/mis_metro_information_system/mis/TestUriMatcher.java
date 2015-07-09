package iomis.github.mis_metro_information_system.mis;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import iomis.github.mis_metro_information_system.mis.data.DbContract;
import iomis.github.mis_metro_information_system.mis.data.DbProvider;

/**
 * Created by esteban.foronda on 9/07/15.
 */
public class TestUriMatcher extends AndroidTestCase{
    private static final Uri TEST_ROUTES_DIR = DbContract.RoutesEntry.CONTENT_URI;
    private static final Uri TEST_ROUTES_WITH_ID_DIR = DbContract.RoutesEntry.buildRouteUri(1);
    private static final Uri TEST_EXPEND_WITH_ID_DIR = DbContract.ExpendsEntry.buildUserUri(1);
    private static final Uri TEST_EXPEND_DIR = DbContract.ExpendsEntry.CONTENT_URI;

    public void testUriMatcher() {
        UriMatcher testMatcher = DbProvider.buildUriMatcher();

        assertEquals("Error: The Routes URI was matched incorrectly.",
                testMatcher.match(TEST_ROUTES_DIR), DbProvider.ROUTE_LIST);
        assertEquals("Error: The Route with a specific Id URI was matched incorrectly.",
                testMatcher.match(TEST_ROUTES_WITH_ID_DIR), DbProvider.ROUTE_ID);
        assertEquals("Error: The Expend with specific Id URI was matched incorrectly.",
                testMatcher.match(TEST_EXPEND_WITH_ID_DIR), DbProvider.EXPENDS_ID);
        assertEquals("Error: The Expend URI was matched incorrectly.",
                testMatcher.match(TEST_EXPEND_DIR), DbProvider.EXPENDS_LIST);
    }
}
