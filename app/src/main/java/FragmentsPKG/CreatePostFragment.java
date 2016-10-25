package FragmentsPKG;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import helper.SQLiteHandler;
import helper.SessionManager;
import mrkking.book.AppConfig;
import mrkking.book.R;

/**
 * Created by kingke on 10/21/16.
 */

public class CreatePostFragment extends Fragment {

    public enum condition{
        MINT,
        GOOD,
        FAIR,
        SLIGHTLY_FAIR,
        POOR
    }

    TextView tv_title;
    TextView tv_isbn;
    Spinner sp_condition;
    TextView tv_description;
    ImageView iv_image;
    Button submit;
    Uri imageUri;
    SQLiteHandler db;
    private static final int CAMERA_REQUEST = 1888;

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
        init(view);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = tv_title.getText().toString();
                final String isbn = tv_isbn.getText().toString();

                if(!title.matches("") && !isbn.matches("")){
                    String condition = sp_condition.getSelectedItem().toString();
                    createPost(title,isbn,condition);
                }else{
                    Toast.makeText(getContext(),"Sorry, looks like you missed some fields!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        return view;
    }

    public void takePhoto() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1888);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 1888:
                if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    iv_image.setImageBitmap(photo);
                }
                break;
        }
    }

    public void init(View v){
        tv_title = (TextView) v.findViewById(R.id.postf_title);
        tv_isbn = (TextView) v.findViewById(R.id.postf_isbn);
        sp_condition = (Spinner) v.findViewById(R.id.postf_spinner);
        iv_image = (ImageButton) v.findViewById(R.id.postf_image);
        submit = (Button) v.findViewById(R.id.postf_submit);
        db = new SQLiteHandler(getContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    private void createPost(final String title,final String isbn,final String condition){

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            // Check for error node in json
                            if (!error) {
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {

                        }
                    }
                },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Login Error: " + error.getMessage());
                        Toast.makeText(getContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String user = db.getUserDetails().get("uid");
                Map<String, String> params = new HashMap<String, String>();
                params.put("unique_id", user);
                params.put("title", title);
                params.put("isbn", isbn);
                params.put("condition",condition);
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);

    }

}
