package Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import DataModels.UserPost;
import mrkking.book.R;

/**
 * Created by kingke on 10/21/16.
 */

public class BrowseListAdaptor extends BaseAdapter {

    Context context;
    ArrayList<UserPost> posts;
    TextView title, date, user;

    public void init(View v){
        title = (TextView) v.findViewById(R.id.lv_item_title);
        date = (TextView) v.findViewById(R.id.lv_item_date);
        user = (TextView) v.findViewById(R.id.lv_item_user);
    }

    public BrowseListAdaptor(Context c, ArrayList<UserPost> list){
        context = c;
        posts = list;
    }

    @Override
    public int getCount() {
        return posts.size();
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
        View v = view;


        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(R.layout.lv_browse_item, null);
        }

        init(v);

        title.setText(posts.get(i).getTitle());
        user.setText(posts.get(i).getUser());
        date.setText(posts.get(i).getPostDate());
        return v;
    }


}
