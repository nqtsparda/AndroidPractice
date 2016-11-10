package nguyen.tung.com.androidpractice;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by nqtsparda on 11/10/2016.
 */

public class WelcomePagerAdapter extends FragmentStatePagerAdapter {

    int mCurrentPosition = 1;
    List<Bitmap> welcomeImgs;
    ViewPager pager;

    public WelcomePagerAdapter(FragmentManager fm, ViewPager pager) {
        super(fm);
        this.pager = pager;
    }


    @Override
    public Fragment getItem(int position) {
        WelcomeSwipeFragment fragment = WelcomeSwipeFragment.newInstance(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
