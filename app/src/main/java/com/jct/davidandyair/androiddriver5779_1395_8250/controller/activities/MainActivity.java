package com.jct.davidandyair.androiddriver5779_1395_8250.controller.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;
import com.jct.davidandyair.androiddriver5779_1395_8250.controller.fragments.AboutUsFragment;
import com.jct.davidandyair.androiddriver5779_1395_8250.controller.fragments.HomeFragment;
import com.jct.davidandyair.androiddriver5779_1395_8250.controller.fragments.MyDrivesFragment;
import com.jct.davidandyair.androiddriver5779_1395_8250.controller.fragments.OrdersFragment;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Driver driver;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        //get the driver
        Intent intent = getIntent();
        driver = (Driver)intent.getSerializableExtra("driver");

        //set the menu header to the driver name
        TextView driverName = findViewById(R.id.driver_name);
        driverName.setText(driver.getFirstName()+" " + driver.getLastName());*/


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here:

        int id = item.getItemId();

        if (id == R.id.nav_orders) {
            OrdersFragment ordersFragment = new OrdersFragment();
            loadFragment(ordersFragment);

        } else if (id == R.id.nav_my_drives) {
            MyDrivesFragment myDrivesFragment = new MyDrivesFragment();
            loadFragment(myDrivesFragment);

        } else if (id == R.id.nav_home) {
            HomeFragment homeFragment = new HomeFragment();
            loadFragment(homeFragment);

        } else if (id == R.id.nav_about) {
            AboutUsFragment aboutUsFragment = new AboutUsFragment();
            loadFragment(aboutUsFragment);

        } else if (id == R.id.nav_share) {
            //TODO: Add Share Options

        } else if (id == R.id.nav_exit) {
            //TODO: Close The App

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}