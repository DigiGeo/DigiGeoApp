package org.digigeo.digigeo;


import android.os.Bundle;
import android.support.constraint.solver.Cache;
import android.support.v4.app.ActivityCompat;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CacheList extends Fragment {
    public CacheList() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_cache, container, false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView CacheName;
        public TextView Latitude;
        public TextView Longitude;
        public Button Open;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.cache_card,parent,false));
            CacheName = itemView.findViewById(R.id.cardCacheName);
            Latitude = itemView.findViewById(R.id.cardLatitude);
            Longitude = itemView.findViewById(R.id.cardLongitude);
            Open = itemView.findViewById(R.id.openCache);

            Open.setOnClickListener(v -> {
            Context context = v.getContext();
            CharSequence text = "This Will open Cache fragment for" + CacheName.getText();

                Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.show();
            });
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        private static  int length;
        private ArrayList<org.digigeo.digigeo.Entity.Cache> myCaches;

        public ContentAdapter(Bundle Caches) {

            myCaches = Caches.getParcelableArrayList("matches");
            length = myCaches.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.CacheName.setText("TEST NAME PLEASE IGNORE");
            holder.Latitude.setText("47.606209");
            holder.Longitude.setText("-122.332071");
        }

        @Override
        public int getItemCount() {
            return length;
        }
    }
}
