package iomis.github.mis_metro_information_system.mis.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iomis.github.mis_metro_information_system.mis.R;

/**
 * Created by felipe.gutierrez on 8/07/15.
 */
public class FragmentWallet  extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }
}
