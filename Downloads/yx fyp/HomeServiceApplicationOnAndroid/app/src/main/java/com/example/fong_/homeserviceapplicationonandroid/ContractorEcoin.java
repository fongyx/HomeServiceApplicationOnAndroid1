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

public class ContractorEcoin extends AppCompatActivity {

    TextView mConEcoin;

    DatabaseReference conEcoinDB;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_ecoin);

        mConEcoin = findViewById(R.id.conEcoin2);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        conEcoinDB = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

        conEcoinDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.e("user e coin",Integer.toString(user.geteCoin()));
                mConEcoin.setText(Integer.toString(user.geteCoin()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
