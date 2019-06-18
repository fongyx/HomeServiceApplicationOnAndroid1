package com.example.fong_.homeserviceapplicationonandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fong_.homeserviceapplicationonandroid.Common.Common;
import com.example.fong_.homeserviceapplicationonandroid.Interface.ItemClickListener;
import com.example.fong_.homeserviceapplicationonandroid.Model.Chat;
import com.example.fong_.homeserviceapplicationonandroid.Model.Services;
import com.example.fong_.homeserviceapplicationonandroid.Model.User;
import com.example.fong_.homeserviceapplicationonandroid.ViewHolder.ServiceAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class MyRequest extends AppCompatActivity implements ChildEventListener, ItemClickListener {

    TextView NoService, LongClick;
    Button BookService;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    FirebaseUser firebaseUser;

    DatabaseReference services;


    List<Services> servicesList = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    Context context = this;
    ItemClickListener itemClickListener = this;


    MaterialSpinner spinner;

    ServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);

        //Firebase
        database = FirebaseDatabase.getInstance();
        services = database.getReference("Service");

        //Initialize
        NoService = (TextView)findViewById(R.id.NoService);
        LongClick = (TextView)findViewById(R.id.LongClick);
        BookService = (Button) findViewById(R.id.BookNow);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = (RecyclerView)findViewById(R.id.listServices);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadServiceList();

        BookService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRequest.this, RequestService.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick2(Services services,User user) {
        showAlertDialog(services,user);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.DELETE))
            cancelService(item.getOrder());
        return true;
    }

    private void cancelService(int position){

        String id = servicesList.get(position).getServiceID();

        services.child(id).removeValue();

        Toast.makeText(MyRequest.this,"Service is cancelled",Toast.LENGTH_LONG).show();
        //Refresh Screen
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void loadServiceList() {

        services.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Services services = snapshot.getValue(Services.class);

                    assert services != null;
                    if (services.getServiceStatus().equals("Pending") && services.getCustomerID().equals(firebaseUser.getUid())
                            || services.getServiceStatus().equals("Accepted") && services.getCustomerID().equals(firebaseUser.getUid())){
                        servicesList.add(services);
                    }

//                    if (!services.getCustomerName().equals(firebaseUser.getDisplayName())){
//                        servicesList.add(services);
//                    }
//                    else {
//                        Toast.makeText(MyRequest.this, "Your Service is Empty !", Toast.LENGTH_SHORT).show();
//                    }

                }

                if(servicesList.size()<1)
                {
                    NoService.setVisibility(View.VISIBLE);
                    BookService.setVisibility(View.VISIBLE);
                    LongClick.setVisibility(View.INVISIBLE);

                }
                else
                {
                    NoService.setVisibility(View.INVISIBLE);
                    BookService.setVisibility(View.INVISIBLE);
                    LongClick.setVisibility(View.VISIBLE);
                }

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        adapter = new ServiceAdapter(servicesList,context,user,itemClickListener);
                        recyclerView.setAdapter(adapter);

                        Log.e("Ecoin", Integer.toString(user.geteCoin()));
                        Log.e("Ecoin", Integer.toString(user.geteCoin()));
                        Log.e("Ecoin", Integer.toString(user.geteCoin()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



    }

    public void completeUpdateToDB(final Services services,final User user){

        DatabaseReference callContractorID = database.getReference("User");

        callContractorID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User con = new User();
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    Log.e("user data", user.getId());
                    userList.add(user);

                }

                for (int i = 0; i < userList.size() ;i++){
                    if (userList.get(i).getId().equals(services.getContractorID())){
                        con = userList.get(i);
                        Log.e("contractor",con.getName());
                        break;
                    }
                }

                services.setServiceStatus("Completed");
                user.seteCoin(user.geteCoin() - Integer.valueOf(services.getFixedPrice()));
                con.seteCoin(con.geteCoin() + Integer.valueOf(services.getFixedPrice()));
                Log.e("contractor",Integer.toString(con.geteCoin()));
                Log.e("contractor",con.getId());


                DatabaseReference updateCon = FirebaseDatabase.getInstance().getReference("User").child(con.getId());
                updateCon.setValue(con);

                DatabaseReference completeServiceDB = FirebaseDatabase.getInstance().getReference("Service").child(services.getServiceID());
                completeServiceDB.setValue(services);

                DatabaseReference updateDB = FirebaseDatabase.getInstance().getReference("User").child(user.getId());
                updateDB.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        DatabaseReference completeServiceDB = FirebaseDatabase.getInstance().getReference("Service").child(services.getServiceID());
//        DatabaseReference updateDB = FirebaseDatabase.getInstance().getReference("User").child(user.getId());
//        DatabaseReference updateCon = FirebaseDatabase.getInstance().getReference("User").child(con.getId());

//        completeServiceDB.setValue(services);
//        updateDB.setValue(user);
//        updateCon.setValue(user);


    }

    public void showAlertDialog(final Services services, final User user){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyRequest.this);
        alertDialog.setTitle("One More Step!");
        alertDialog.setMessage("Please Pay by E-Coin");
        alertDialog.setIcon(R.drawable.ic_notifications_black_24dp);

//        final EditText edtFixedPrice = new EditText(RequestService.this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT
//        );
//        edtFixedPrice.setLayoutParams(lp);
//        alertDialog.setView(edtFixedPrice); // Add edit text to alert dialog

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            int fixPrice = Integer.valueOf(services.getFixedPrice());

//            SharedPreferences sharedPreferences = getSharedPreferences("Service2",Context.MODE_PRIVATE);
//            String CustomerName = sharedPreferences.getString("name","");
//            String service_ID = String.valueOf(System.currentTimeMillis());
//            final String SCategory = mCategory.getSelectedItem().toString();
//            final String SAddress = mServiceAddress.getText().toString();
//            final String SDate = mServiceDate.getText().toString();
//            final String STime = mServiceTime.getText().toString();
//            final String SDes = mServiceDes.getText().toString();

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(user.geteCoin() < fixPrice){
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(MyRequest.this);
                    alertDialog2.setTitle("ALERT !");
                    alertDialog2.setMessage("Insufficient E-Coin ! Please Top-Up !");
                    alertDialog2.setIcon(R.drawable.ic_warning_black_24dp);

                    alertDialog2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog2.show();

                }
                else {

                    Toast.makeText(MyRequest.this, "Service is done and paid!", Toast.LENGTH_LONG).show();
                    rateDialog(services, user);

//                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MyRequest.this);
//                    View mView = getLayoutInflater().inflate(R.layout.spinner_layout,null);
//                    mBuilder.setTitle("Rating");
//                    final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinnerID);
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyRequest.this,
//                            android.R.layout.simple_spinner_item,
//                            getResources().getStringArray(R.array.rating_array));
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    mSpinner.setAdapter(adapter);
//
//                    mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("Please Rate For Service")){
//                                Toast.makeText(MyRequest.this, mSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                                services.setRating(mSpinner.getSelectedItem().toString());
//                                Toast.makeText(MyRequest.this, "Thank you for rating!", Toast.LENGTH_LONG).show();
//                                completeUpdateToDB(services,user);
//                                dialog.dismiss();
//                            }
//                            else {
//                                Toast.makeText(MyRequest.this, mSpinner.getSelectedItem().toString() + "Thank you.", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    });
//                    mBuilder.setView(mView);
//                    AlertDialog dialog2 = mBuilder.create();
//                    dialog2.show();


//                    Toast.makeText(MyRequest.this, "Thank you for rating!", Toast.LENGTH_LONG).show();
//
//                    completeUpdateToDB(services,user);

                }

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

    public void rateDialog(final Services services, final User user) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MyRequest.this);
        View mView = getLayoutInflater().inflate(R.layout.spinner_layout,null);
        mBuilder.setTitle("Rating");
        final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinnerID);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyRequest.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.rating_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("Please Rate For Service")){
                    Toast.makeText(MyRequest.this, mSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    services.setRating(mSpinner.getSelectedItem().toString());
                    Toast.makeText(MyRequest.this, "Thank you for rating!", Toast.LENGTH_LONG).show();
                    completeUpdateToDB(services,user);
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(MyRequest.this, mSpinner.getSelectedItem().toString() + ", thank you.", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    rateDialog(services, user);
                }

            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog2 = mBuilder.create();
        dialog2.show();
    }


    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
//        String id = dataSnapshot.getRef()
//        Services services1 = dataSnapshot.getValue(Services.class);
//        services.removeValue();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }


    @Override
    public void onClick(View view, int position, boolean isLongClick) {

    }


}
