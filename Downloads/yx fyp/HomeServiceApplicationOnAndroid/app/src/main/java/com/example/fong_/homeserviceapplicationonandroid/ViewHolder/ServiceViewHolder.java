package com.example.fong_.homeserviceapplicationonandroid.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fong_.homeserviceapplicationonandroid.Common.Common;
import com.example.fong_.homeserviceapplicationonandroid.Interface.ItemClickListener;
import com.example.fong_.homeserviceapplicationonandroid.R;

public class ServiceViewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener, View.OnCreateContextMenuListener{


    public TextView service_category, service_address, service_date, service_time, service_des, fixed_price, bidding_price;
    public TextView customer_name, contractor_name, service_status,service_rating;
    public Button btn_Complete;

    private ItemClickListener itemClickListener;


    public ServiceViewHolder(View itemView) {
        super(itemView);
        customer_name = (TextView)itemView.findViewById(R.id.customer_name);
        contractor_name = (TextView)itemView.findViewById(R.id.contractor_name);
        service_status = (TextView)itemView.findViewById(R.id.service_status);
        service_date = (TextView)itemView.findViewById(R.id.service_date);
        service_time = (TextView)itemView.findViewById(R.id.service_time);
        service_address = (TextView)itemView.findViewById(R.id.service_address);
        service_category = (TextView)itemView.findViewById(R.id.service_category);
        service_des = (TextView)itemView.findViewById(R.id.service_des);
        fixed_price = (TextView)itemView.findViewById(R.id.fixed_price);
        bidding_price = (TextView)itemView.findViewById(R.id.bidding_price);
        btn_Complete = (Button) itemView.findViewById(R.id.btn_Complete);
        service_rating = (TextView) itemView.findViewById(R.id.rating1);


        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);


    }


    public  void setItemClickListener (ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

//        itemClickListener.onClick(view,getAdapterPosition(),false);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select The Action");
        menu.add(0,0,getAdapterPosition(),Common.DELETE);

    }
}
