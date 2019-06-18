package com.example.fong_.homeserviceapplicationonandroid.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fong_.homeserviceapplicationonandroid.Common.Common;
import com.example.fong_.homeserviceapplicationonandroid.Database.Database;
import com.example.fong_.homeserviceapplicationonandroid.Model.Services;
import com.example.fong_.homeserviceapplicationonandroid.R;

import java.util.ArrayList;
import java.util.List;

public class AvaiServiceViewHolder extends RecyclerView.ViewHolder {


    public TextView avai_service_category, avai_service_address, avai_service_date, avai_service_time, avai_service_des, avai_fixed_price, avai_bidding_price;
    public TextView avai_customer_name, avai_contractor_name, avai_service_status;
    public Button btn_Accept;

    public AvaiServiceViewHolder(View itemView) {
        super(itemView);
        avai_customer_name = (TextView)itemView.findViewById(R.id.avai_customer_name);
        avai_contractor_name = (TextView)itemView.findViewById(R.id.avai_contractor_name);
        avai_service_status = (TextView)itemView.findViewById(R.id.avai_service_status);
        avai_service_date = (TextView)itemView.findViewById(R.id.avai_service_date);
        avai_service_time = (TextView)itemView.findViewById(R.id.avai_service_time);
        avai_service_address = (TextView)itemView.findViewById(R.id.avai_service_address);
        avai_service_category = (TextView)itemView.findViewById(R.id.avai_service_category);
        avai_service_des = (TextView)itemView.findViewById(R.id.avai_service_des);
        avai_fixed_price = (TextView)itemView.findViewById(R.id.avai_fixed_price);
        avai_bidding_price = (TextView)itemView.findViewById(R.id.avai_bidding_price);
        btn_Accept = (Button) itemView.findViewById(R.id.btn_Accept);

    }

}
