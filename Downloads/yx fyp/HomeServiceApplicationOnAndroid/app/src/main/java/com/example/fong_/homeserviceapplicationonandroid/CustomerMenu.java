package com.example.fong_.homeserviceapplicationonandroid;

import android.view.KeyEvent;
import android.widget.Button;
import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.fong_.homeserviceapplicationonandroid.Common.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button mRequestService, mMyRequest, mRateFeedback, mTopUp;

    FirebaseDatabase database;
    DatabaseReference service;

    TextView txtFullName;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_menu);
        setContentView(R.layout.activity_nav_drawer);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        service = database.getReference("Service");


        //Set Name for user
        //View headerView = navigationView.getHeaderView(0);
        //txtFullName = (TextView) headerView.findViewById(R.id.txtFullName);
        //txtFullName.setText(Common.currentUser.getName());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mRequestService = (Button) findViewById(R.id.requestService);
        mMyRequest = (Button) findViewById(R.id.myRequest);
//        mRateFeedback = (Button) findViewById(R.id.rateFeedback);
        mTopUp = (Button) findViewById(R.id.topUp);

        mRequestService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMenu.this, RequestService.class);
                startActivity(intent);

            }
        });

        mMyRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMenu.this, MyRequest.class);
                startActivity(intent);

            }
        });

//        mRateFeedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CustomerMenu.this, RateFeedback.class);
//                startActivity(intent);
//
//            }
//        });

        mTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMenu.this, TopUpDetail.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onKeyDown(int key_code, KeyEvent key_event) {
        if (key_code== KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(key_code, key_event);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /// Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        ////noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
       //     return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history)
        {
            Intent intent = new Intent(CustomerMenu.this, ServiceHistory.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_chat)
        {
            Intent intent = new Intent(CustomerMenu.this, ChatRoom.class);
            startActivity(intent);
            finish();

        }
        else if (id == R.id.nav_sign_out)
        {
            //sign out
            FirebaseAuth.getInstance().signOut();
            Intent a = new Intent (CustomerMenu.this,MainActivity.class);
            startActivity(a);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
