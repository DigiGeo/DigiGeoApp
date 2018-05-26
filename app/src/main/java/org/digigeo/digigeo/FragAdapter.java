package org.digigeo.digigeo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private Bundle b;


    // constructor
    public FragAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // grabs the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if(position ==0) {
            return new Map();
        }
        else if (position == 1){
            return new Cache();
        }
        else {
            return new BlankFragment();
        }

    }

    // sets the number of tabs
    @Override
    public int getCount() {
        return 3;
    }

    // heading for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // title based on item position
        switch (position) {
            case 0:
               return "Map";
            case 1:
                return "Cache";
            case 2:
                return mContext.getString(R.string.blank);
            default:
                return null;
        }
    }

}