package Adaptors;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import DataModels.UserPost;

/**
 * Created by kingke on 10/21/16.
 */

public class BrowseListAdaptor extends BaseAdapter {



    public BrowseListAdaptor(Context c, ArrayList<UserPost> list){

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
