package mrkking.book;

/**
 * Created by Kory on 10/20/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import FragmentsPKG.BrowseFragment;
import FragmentsPKG.CreatePostFragment;
import FragmentsPKG.MyPostFragment;
import FragmentsPKG.Settings;
import helper.SQLiteHandler;
import helper.SessionManager;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView tv_name;
    private ImageView iv_image;
    NavigationView navigationView;
    private SQLiteHandler db;
    private SessionManager session;
    private Fragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();
        tv_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_name);
        frag = new BrowseFragment();

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        tv_name.setText("Welcome " + name + "!");


    }

    public void initNavigationDrawer() {

        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                frag = new BrowseFragment();

                switch (id) {
                    case R.id.browse:
                        frag = new BrowseFragment();
                        break;
                    case R.id.my_posts:
                        frag = new MyPostFragment();
                        break;
                    case R.id.my_settings:
                        frag = new Settings();
                        break;
                    case R.id.create_post:
                        frag = new CreatePostFragment();
                        break;
                    case R.id.sign_out:
                        logoutUser();
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        break;
                    default:

                        break;
                }
                drawerLayout.closeDrawers();

                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.flContent, frag).commit();

                return true;
            }
        });

        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView)header.findViewById(R.id.tv_name);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {

        super.onResume();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.flContent, frag).commit();
    }

}
