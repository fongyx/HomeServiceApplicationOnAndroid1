package com.example.fong_.homeserviceapplicationonandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.fong_.homeserviceapplicationonandroid.Interface.ItemClickListener_AvailableService;
import com.example.fong_.homeserviceapplicationonandroid.Model.Services;
import com.example.fong_.homeserviceapplicationonandroid.ViewHolder.AvaiServiceAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AvailableService extends AppCompatActivity implements ChildEventListener,ItemClickListener_AvailableService {

    //View
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference avai_services;

    List<Services> servicesList = new ArrayList<>();
    AvaiServiceAdapter adapter;

    Context context = this;
    ItemClickListener_AvailableService interface_service = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_service);

        //Firebase
        database = FirebaseDatabase.getInstance();
        avai_services = database.getReference("Service");

        //Init View
        recyclerView = (RecyclerView)findViewById(R.id.available_listServices);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadAvailableServiceList();


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        return true;
    }

    @Override
    public void onClick(@NonNull Services services) {

        updateServiceToDB(services);
        if (android.os.Build.VERSION.SDK_INT >= 15){
            //Code for recreate
            recreate();

        }else{
            //Code for Intent
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }


    private void loadAvailableServiceList() {

        avai_services.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                servicesList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Services services = snapshot.getValue(Services.class);


                    assert services != null;
                    if (services.getServiceStatus().equals("Pending") || services.getServiceStatus().equals("Accepted")){
                        servicesList.add(services);
                    }


                }

                adapter = new AvaiServiceAdapter(servicesList,context,interface_service);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        servicesList = new Database(this).getBooking();
//        adapter = new AvaiServiceAdapter(servicesList,this,this);
//        recyclerView.setAdapter(adapter);


    }

    public void updateServiceToDB(Services services){
        final SharedPreferences sharedPreferences1 = this.getSharedPreferences("User_data",Context.MODE_PRIVATE);
        services.setContractorName(sharedPreferences1.getString("name",""));
        services.setContractorID(sharedPreferences1.getString("userID",""));


//        final SharedPreferences sharedPreferences2 = this.getSharedPreferences("Service1",Context.MODE_PRIVATE);
        //final String ServiceID = sharedPreferences2.getString("serviceID","");
        services.setServiceStatus("Accepted");


        DatabaseReference serviceDB = FirebaseDatabase.getInstance().getReference("Service").child(services.getServiceID());
//        Services newService = new Services(ServiceID, CustomerName, ContractorName, ServiceStatus, SCategory, SAddress, SDate, STime, SDes, SFixedPrice, SBiddingPrice);
        serviceDB.setValue(services);

        saveData(services);

    }

    public void saveData(Services services)
    {
        SharedPreferences sharedPreferences = this.getSharedPreferences("Service2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("serviceID",services.getServiceID());
        editor.putString("customerName",services.getCustomerName());
        editor.putString("customerID",services.getCustomerID());
        editor.putString("contractorName",services.getContractorName());
        editor.putString("contractorID",services.getContractorID());
        editor.putString("serviceStatus",services.getServiceStatus());
        editor.putString("sCategory",services.getCategory());
        editor.putString("sAddress",services.getServiceAddress());
        editor.putString("sDate",services.getServiceDate());
        editor.putString("sTime",services.getServiceTime());
        editor.putString("sDes",services.getServiceDescription());
        editor.putString("sFixedPrice",services.getFixedPrice());
        editor.putString("sBiddingPrice",services.getBiddingPrice());
        editor.apply();
    }



    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }


}
