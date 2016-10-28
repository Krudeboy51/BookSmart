package FragmentsPKG;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adaptors.BrowseListAdaptor;
import DataModels.UserPost;
import helper.SQLiteHandler;
import mrkking.book.AppConfig;
import mrkking.book.R;

/**
 * Created by Kory on 10/20/2016.
 */
public class MyPostFragment extends Fragment{
    public static String TAG = "Posts Fragment";
    ListView lv;
    SQLiteHandler db;

    public void init(View v){
        lv = (ListView) v.findViewById(R.id.lv_myposts);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.posts_fragment, container, false);
        init(v);
        ArrayList<UserPost> post = new ArrayList<>();

        post = getPostList();

        if(post == null)
            post.add(new UserPost("No Items found Sorry", " ", " ", " "));


        BrowseListAdaptor adaptor = new BrowseListAdaptor(getContext(), post);
        lv.setAdapter(adaptor);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private ArrayList<UserPost> getPostList() {
        final ArrayList<UserPost> postlist = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET,
                AppConfig.URL_GET_ALL_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject post = array.getJSONObject(i);
                                postlist.add(new UserPost(post.getString("post_title"),
                                        post.getString("post_date"),
                                        post.getString("unique_id"),
                                        post.getString("post_condition")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String user = db.getUserDetails().get("uid");
                Toast.makeText(getContext(),user,Toast.LENGTH_SHORT).show();
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", user);
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);

        return postlist;
    }


}
