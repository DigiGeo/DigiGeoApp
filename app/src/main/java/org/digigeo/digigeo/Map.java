package org.digigeo.digigeo;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.digigeo.digigeo.Database.AppDb;
import org.digigeo.digigeo.Entity.Cache;

import java.lang.ref.WeakReference;
import java.util.List;

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
        //enabling zoom controls and gestures
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        locationManager = (LocationManager) this.getContext().getSystemService(this.getContext().LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showAlertPhone();
            return;
        }
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
          //  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 100, locationListenerNetwork); //no longer zooming to location.  closer to production code
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mMap.setMyLocationEnabled(true);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                myPosition = new LatLng(latitude, longitude);
                cameraUpdate = CameraUpdateFactory.newLatLngZoom(myPosition, 16);
                mMap.animateCamera(cameraUpdate);
                new GetCaches(Map.this).execute();

            }
        }
    }

    //rescaling bitmap based on size
    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {

        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
                      //  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 100, locationListenerNetwork); // no longer zooming to mylocation closer to production code
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        mMap.setMyLocationEnabled(true);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            myPosition = new LatLng(latitude, longitude);
                            cameraUpdate = CameraUpdateFactory.newLatLngZoom(myPosition, 16);
                            mMap.animateCamera(cameraUpdate);
                            new GetCaches(Map.this).execute();
                        }
                    }
                }
            }
            default:{
            }
        }
    }

    //updating the map on resume
    @Override
    public void onResume(){
        mapFragment.getMapAsync(this);
        super.onResume();
    }

    //clearing the map on pause
    @Override
    public void onPause()
    {
        mMap.clear();
        super.onPause();
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
    }

 /*   private final LocationListener locationListenerNetwork = new LocationListener() {
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
        };*/

    private static class GetCaches extends AsyncTask<Void, Void, List<Cache>> {
        private WeakReference<Fragment> weakFragment;
        GetCaches(Fragment fragment) {
            weakFragment = new WeakReference<>(fragment);
        }

        @Override
        protected List<org.digigeo.digigeo.Entity.Cache> doInBackground(Void... voids) {
            Fragment fragment = weakFragment.get();
            if (fragment == null) {
                return null;
            }

            AppDb db = AppDb.getInstance(fragment.getContext());
            List<org.digigeo.digigeo.Entity.Cache> caches = db.cacheDao().getMyCaches();

            return caches;
        }

        @Override
        protected void onPostExecute(List<org.digigeo.digigeo.Entity.Cache> caches) {
            Map fragment = (Map) weakFragment.get();
            if (caches == null || fragment == null) {
                return;
            }
            //Log.i("in post execute",caches.toString());
            for (int i = 0; i < caches.size(); i++) {
                Bitmap markerBitmap = BitmapFactory.decodeResource(fragment.getContext().getResources(), R.drawable.marker_image);
                markerBitmap = scaleBitmap(markerBitmap, 85, 85);
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(caches.get(i).getLatitude(), caches.get(i).getLongitude())).title(caches.get(i).getName()).snippet(caches.get(i).getContent());
                marker.icon(BitmapDescriptorFactory.fromBitmap(markerBitmap));
                fragment.mMap.addMarker(marker);

                //listener and click event for displaying markers info
                fragment.mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        marker.showInfoWindow();
                        return true;
                    }
                });
            }

        }


    }


}
