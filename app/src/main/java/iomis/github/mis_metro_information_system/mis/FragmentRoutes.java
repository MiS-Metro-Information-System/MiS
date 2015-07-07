package iomis.github.mis_metro_information_system.mis;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by felipe.gutierrez on 06/07/2015.
 */
public class FragmentRoutes extends Fragment{
    //Source http://stackoverflow.com/questions/19353255/how-to-put-google-maps-v2-on-a-fragment-using-viewpager
    private static GoogleMap googleMap;
    private static View view;
    public MapView mapView;
    private static Double latitude, longitude;
    public static Double [] coordenatesMetroStations = {6.1546144,-75.6214601,6.174159, -75.597090
    ,6.185886, -75.585556,6.193230, -75.582327,6.212216, -75.578070};

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
        // Passing harcoded values for latitude & longitude. Please change as per your need. This is just used to drop a Marker on the Map
        //setUpMapIfNeeded(); // For setting up the MapFragment

        return view;
    }

   /* public void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            //getChildFragmentManager()
            //MainActivity.fragmentManager
            ((SupportMapFragment) MainActivity.fragmentManager.findFragmentById(R.id.map)).getMap();
            Log.e("Error", mMap.toString());
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }*/
    private static void setUpMap() {
        // For showing a move to my loction button
        googleMap.setMyLocationEnabled(true);
        // For dropping a marker at a point on the Map
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("My Home").snippet("Home Address"));
        // For zooming automatically to the Dropped PIN Location
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
        //        longitude), 12.0f));
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.width(5);
        polylineOptions.color(Color.parseColor("#00FF00"));
        for(int i = 0; i < coordenatesMetroStations.length; i=i+2){
            polylineOptions.add(new LatLng(coordenatesMetroStations[i], coordenatesMetroStations[i+1]));
        }
        googleMap.addPolyline(polylineOptions);
        latitude = googleMap.getMyLocation().getLatitude();
        longitude = googleMap.getMyLocation().getLongitude();
    }



   /* @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (mMap != null)
            setUpMap();

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) MainActivity.fragmentManager
                    .findFragmentById(R.id.map)).getMap(); // getMap is deprecated
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }*/

    /**** The mapfragment's id must be removed from the FragmentManager
     **** or else if the same it is passed on the next time then
     **** app will crash ****/
   /* @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMap != null) {
            MainActivity.fragmentManager.beginTransaction()
                    .remove(MainActivity.fragmentManager.findFragmentById(R.id.map)).commit();
            mMap = null;
        }
    }*/

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
}
