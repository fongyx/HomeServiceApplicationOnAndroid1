package com.example.fong_.homeserviceapplicationonandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminMenu extends AppCompatActivity {

    Button mEditEcoin, btn_admin_signout, mAdminChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        mEditEcoin = (Button) findViewById(R.id.editEcoin);
        btn_admin_signout = (Button) findViewById(R.id.btn_admin_signout);
        mAdminChat = (Button) findViewById(R.id.adminChat);


        mEditEcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMenu.this, EditEcoinMenu.class);
                startActivity(intent);
            }
        });

        mAdminChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMenu.this, ChatRoom.class);
                startActivity(intent);
                finish();
            }
        });

        btn_admin_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent a = new Intent (AdminMenu.this,MainActivity.class);
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
