package org.digigeo.digigeo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import org.digigeo.digigeo.Database.AppDb;
import org.digigeo.digigeo.Database.CacheDao;
import org.digigeo.digigeo.Entity.Cache;

import java.lang.ref.WeakReference;
import java.util.Objects;

import static java.security.Security.getProvider;

public class Create extends Fragment {

    private TextView cacheName, cacheContents;
    private EditText editCacheName, editCacheContent;
    private double lat, lon;
    LocationManager lm;
    public Button submit;

    public Create() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create, container, false);

        cacheName = rootView.findViewById(R.id.cacheName);
        editCacheName = rootView.findViewById(R.id.editCacheName);
        cacheContents = rootView.findViewById(R.id.cacheContent);
        editCacheContent = rootView.findViewById(R.id.editCacheContent);
        submit = rootView.findViewById(R.id.submitBtn);

        lm = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);


        submit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Cache cache = new Cache();

                getLocation();

                if(!editCacheName.getText().toString().trim().isEmpty() &&
                        !editCacheContent.getText().toString().trim().isEmpty()){

                    cache.setName(editCacheName.getText().toString());
                    cache.setContent(editCacheContent.getText().toString());
                    cache.setLatitude(lat);
                    cache.setLongitude(lon);

                    Toast.makeText(rootView.getContext(), "Cache created!", Toast.LENGTH_SHORT).show();

                    editCacheName.setText("");
                    editCacheContent.setText("");

                } else {
                    alertDialogue("Can't create an empty cache\nFill out the form and try again");
                }
            }
        });

        return rootView;
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            lat = location.getLatitude();
            lon = location.getLongitude();
        }
    }

    public void alertDialogue(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage(message);
        builder1.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder1.show();
    }

    private static class SetCacheTask extends AsyncTask<Void, Void, Cache> {

        private WeakReference<Activity> weakActivity;
        private Cache cache;


        public SetCacheTask(Activity activity, Cache cache) {
            weakActivity = new WeakReference<>(activity);
            this.cache = cache;
        }

        @Override
        protected Cache doInBackground(Void... voids) {
            Activity activity = weakActivity.get();

            if (activity == null) {
                return null;
            }

            AppDb db = AppDb.getInstance(activity.getApplicationContext());

            db.cacheDao().insertAll(cache);

            return cache;
        }
    }
}