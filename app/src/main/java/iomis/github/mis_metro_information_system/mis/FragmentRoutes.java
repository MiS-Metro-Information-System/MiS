package iomis.github.mis_metro_information_system.mis;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by felipe.gutierrez on 06/07/2015.
 */
public class FragmentRoutes extends Fragment {
    private GoogleMap googleMap;
    private Double [] coordenatesMetroStations = {6.1546144,-75.6214601,6.174159, -75.597090
    ,6.185886, -75.585556,6.193230, -75.582327,6.212216, -75.578070};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try{
            initializeMap();
        }catch (Exception e){
            Log.e("Exception", e.toString());
        }
        return inflater.inflate(R.layout.fragment_routes, container, false);
    }
    private void initializeMap(){
        if (googleMap == null) {
            googleMap = ((MapFragment) getActivity().getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(true);
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.color(Color.parseColor("#00FF00"));
            for(int i = 0; i < coordenatesMetroStations.length; i=i+2){
                    polylineOptions.add(new LatLng(coordenatesMetroStations[i], coordenatesMetroStations[i+1]));
            }
            googleMap.addPolyline(polylineOptions);
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeMap();
    }
}
