package FragmentsPKG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import helper.SQLiteHandler;
import helper.SessionManager;
import mrkking.book.AppConfig;
import mrkking.book.MainActivity;
import mrkking.book.R;

/**
 * Created by Kory on 10/20/2016.
 */
public class Settings extends Fragment {

    public static String TAG = "Browse Fragment";
    Context appCon;

    private ImageView profIMG;
    private EditText opass, npass;
    private Button submit;
    NavigationView navigationView;
    private SQLiteHandler db;
    private SessionManager session;

    public static BrowseFragment newInstance(Context c) {
        Bundle args = new Bundle();

        BrowseFragment fragment = new BrowseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.settings_fragment, container, false);

        opass = (EditText) v.findViewById(R.id.oldpass);
        npass = (EditText) v.findViewById(R.id.newpass);
        submit = (Button) v.findViewById(R.id.btnpasschange);

        // SQLite database handler
        db = new SQLiteHandler(getContext());

        // Session manager
        session = new SessionManager(getContext());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> user = db.getUserDetails();
                String email = user.get("email");
                String pass = opass.getText().toString();
                String newpass = npass.getText().toString();
                changePassWord(email,pass,newpass);
            }
        });
        return v;
    }

    public void changePassWord(final String email,final String password,final String npassword){
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CHANGEPASSWORD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        String errorMsg = "Success";
                        Toast.makeText(getContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("newpassword",npassword);
                return params;
            }

        };

        Log.w("String Request: ", strReq.toString());
        Volley.newRequestQueue(getContext()).add(strReq);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
