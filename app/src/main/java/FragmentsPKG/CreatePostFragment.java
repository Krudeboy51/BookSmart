package FragmentsPKG;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mrkking.book.R;

/**
 * Created by kingke on 10/21/16.
 */

public class CreatePostFragment extends Fragment {

    public static String TAG = "Create Fragment";

    public static MyPostFragment newInstance() {

        Bundle args = new Bundle();

        MyPostFragment fragment = new MyPostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_post_fragment, container, false);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
