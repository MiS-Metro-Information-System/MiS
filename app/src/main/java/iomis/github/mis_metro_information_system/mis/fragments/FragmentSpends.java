package iomis.github.mis_metro_information_system.mis.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import iomis.github.mis_metro_information_system.mis.R;
/**
 * Created by felipe.gutierrez on 15/07/15.
 */
public class FragmentSpends extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spends, container, false );
    }
    class SpendsAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if(view == null){
                //view = inflater.inflate(R.layout.spends_row, null);
            }
            return view;
        }
    }
}
