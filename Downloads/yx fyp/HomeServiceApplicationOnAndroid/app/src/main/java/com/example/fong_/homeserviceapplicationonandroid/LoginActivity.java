package com.example.fong_.homeserviceapplicationonandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fong_.homeserviceapplicationonandroid.Common.Common;
import com.example.fong_.homeserviceapplicationonandroid.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {


    DatabaseReference current_user_db;
    private EditText mEmail, mPassword;
    private Button btnLogin;
    private TextInputLayout userEmailWrapper, userPasswordWrapper;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        userEmailWrapper = findViewById(R.id.userEmailWrapper);
        userPasswordWrapper = findViewById(R.id.userPasswordWrapper);

        btnLogin = (Button) findViewById(R.id.btnLogin);


        mAuth = FirebaseAuth.getInstance();


//        if (mAuth.getCurrentUser() != null) {
//            SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
//            String userType = sharedPreferences.getString("userType","");
//            String email = mAuth.getCurrentUser().getEmail();
//
//            if(userType.equals("Contractor"))
//            {
//                startActivity(new Intent(LoginActivity.this,ContractorMenu.class));
//                finish();
//            }
//            else if (userType.equals("Customer"))
//            {
//                startActivity(new Intent(LoginActivity.this, CustomerMenu.class));
//                finish();
//            }
//            else if (email.toString().equals("admin@mail.com") || userType.equals("Admin"))
//            {
//                Intent intent = new Intent(LoginActivity.this, AdminMenu.class);
//                startActivity(intent);
//                finish();
//            }
//        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                if (email.isEmpty()) {
                    userEmailWrapper.setError("Enter Email");
                    userEmailWrapper.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    userPasswordWrapper.setError("Enter Password");
                    userPasswordWrapper.requestFocus();
                    return;
                }

                showProgressDialog();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful())
                        {
                            //We can return to the another activity

                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();


                        }
                        else
                        {
                            getUserData(email);

                        }
                    }
                });
            }
        });
    }

    public void getUserData(final String email)
    {
        current_user_db = FirebaseDatabase.getInstance().getReference("User").getRef();
        current_user_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    User user = new User();
                    user = ds.getValue(User.class);

                    if (email.equals(user.getEmail()))
                    {
                        String name = user.getName();
                        String address = user.getAddress();
                        String contact = user.getContact();
                        String userType = user.getUserType();
                        int eCoin = user.geteCoin();
                        saveData(name, email, contact, address, userType, eCoin);

                        if (userType.equals("Contractor"))
                        {
                            Intent intent = new Intent(LoginActivity.this, ContractorMenu.class);
                            startActivity(intent);
                            finish();
                        }
                        else if (userType.equals("Customer"))
                        {
                            Intent intent = new Intent(LoginActivity.this, CustomerMenu.class);
                            startActivity(intent);
                            finish();
                        }
                        else if (userType.equals("Admin") && email.toString().equals("admin@mail.com"))
                        {
                            Intent intent = new Intent(LoginActivity.this, AdminMenu.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void saveData(String name, String email, String contact, String address, String userType, int Ecoin)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("User_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        final String id = mAuth.getCurrentUser().getUid();
        String coin = Integer.toString(Ecoin);
        editor.putString("name",name);
        editor.putString("email",email);
        editor.putString("contact",contact);
        editor.putString("address",address);
        editor.putString("userType",userType);
        editor.putString("Ecoin",coin);
        editor.putString("userID",id);
        editor.apply();

    }

    public void showProgressDialog()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait.");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

}
