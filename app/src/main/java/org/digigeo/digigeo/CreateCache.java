package org.digigeo.digigeo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CreateCache extends Fragment {

    private EditText cacheName;
    private EditText cacheContents;
    private Button submit;

    //TODO add room and sqlite database?

    public CreateCache() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_cache, container, false);

        cacheName = rootView.findViewById(R.id.cacheName);
        cacheContents = rootView.findViewById(R.id.cacheContent);
        submit = rootView.findViewById(R.id.submitBtn);

        return rootView;
    }

    public CacheObj createCache(View v) {

        String name = cacheName.getText().toString();
        String contents = cacheContents.getText().toString();

        //TODO get current Lat and Long from map fragment
        double lat = 47.6062; // dummy lat
        double lon = 122.3321; //dummy long

        CacheObj cache = new CacheObj(name, contents, lat, lon);

        return cache;
    }
}
