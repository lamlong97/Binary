package com.ltlstudio.lamlong.binary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ltlstudio.lamlong.binary.Fragment.BinaryConvertFragment;
import com.ltlstudio.lamlong.binary.Fragment.TextConvertFragment;

class PagerAdapter extends FragmentStatePagerAdapter {
    PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag = new TextConvertFragment();
                break;
            case 1:
                frag = new BinaryConvertFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Text to Binary";
                break;
            case 1:
                title = "Binary to Text";
                break;
        }
        return title;
    }
}
