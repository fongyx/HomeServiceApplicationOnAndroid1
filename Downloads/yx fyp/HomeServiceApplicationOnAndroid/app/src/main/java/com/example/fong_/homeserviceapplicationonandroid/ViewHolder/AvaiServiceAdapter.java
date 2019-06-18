package com.example.fong_.homeserviceapplicationonandroid.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fong_.homeserviceapplicationonandroid.Interface.ItemClickListener_AvailableService;
import com.example.fong_.homeserviceapplicationonandroid.Model.Services;
import com.example.fong_.homeserviceapplicationonandroid.R;

import java.util.ArrayList;
import java.util.List;

public class AvaiServiceAdapter extends RecyclerView.Adapter<AvaiServiceViewHolder>{

    private List<Services> listData = new ArrayList<>();
    private Context context;
    private ItemClickListener_AvailableService avai_itemClickListener;

    public AvaiServiceAdapter(List<Services> listData, Context context,ItemClickListener_AvailableService avai_itemClickListener){
        this.listData = listData;
        this.context = context;
        this.avai_itemClickListener = avai_itemClickListener;
    }

    @NonNull
    @Override
    public AvaiServiceViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.avai_service_list_layout,parent,false);
        return new AvaiServiceViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull AvaiServiceViewHolder holder, int position) {

        final Services serviceData = listData.get(position);

        holder.avai_customer_name.setText(serviceData.getCustomerName());
        holder.avai_contractor_name.setText(serviceData.getContractorName());
        holder.avai_service_status.setText(serviceData.getServiceStatus());
        holder.avai_service_date.setText(serviceData.getServiceDate());
        holder.avai_service_time.setText(serviceData.getServiceTime());
        holder.avai_service_address.setText(serviceData.getServiceAddress());
        holder.avai_service_category.setText(serviceData.getCategory());
        holder.avai_service_des.setText(serviceData.getServiceDescription());
        holder.avai_fixed_price.setText("RM" + serviceData.getFixedPrice());
        holder.avai_bidding_price.setText("RM" + serviceData.getBiddingPrice());

        if (serviceData.getServiceStatus().equals("Pending")){
            holder.btn_Accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("serviceData Result",serviceData.getCustomerName());
                    avai_itemClickListener.onClick(serviceData);
                }
            });
        }
        else if (serviceData.getServiceStatus().equals("Accepted")){
            holder.btn_Accept.setEnabled(false);
            holder.btn_Accept.setText("ACCEPTED!");
            holder.btn_Accept.setBackgroundColor(Color.GRAY);
        }

    }

//
//    public void updateServiceToDB(){
//        final SharedPreferences sharedPreferences1 = context.getSharedPreferences("User_data",Context.MODE_PRIVATE);
//        final String ContractorName = sharedPreferences1.getString("name","");
//
//        final SharedPreferences sharedPreferences2 = context.getSharedPreferences("Service1",Context.MODE_PRIVATE);
//        final String ServiceID = sharedPreferences2.getString("serviceID","");
//        final String CustomerName = sharedPreferences2.getString("customerName","");
//        final String ServiceStatus = sharedPreferences2.getString("serviceStatus","");
//        final String SCategory = sharedPreferences2.getString("sCategory","");
//        final String SAddress = sharedPreferences2.getString("sAddress","");
//        final String SDate = sharedPreferences2.getString("sDate","");
//        final String STime = sharedPreferences2.getString("sTime","");
//        final String SDes = sharedPreferences2.getString("sDes","");
//        final String SFixedPrice = sharedPreferences2.getString("sFixedPrice","");
//        final String SBiddingPrice = sharedPreferences2.getString("sBiddingPrice","");
//
//        DatabaseReference serviceDB = FirebaseDatabase.getInstance().getReference("Service").child(ServiceID);
//        Services newService = new Services(ServiceID, CustomerName, ContractorName, ServiceStatus, SCategory, SAddress, SDate, STime, SDes, SFixedPrice, SBiddingPrice);
//        serviceDB.setValue(newService);
//
//        saveData(ServiceID,CustomerName,ContractorName,ServiceStatus,SCategory,SAddress,SDate,STime,SDes,SFixedPrice,SBiddingPrice);
//
//    }
//
//    public void saveData(String serviceID, String customerName,String contractorName, String serviceStatus, String sCategory, String sAddress, String sDate, String sTime,String sDes, String sFixedPrice,String sBiddingPrice )
//    {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("Service2", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("serviceID",serviceID);
//        editor.putString("customerName",customerName);
//        editor.putString("contractorName",contractorName);
//        editor.putString("serviceStatus",serviceStatus);
//        editor.putString("sCategory",sCategory);
//        editor.putString("sAddress",sAddress);
//        editor.putString("sDate",sDate);
//        editor.putString("sTime",sTime);
//        editor.putString("sDes",sDes);
//        editor.putString("sFixedPrice",sFixedPrice);
//        editor.putString("sBiddingPrice",sBiddingPrice);
//        editor.apply();
//    }


    @Override
    public int getItemCount() {
        return listData.size();
    }
}