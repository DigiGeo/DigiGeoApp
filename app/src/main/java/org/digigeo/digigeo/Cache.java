package org.digigeo.digigeo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Cache extends Fragment {
    public Cache() {
        // Required empty public constructor
        // create cache list based on number of clicked maps
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cache, container, false);
        return rootView;
    }
}
