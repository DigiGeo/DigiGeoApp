package org.digigeo.digigeo;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LatLng myPosition;
    private LocationManager locationManager;
    private Double latitude;
    private Double longitude;
    private CameraUpdate cameraUpdate;
    private SupportMapFragment mapFragment;
    private final int REQUEST_LOCATION = 10;

    public Map() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        locationManager = (LocationManager) this.getContext().getSystemService(this.getContext().LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showAlertPhone();
            return;
        }
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 100, locationListenerNetwork);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mMap.setMyLocationEnabled(true);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                myPosition = new LatLng(latitude, longitude);
                cameraUpdate = CameraUpdateFactory.newLatLngZoom(myPosition, 16);
                mMap.animateCamera(cameraUpdate);
            }
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 100, locationListenerNetwork);
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        mMap.setMyLocationEnabled(true);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            myPosition = new LatLng(latitude, longitude);
                            cameraUpdate = CameraUpdateFactory.newLatLngZoom(myPosition, 16);
                            mMap.animateCamera(cameraUpdate);
                        }
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
            }
            default:{
            }

        }
    }

    @Override
    public void onResume(){
        mapFragment.getMapAsync(this);
        super.onResume();
    }


    private void showAlertPhone() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.getContext());
        dialog.setTitle("Enable Location GPS")
                .setMessage("Set Permissions")
                .setPositiveButton("Approve", (paramDialogInterface, paramInt) -> {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                })
                .setNegativeButton("Cancel", (paramDialogInterface, paramInt) -> {});
        dialog.show();
        //todo:figure out how to close dialog and extract string resources

    }

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                myPosition = new LatLng(latitude, longitude);
                cameraUpdate = CameraUpdateFactory.newLatLngZoom(myPosition, 16);
                mMap.animateCamera(cameraUpdate);
            }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {}

        @Override
        public void onProviderDisabled(String s) {}
        };


}
