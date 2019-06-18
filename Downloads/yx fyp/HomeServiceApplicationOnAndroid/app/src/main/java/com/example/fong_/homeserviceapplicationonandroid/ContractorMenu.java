package com.example.fong_.homeserviceapplicationonandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContractorMenu extends AppCompatActivity {

    Button mAvailableService, mConRateFeedback, mMyEcoin, mContractorChat, mServiceHistory, mSignout;

    FirebaseDatabase database;
    DatabaseReference conService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_menu);


        mAvailableService = (Button) findViewById(R.id.getAvailableService);
//        mConRateFeedback = (Button) findViewById(R.id.viewRateFeedback);
        mMyEcoin = (Button) findViewById(R.id.conEcoin);
        mContractorChat = (Button) findViewById(R.id.contractorChat);
        mServiceHistory = (Button) findViewById(R.id.viewHistory);
        mSignout = (Button) findViewById(R.id.btn_contractor_signout);

        mAvailableService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContractorMenu.this, AvailableService.class);
                startActivity(intent);

            }
        });

        mContractorChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContractorMenu.this, ChatRoom.class);
                startActivity(intent);
                finish();

            }
        });

        mServiceHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContractorMenu.this, ServiceHistory.class);
                startActivity(intent);

            }
        });

//        mConRateFeedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ContractorMenu.this, ContractorRateFeedback.class);
//                startActivity(intent);
//
//            }
//        });

        mMyEcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContractorMenu.this, ContractorEcoin.class);
                startActivity(intent);

            }
        });

        mSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent a = new Intent (ContractorMenu.this,MainActivity.class);
                startActivity(a);
                finish();

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

}
