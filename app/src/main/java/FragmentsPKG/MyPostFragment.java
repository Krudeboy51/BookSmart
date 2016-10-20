package FragmentsPKG;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mrkking.book.R;

/**
 * Created by Kory on 10/20/2016.
 */
public class MyPostFragment extends Fragment{
    public static String TAG = "Posts Fragment";

    public static MyPostFragment newInstance() {

        Bundle args = new Bundle();

        MyPostFragment fragment = new MyPostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.posts_fragment, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
