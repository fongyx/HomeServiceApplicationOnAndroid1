package com.example.fong_.homeserviceapplicationonandroid.Interface;

import android.view.View;

import com.example.fong_.homeserviceapplicationonandroid.Model.Services;
import com.example.fong_.homeserviceapplicationonandroid.Model.User;


public interface ItemClickListener {

    void onClick (View view, int position, boolean isLongClick);
    void onClick2 (Services services, User user);

}
