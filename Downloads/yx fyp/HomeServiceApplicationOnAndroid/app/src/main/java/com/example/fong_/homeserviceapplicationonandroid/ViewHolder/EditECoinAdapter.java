package com.example.fong_.homeserviceapplicationonandroid.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fong_.homeserviceapplicationonandroid.MessageActivity;
import com.example.fong_.homeserviceapplicationonandroid.Model.User;
import com.example.fong_.homeserviceapplicationonandroid.R;
import com.example.fong_.homeserviceapplicationonandroid.addDeduct;

import java.util.List;

public class EditECoinAdapter extends RecyclerView.Adapter<EditECoinAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;

    public EditECoinAdapter(Context mContext, List<User> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public EditECoinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new EditECoinAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditECoinAdapter.ViewHolder holder, int position) {

        final User user = mUsers.get(position);
        holder.username.setText(user.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, addDeduct.class);
                intent.putExtra("userid",user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.chat_user_username);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }
}