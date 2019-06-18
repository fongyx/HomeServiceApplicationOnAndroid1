package com.example.fong_.homeserviceapplicationonandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class ContractorRegisterActivity extends AppCompatActivity {

    EditText mName, mEmail, mContact, mAddress, mPassword;
    Button  mRegister;
    TextInputLayout nameWrapper, emailAddressWrapper, contactWrapper, addressWrapper, passwordWrapper;

    private FirebaseAuth mAuth;

    FirebaseDatabase database;

    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_register);

        mAuth = FirebaseAuth.getInstance();

        mName = (EditText) findViewById(R.id.name);
        mEmail = (EditText) findViewById(R.id.email);
        mContact = (EditText) findViewById(R.id.contact);
        mAddress = (EditText) findViewById(R.id.address);
        mPassword = (EditText) findViewById(R.id.password);

        nameWrapper = findViewById(R.id.nameWrapper);
        emailAddressWrapper = findViewById(R.id.emailAddressWrapper);
        contactWrapper = findViewById(R.id.contactWrapper);
        addressWrapper = findViewById(R.id.addressWrapper);
        passwordWrapper = findViewById(R.id.passwordWrapper);

        mRegister = (Button) findViewById(R.id.btnRegister);

        database = FirebaseDatabase.getInstance();

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAuth.getCurrentUser()!=null){
                    //User is logged in and can redirect to another activity.
                }
                else {

                    final String name = mName.getText().toString().trim();
                    final String email = mEmail.getText().toString().trim();
                    final String address = mAddress.getText().toString().trim();
                    final String contact = mContact.getText().toString().trim();

                    DatabaseReference loadUser = database.getReference("User");

                    loadUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String password = mPassword.getText().toString().trim();

                            if (name.isEmpty()) {
                                nameWrapper.setError("Enter Name");
                                nameWrapper.requestFocus();
                                return;
                            }
                            if (email.isEmpty()) {
                                emailAddressWrapper.setError("Enter Email Address");
                                emailAddressWrapper.requestFocus();
                                return;
                            }
                            if (address.isEmpty()) {
                                addressWrapper.setError("Enter Address");
                                addressWrapper.requestFocus();
                                return;
                            }
                            if (contact.isEmpty()) {
                                contactWrapper.setError("Enter Contact Number");
                                contactWrapper.requestFocus();
                                return;
                            }
                            if (password.isEmpty()) {
                                passwordWrapper.setError("Enter Password");
                                passwordWrapper.requestFocus();
                                return;
                            }

                            userList.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                User user = snapshot.getValue(User.class);
                                assert user != null;
                                Log.e("user data", user.getId());
                                userList.add(user);

                            }

                            for (int i = 0; i < userList.size() ;i++){
                                if (userList.get(i).getName().toLowerCase().equals(name.toLowerCase())){
                                    nameWrapper.setError("User's name existed !");
                                    nameWrapper.requestFocus();
                                    return;
                                }
                            }


                            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        //We need to add additional info in firebase DB
                                        String user_id = mAuth.getCurrentUser().getUid();
                                        User user = new User(user_id,name, email, contact, address, "Contractor",0);
                                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference("User").child(user_id);
                                        current_user_db.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(ContractorRegisterActivity.this, "User created successfully.", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(ContractorRegisterActivity.this, LoginActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else{
                                                    Toast.makeText(ContractorRegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(ContractorRegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

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
