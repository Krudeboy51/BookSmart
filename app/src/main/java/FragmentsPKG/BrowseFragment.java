package FragmentsPKG;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adaptors.BrowseListAdaptor;
import DataModels.UserPost;
import mrkking.book.AppConfig;
import mrkking.book.R;

/**
 * Created by Kory on 10/20/2016.
 */
public class BrowseFragment extends Fragment {

    private ListView lv;

    private void init(View v){
        lv = (ListView) v.findViewById(R.id.lv_browse);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sale_fragment, container, false);
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

    private ArrayList<UserPost> getPostList(){
        final ArrayList<UserPost> postlist = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET,
                AppConfig.URL_GET_ALL_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for(int i = 0; i < array.length(); i++){
                                JSONObject post = array.getJSONObject(i);
                                postlist.add(new UserPost(post.getString("post_title"),
                                        post.getString("post_date"),
                                        post.getString("unique_id"),
                                        post.getString("post_condition")));
                            }
                        }catch (JSONException e){
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
                });

        Volley.newRequestQueue(getContext()).add(request);


        return postlist;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
