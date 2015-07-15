package iomis.github.mis_metro_information_system.mis.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import iomis.github.mis_metro_information_system.mis.R;
/**
 * Created by felipe.gutierrez on 15/07/15.
 */
public class FragmentSpends extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spends, container, false );
    }
}
