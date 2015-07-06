package iomis.github.mis_metro_information_system.mis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by felipe.gutierrez on 06/07/2015.
 */
public class FragmentTime extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_time, container, false);
    }
}
