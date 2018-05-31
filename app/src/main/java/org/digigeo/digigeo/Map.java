package org.digigeo.digigeo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    public Map() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //todo:  get real time location of phone and display
        mMap = googleMap;
        // Add a marker in Seattle and move the camera to level 16
        LatLng seattle = new LatLng(47.6062, -122.3321);
        //test information will remove
        mMap.addMarker(new MarkerOptions().position(seattle).title("Marker in Seattle"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seattle));
        //setting the default to city level
        mMap.setMinZoomPreference(16.0f);
        //enabling zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //listener and click event for placing markers
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title("New Cache");

                mMap.addMarker(marker);
            }
        });

        //listener and click event for removing markers
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.remove();
                return true;
            }
        });
    }
}
