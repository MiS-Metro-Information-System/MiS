package iomis.github.mis_metro_information_system.mis.Oauth;


import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

/**
 * Created by felipe.gutierrez on 9/07/15.
 */
public class GooglePlus {
    public static final int RC_SIGN_IN = 0;
    public ConnectionResult mConnectionResult;
    public static SharedPreferences sharedPreferences;
    public static final String googlePlusLoginStatus = "status";
    public boolean mIntentInProgress;;
    public static String plusGoogleConnection = "GooglePLusLogin";
    // Google client to interact with Google API
    public static GoogleApiClient mGoogleApiClient;
    public boolean mSignInClicked;
    private static Activity activity;
    public GooglePlus(Activity activity){
        this.activity = activity;
    }
    /**
     * Sign-out from google
     * */
    public static void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()){
            sharedPreferences =  activity.getSharedPreferences(plusGoogleConnection, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(googlePlusLoginStatus, 0);
            editor.commit();
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }
    /**
     * Sign-in into google
     * */
    public void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }
    /**
     * Method to resolve any signin errors
     * */
    public void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(activity, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

}
