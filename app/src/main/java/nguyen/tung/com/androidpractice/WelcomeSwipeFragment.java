package nguyen.tung.com.androidpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nqtsparda on 11/10/2016.
 */

public class WelcomeSwipeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View welcomeSwipeView = inflater.inflate(R.layout.welcome1, container, false);
        ImageView imageView = (ImageView) welcomeSwipeView.findViewById(R.id.imageWelcome);
        Bundle bundle = getArguments();
        int imagePostion = bundle.getInt("welcome_swipe_position");
        List<Integer> welcomeImgIdList = getWelcomeImgIdList();
        imageView.setImageDrawable(getResources().getDrawable(welcomeImgIdList.get(imagePostion)));
        return welcomeSwipeView;
    }

    public static WelcomeSwipeFragment newInstance(int position){
        WelcomeSwipeFragment fragment = new WelcomeSwipeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("welcome_swipe_position", position);
        fragment.setArguments(bundle);
        return fragment;
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
}
