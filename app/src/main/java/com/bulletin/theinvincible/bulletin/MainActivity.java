package com.bulletin.theinvincible.bulletin;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  General.Transfer {
    FragmentManager frag;
    Bundle bundle;
    StringList mStringList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        frag = getSupportFragmentManager();

        if (id == R.id.nav_sport) {
            frag.beginTransaction().replace(R.id.content_frame, new Sport()).commit();
        } else if (id == R.id.nav_entertainment) {
            frag.beginTransaction().replace(R.id.content_frame, new Entertainment()).commit();
        } else if (id == R.id.nav_general) {
            frag.beginTransaction().replace(R.id.content_frame, new General()).commit();
        } else if (id == R.id.nav_business) {
            Business business = new Business();

            frag.beginTransaction().replace(R.id.content_frame, business).commit();
        } else if (id == R.id.nav_techno) {
            frag.beginTransaction().replace(R.id.content_frame, new Technology()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


//    @Override
//    public void send(StringList stringList) {
////        Log.d("ashu", "stronglist value in mainactivity business " + stringList);
////        bundle = new Bundle();
////        bundle.putParcelable("businessnews", stringList);
//        BusinessDetail businessDetail = new BusinessDetail();
////        businessDetail.setArguments(bundle);
//        frag = getSupportFragmentManager();
//        frag.beginTransaction().replace(R.id.content_frame, businessDetail).commit();
//    }


    @Override
    public void senda(StringList stringList) {
        Log.d("ashu", "stronglist value in mainactivity genral " + stringList);
        bundle = new Bundle();
        bundle.putParcelable("generalnews", stringList);
        GeneralDetail generalDetail = new GeneralDetail();
        generalDetail.setArguments(bundle);
        frag = getSupportFragmentManager();
        frag.beginTransaction().replace(R.id.content_frame, generalDetail).commit();

    }
}
