package nguyen.tung.com.androidpractice;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private WelcomePagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private int position = 1;
    private Handler handler;
    private Runnable animateViewPager  ;
    List<Integer> imageList;
    private  boolean stopSliding;
    int mCurrentPosition = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        imageList = getWelcomeImgIdList();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);

//
//        // layouts of all welcome sliders
//        // add few more layouts if you want
//        layouts = new int[]{
//                R.layout.welcome1,
//                R.layout.welcome2,
//                R.layout.welcome3,
//                R.layout.welcome4};
//
//        // adding bottom dots
        addBottomDots(0);
//
//        // making notification bar transparent
//        changeStatusBarColor();

        myViewPagerAdapter = new WelcomePagerAdapter(getSupportFragmentManager(), viewPager);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position-1);
                mCurrentPosition = position; // Declare mCurrentPosition as a global variable to track the current position of the item in the ViewPager
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // For going from the first item to the last item, Set the current item to the item before the last item if the current position is 0
                if (mCurrentPosition == 0)
                    viewPager.setCurrentItem(imageList.size()-2, false); // lastPageIndex is the index of the last item, in this case is pointing to the 2nd A on the list. This variable should be declared and initialzed as a global variable

                // For going from the last item to the first item, Set the current item to the second item if the current position is on the last
                if (mCurrentPosition == (imageList.size()-1))
                    viewPager.setCurrentItem(1, false);
            }
        });
        viewPager.setCurrentItem(1);
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_UP:
                        // calls when touch release on ViewPager
                        if (imageList != null && imageList.size() != 0) {
                            stopSliding = false;
                            runnable(imageList.size());
                            handler.postDelayed(animateViewPager,
                                    4000);
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // calls when ViewPager touch
                        if (handler != null && stopSliding == false) {
                            stopSliding = true;
                            handler.removeCallbacks(animateViewPager);
                        }
                        break;
                }
                return false;
            }
        });
//        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
//
//        btnSkip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // checking for last page
//                // if last page home screen will be launched
//                int current = getItem(+1);
//                if (current < layouts.length) {
//                    // move to next screen
//                    viewPager.setCurrentItem(current);
//                } else {
//                }
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (imageList != null && imageList.size() != 0) {
            stopSliding = false;
            runnable(imageList.size());
            handler.postDelayed(animateViewPager,
                    4000);
        }
    }

    public List<Integer> getWelcomeImgIdList() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.c2);
        imageList.add(R.drawable.c3);
        imageList.add(R.drawable.c1);
        imageList.add(R.drawable.c2);
        imageList.add(R.drawable.c3);
        return imageList;
    }

    public void runnable(final int size) {
        handler = new Handler();
        animateViewPager = new Runnable() {
            public void run() {
                if (!stopSliding) {
                    if (viewPager.getCurrentItem() == size - 1) {
                        viewPager.setCurrentItem(0, false);
                    } else {
                        viewPager.setCurrentItem(
                                viewPager.getCurrentItem() + 1, true);
                    }
                    handler.postDelayed(animateViewPager, 4000);
                }
            }
        };
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[imageList.size() - 2];

        if(currentPage == -1){
            currentPage = imageList.size() -3;
        } else if(currentPage == (imageList.size() - 2)){
            currentPage = 0;
        }

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.myWhite));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.myBlack));
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
