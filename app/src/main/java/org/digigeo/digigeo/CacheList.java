package org.digigeo.digigeo;


import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.digigeo.digigeo.Database.AppDb;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class CacheList extends Fragment {

    RecyclerView recyclerView;

    public CacheList() {
        // Required empty public constructor
    }



    /*
        Overridden lifecycle functions
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_cache, container, false);

        new GetCaches(this).execute();

        return recyclerView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            new GetCaches(this).execute();

        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView CacheName;
        public TextView Latitude;
        public TextView Longitude;
        public Button Open;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.cache_card, parent, false));
            CacheName = itemView.findViewById(R.id.cardCacheName);
            Latitude = itemView.findViewById(R.id.cardLatitude);
            Longitude = itemView.findViewById(R.id.cardLongitude);
            Open = itemView.findViewById(R.id.openCache);

            Open.setOnClickListener(v -> {
                Context context = v.getContext();

                CharSequence text = "This Will open Cache fragment for " + CacheName.getText();

                Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.show();
            });
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        private static int length;
        private ArrayList<org.digigeo.digigeo.Entity.Cache> myCaches;

        public ContentAdapter(Bundle Caches) {

            myCaches = Caches.getParcelableArrayList("myCaches");
            length = myCaches.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.CacheName.setText(myCaches.get(position).getName());
            holder.Latitude.setText(String.valueOf(myCaches.get(position).getLatitude()));
            holder.Longitude.setText(String.valueOf(myCaches.get(position).getLongitude()));
        }

        @Override
        public int getItemCount() {
            return length;
        }
    }

    private static class GetCaches extends AsyncTask<Void, Void, List<org.digigeo.digigeo.Entity.Cache>> {

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
            CacheList fragment = (CacheList) weakFragment.get();
            ArrayList<org.digigeo.digigeo.Entity.Cache> myCaches = new ArrayList<>();
            Bundle bundle = new Bundle();


            if (caches == null || fragment == null) {
                return;
            }

            for (int i = 0; i < caches.size(); i++) {
                myCaches.add(caches.get(i));
            }

            bundle.putParcelableArrayList("myCaches", myCaches);
            ContentAdapter adapter = new ContentAdapter(bundle);
            fragment.recyclerView.setAdapter(adapter);
            fragment.recyclerView.setHasFixedSize(true);
            fragment.recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getActivity()));
        }
    }
}
