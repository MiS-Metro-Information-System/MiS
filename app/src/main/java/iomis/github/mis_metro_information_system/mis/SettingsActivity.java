package iomis.github.mis_metro_information_system.mis;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import iomis.github.mis_metro_information_system.mis.Oauth.GooglePlus;

/**
 * Created by felipe.gutierrez on 8/07/15.
 */
public class SettingsActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button logout = (Button)findViewById(R.id.button_log_out);
        logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_log_out:
                GooglePlus.signOutFromGplus();
                break;
            default:
                break;
        }
    }
}
