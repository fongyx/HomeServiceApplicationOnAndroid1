package com.example.fong_.homeserviceapplicationonandroid;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.fong_.homeserviceapplicationonandroid.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TopUpDetail extends AppCompatActivity {

    TextView mCusEcoin;

    DatabaseReference cusEcoinDB;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_detail);

        mCusEcoin = findViewById(R.id.cusEcoin);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        cusEcoinDB = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

        cusEcoinDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.e("user e coin",Integer.toString(user.geteCoin()));
                mCusEcoin.setText(Integer.toString(user.geteCoin()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
