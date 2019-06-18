package com.example.fong_.homeserviceapplicationonandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fong_.homeserviceapplicationonandroid.Interface.ItemClickListener;
import com.example.fong_.homeserviceapplicationonandroid.Model.Services;
import com.example.fong_.homeserviceapplicationonandroid.Model.User;
import com.example.fong_.homeserviceapplicationonandroid.ViewHolder.ServiceAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceHistory extends AppCompatActivity implements ItemClickListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    FirebaseDatabase database;
    DatabaseReference services;
    FirebaseUser firebaseUser;


    ServiceAdapter adapter;

    List<Services> servicesList = new ArrayList<>();
    Context context = this;
    ItemClickListener itemClickListener = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_history);

        //Firebase
        database = FirebaseDatabase.getInstance();
        services = database.getReference("Service");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = (RecyclerView)findViewById(R.id.completed_service);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadCompletedServiceList();
    }

    private void loadCompletedServiceList() {

        services.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Services services = snapshot.getValue(Services.class);

                    assert services != null;
                    Log.e("Current user id",firebaseUser.getUid());
                    Log.e("Current customer id",services.getCustomerID());

                    if (services.getServiceStatus().equals("Completed") && services.getCustomerID().equals(firebaseUser.getUid())
                            || services.getServiceStatus().equals("Completed") && services.getContractorID().equals(firebaseUser.getUid())){
//                        Log.e("Current user id",firebaseUser.getUid());
                        servicesList.add(services);
                    }


                }
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        adapter = new ServiceAdapter(servicesList,context,user,itemClickListener);
                        recyclerView.setAdapter(adapter);

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

    @Override
    public void onClick(View view, int position, boolean isLongClick) {

    }

    @Override
    public void onClick2(Services services, User user) {

    }
}
