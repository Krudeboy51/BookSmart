package FragmentsPKG;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.File;

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
                //String desc = tv_description.getText().toString();

                if(!title.matches("") && !isbn.matches("")){
                    String condition = sp_condition.getSelectedItem().toString();

                }else{
                    Toast.makeText(getContext(),"Sorry, looks like you missed some fields!",Toast.LENGTH_SHORT).show();
                }

                iv_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        takePhoto();
                    }
                });
            }
        });

        return view;
    }

    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        getActivity().startActivityForResult(intent, 100);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Activity result","HERERERERER");
        switch (requestCode) {
            case 100:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = getActivity().getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        iv_image.setImageBitmap(bitmap);
                        Toast.makeText(getActivity(), selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

    public void init(View v){
        tv_title = (TextView) v.findViewById(R.id.postf_title);
        tv_isbn = (TextView) v.findViewById(R.id.postf_isbn);
        sp_condition = (Spinner) v.findViewById(R.id.postf_spinner);
       // tv_description = (TextView) v.findViewById(R.id.postf_desc);
        iv_image = (ImageButton) v.findViewById(R.id.postf_image);
        submit = (Button) v.findViewById(R.id.postf_submit);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    private void createPost(String title, String isbn, String desc, String condition){

    }

}
