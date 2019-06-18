package com.example.fong_.homeserviceapplicationonandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fong_.homeserviceapplicationonandroid.Model.Services;
import com.example.fong_.homeserviceapplicationonandroid.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addDeduct extends AppCompatActivity {

    TextView mEditCoinshow;
    TextView eCoinName;
    Button mAddCoin, mDeductCoin;

    DatabaseReference editCoinDB;
    DatabaseReference editCoinDB222;
    FirebaseUser firebaseUser;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deduct);

        mEditCoinshow = findViewById(R.id.editCoinshow);
        eCoinName = findViewById(R.id.eCoinName);
        mAddCoin = (Button) findViewById(R.id.addCoin);
        mDeductCoin = (Button) findViewById(R.id.deductCoin);


        intent = getIntent();
        final String userid = intent.getStringExtra("userid");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        editCoinDB = FirebaseDatabase.getInstance().getReference("User").child(userid);

        editCoinDB222 = FirebaseDatabase.getInstance().getReference("User").child(userid);


        editCoinDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.e("user e coin",Integer.toString(user.geteCoin()));
                eCoinName.setText(user.getName());
                mEditCoinshow.setText(Integer.toString(user.geteCoin()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mAddCoin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editCoinDB222.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        Log.e("dsadsadsadsadsa","dadsadsadsa");
                        showAlertDialog(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

        mDeductCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCoinDB222.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        Log.e("dsadsadsadsadsa","dadsadsadsa");
                        showAlertDialog2(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void showAlertDialog(final User user){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(addDeduct.this);
        alertDialog.setTitle("One More Step!");
        alertDialog.setMessage("Enter Amount of E-Coin to add");
        alertDialog.setIcon(R.drawable.ic_notifications_black_24dp);

        final EditText edtAddCoin = new EditText(addDeduct.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddCoin.setLayoutParams(lp);
        alertDialog.setView(edtAddCoin); // Add edit text to alert dialog

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

//            int addECoin = 50;
//            int addECoin = Integer.valueOf(edtAddCoin.getText().toString());


            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.seteCoin(user.geteCoin() + Integer.valueOf(edtAddCoin.getText().toString()));

                DatabaseReference updateDB = FirebaseDatabase.getInstance().getReference("User").child(user.getId());
                updateDB.setValue(user);

                Toast.makeText(addDeduct.this, "Add E-Coin Successfully !", Toast.LENGTH_SHORT).show();


            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

    }

    public void showAlertDialog2(final User user){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(addDeduct.this);
        alertDialog.setTitle("One More Step!");
        alertDialog.setMessage("Enter Amount of E-Coin to deduct");
        alertDialog.setIcon(R.drawable.ic_notifications_black_24dp);

        final EditText edtAddCoin = new EditText(addDeduct.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddCoin.setLayoutParams(lp);
        alertDialog.setView(edtAddCoin); // Add edit text to alert dialog

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

//            int addECoin = 50;
//            int addECoin = Integer.valueOf(edtAddCoin.getText().toString());


            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.seteCoin(user.geteCoin() - Integer.valueOf(edtAddCoin.getText().toString()));

                DatabaseReference updateDB = FirebaseDatabase.getInstance().getReference("User").child(user.getId());
                updateDB.setValue(user);

                Toast.makeText(addDeduct.this, "Deduct E-Coin Successfully !", Toast.LENGTH_SHORT).show();



            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

    }
}
