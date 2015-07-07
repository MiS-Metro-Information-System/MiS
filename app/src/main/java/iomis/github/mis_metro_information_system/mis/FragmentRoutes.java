package iomis.github.mis_metro_information_system.mis;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by felipe.gutierrez on 06/07/2015.
 */
public class FragmentRoutes extends Fragment implements LocationListener{
    //Source http://stackoverflow.com/questions/19353255/how-to-put-google-maps-v2-on-a-fragment-using-viewpager
    private static GoogleMap googleMap;
    private static View view;
    public LocationManager locationManager;
    public MapView mapView;
    private static Double latitude, longitude;
    public static Double [] coordenatesMetroStations = {6.152781, -75.625685, 6.1546144,-75.6214601,6.174159, -75.597090
    ,6.185886, -75.585556,6.193230, -75.582327,6.212216, -75.578070};
    public static String [] metroEstationsNames = {"Niquia", "Bello","Madera","Acevedo", "Tricentenario",
    "Caribe","Universidad","Hospital", "Prado", "Parque Barrío","San Antonio","Alpujarra", "Exposiciones","Industriales",
    "Poblado","Aguacatala","Ayurá","Envigado","Itaguí","Sabaneta","La Estrella"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = (View) inflater.inflate(R.layout.fragment_routes, container, false);
        mapView = (MapView)view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        try{
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }catch (Exception e){
            e.printStackTrace();
        }
        googleMap = mapView.getMap();

        if(googleMap == null){}else{setUpMap();}

        return view;
    }

    private static void setUpMap() {
        // For showing a move to my loction button
        googleMap.setMyLocationEnabled(true);
        /*latitude = googleMap.getMyLocation().getLatitude();
        longitude = googleMap.getMyLocation().getLongitude();
        // For dropping a marker at a point on the Map*/
        // For zooming automatically to the Dropped PIN Location
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(6.185886, -75.585556), 12.0f));
        int estation = metroEstationsNames.length-1;
        for(int d = 0; d < coordenatesMetroStations.length; d+=2){
            latitude = coordenatesMetroStations[d];
            longitude = coordenatesMetroStations[d+1];
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Estación "+
                    metroEstationsNames[estation]).snippet("Information Station");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            googleMap.addMarker(markerOptions);
            estation--;
        }

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.width(5);
        polylineOptions.color(Color.parseColor("#00FF00"));
        for(int i = 0; i < coordenatesMetroStations.length; i=i+2){
            polylineOptions.add(new LatLng(coordenatesMetroStations[i], coordenatesMetroStations[i+1]));
        }
        googleMap.addPolyline(polylineOptions);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng , 10f);
        googleMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
