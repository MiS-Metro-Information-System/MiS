package iomis.github.mis_metro_information_system.mis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
//import com.google.android.gms.plus.model.people.Person;

import iomis.github.mis_metro_information_system.mis.Oauth.GooglePlus;

/**
 * Created by felipe.gutierrez on 8/07/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private SignInButton btnSignInGoogle;
    public GooglePlus gp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gp = new GooglePlus(this);

        // Build GoogleApiClient with access to basic profile
        gp.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();

        //Check if the user has access to the app to omit the login splash screen
        SharedPreferences loginPreferences = getSharedPreferences(gp.plusGoogleConnection, MODE_PRIVATE);
        Toast.makeText(this,Integer.toString(loginPreferences.getInt(gp.googlePlusLoginStatus, 0)), Toast.LENGTH_LONG).show();
        if (loginPreferences.getInt(gp.googlePlusLoginStatus, 0) == 1) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
        //Connection to google plus configuration
        setContentView(R.layout.activity_login);
        btnSignInGoogle = (SignInButton) findViewById(R.id.sign_in_button);
            btnSignInGoogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gp.signInWithGplus();
            }
        });
    }
    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }
        if (!gp.mIntentInProgress) {
            // Store the ConnectionResult for later usage
            gp.mConnectionResult = result;

            if (gp.mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                gp.resolveSignInError();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == gp.RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                gp.mSignInClicked = false;
            }
                gp.mIntentInProgress = false;
            if (!gp.mGoogleApiClient.isConnecting()) {
                gp.mGoogleApiClient.connect();
            }
        }
    }
    protected void onStart() {
        super.onStart();
        gp.mGoogleApiClient.connect();
    }
    protected void onStop() {
        super.onStop();
        if (gp.mGoogleApiClient.isConnected()) {
            gp.mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Connected to google +", Toast.LENGTH_SHORT).show();
        gp.sharedPreferences = getApplicationContext().getSharedPreferences(gp.plusGoogleConnection, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = gp.sharedPreferences.edit();
        editor.putInt(gp.googlePlusLoginStatus, 1);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionSuspended(int i) {
        gp.mGoogleApiClient.connect();
    }

    @Override
    public void onClick(View v) {

    }

}
