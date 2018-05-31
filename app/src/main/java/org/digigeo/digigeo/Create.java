package org.digigeo.digigeo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Create extends Fragment {

    private EditText cacheName;
    private EditText cacheContents;
    private Button submit;

    public Create() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create, container, false);

        //cacheName = rootView.findViewById(R.id.cacheName);
        //cacheContents = rootView.findViewById(R.id.cacheContent);
        //submit = rootView.findViewById(R.id.submitBtn);

        return rootView;
    }

}
