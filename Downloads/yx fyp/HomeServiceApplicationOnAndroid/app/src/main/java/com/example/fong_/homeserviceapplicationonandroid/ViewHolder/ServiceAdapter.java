package com.example.fong_.homeserviceapplicationonandroid.ViewHolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fong_.homeserviceapplicationonandroid.Interface.ItemClickListener;
import com.example.fong_.homeserviceapplicationonandroid.Model.Services;
import com.example.fong_.homeserviceapplicationonandroid.Model.User;
import com.example.fong_.homeserviceapplicationonandroid.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceViewHolder> {

    private List<Services> listData;
//    private List<User> listUserData = new ArrayList<>();
    private Context context;
    private ItemClickListener itemClickListener;
    private User user;


    public ServiceAdapter(List<Services> listData, Context context,User user,ItemClickListener itemClickListener){
        this.listData = listData;
        this.context = context;
        this.user = user;
        this.itemClickListener = itemClickListener;
    }
//
//    public ServiceAdapter(List<Services> listData, List<User> listUserData,Context context){
//        this.listData = listData;
//        this.listUserData = listUserData;
//        this.context = context;
//
//    }

    public ServiceAdapter(List<Services> listData, Context context, ItemClickListener itemClickListener){
        this.listData = listData;
        this.context = context;
        this.itemClickListener = itemClickListener;

    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.service_list_layout,parent,false);
        return  new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, final int position) {



        holder.customer_name.setText(listData.get(position).getCustomerName());
        holder.contractor_name.setText(listData.get(position).getContractorName());
        holder.service_status.setText(listData.get(position).getServiceStatus());
        holder.service_date.setText(listData.get(position).getServiceDate());
        holder.service_time.setText(listData.get(position).getServiceTime());
        holder.service_address.setText(listData.get(position).getServiceAddress());
        holder.service_category.setText(listData.get(position).getCategory());
        holder.service_des.setText(listData.get(position).getServiceDescription());
        holder.fixed_price.setText("RM" + listData.get(position).getFixedPrice());
        holder.bidding_price.setText("RM" +listData.get(position).getBiddingPrice());
        holder.service_rating.setText(listData.get(position).getRating());


        if (listData.get(position).getServiceStatus().equals("Accepted")){
            holder.btn_Complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick2(listData.get(position),user);
                }
            });
        }
        else if (listData.get(position).getServiceStatus().equals("Pending")){
            holder.btn_Complete.setVisibility(GONE);
        }
        else if (listData.get(position).getServiceStatus().equals("Completed")){
            holder.btn_Complete.setEnabled(false);
            holder.btn_Complete.setText("COMPLETED!");
            holder.btn_Complete.setBackgroundColor(Color.GRAY);
        }


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

//    public User callUser(){
//        User user;
//        SharedPreferences sharedPreferences = context.getSharedPreferences("User_data",Context.MODE_PRIVATE);
//        final String userID = sharedPreferences.getString("userID","");
//        Log.e("user id",userID);
//        Log.e("user id",userID);
//        Log.e("user id",userID);
//        Log.e("user id",userID);
//        Log.e("user id",userID);
//
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//                username.setText(user.getName());
//                profile_image.setImageResource(R.mipmap.ic_launcher);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("User");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    User user = snapshot.getValue(User.class);
//
//                    assert user != null;
//                    Log.e("user data",user.getName());
//                    if (user.getId().equals(userID)){
//                        listUserData.add(user);
//                        break;
//                    }
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        user = listUserData.get(0);
//        Log.e("sdsadsads",user.getName());
//        Log.e("sdsadsads",user.getName());
//        Log.e("sdsadsads",user.getName());
//        Log.e("sdsadsads",user.getName());
//        Log.e("sdsadsads",user.getName());
//        Log.e("sdsadsads",user.getName());
//        Log.e("sdsadsads",user.getName());
//        Log.e("sdsadsads",user.getName());
//        Log.e("sdsadsads",user.getName());
//        Log.e("sdsadsads",user.getName());
//        return user;


//    }

}
