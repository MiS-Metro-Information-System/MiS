package iomis.github.mis_metro_information_system.mis;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by felipe.gutierrez on 06/07/2015.
 */
public class FragmentRoutes extends FragmentActivity {
    private GoogleMap googleMap;
    private Double [] coordenatesMetroStations = {6.1546144,-75.6214601,6.174159, -75.597090
    ,6.185886, -75.585556,6.193230, -75.582327,6.212216, -75.578070};
    private Context context;
    public FragmentRoutes(Context context){
        this.context = context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            initializeMap();
        }catch(Error e){
            Log.e("Error = ", e.toString());
        }
        return  inflater.inflate(R.layout.fragment_routes, container, false);
    }
    public void initializeMap(){
        Toast.makeText(context,"FORONDA", Toast.LENGTH_LONG).show();
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            double currentLatitude = googleMap.getMyLocation().getLatitude();
            double currentLongitude = googleMap.getMyLocation().getLongitude();
            LatLng latLng = new LatLng(currentLatitude, currentLongitude);

            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title("I am here!");
            googleMap.addMarker(options);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            Log.e("Llega hasta aca", Double.toString(currentLatitude));
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(true);

            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.width(5);
            polylineOptions.color(Color.parseColor("#00FF00"));
            for(int i = 0; i < coordenatesMetroStations.length; i=i+2){
                    polylineOptions.add(new LatLng(coordenatesMetroStations[i], coordenatesMetroStations[i+1]));
            }
            googleMap.addPolyline(polylineOptions);
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(context,
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //initializeMap();
    }
}
