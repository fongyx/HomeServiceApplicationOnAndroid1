package com.example.fong_.homeserviceapplicationonandroid;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.DigitsKeyListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fong_.homeserviceapplicationonandroid.Model.Services;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RequestService extends AppCompatActivity {

    Spinner mCategory;
    EditText mServiceAddress, mServiceDate, mServiceTime, mServiceDes;
    Button mFixedPrice;
//    Button mBidding;

    int mYear, mMonth, mDay, mHour, mMinute;
    private Calendar calendar;
    FirebaseDatabase database;
    DatabaseReference service;
    Services model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);


        //Firebase
        database = FirebaseDatabase.getInstance();
        service = database.getReference("Service");

        mCategory = (Spinner) findViewById(R.id.category);

        mServiceAddress = (EditText) findViewById(R.id.serviceAddress);
        mServiceDate = (EditText) findViewById(R.id.serviceDate);
        mServiceTime = (EditText) findViewById(R.id.serviceTime);
        mServiceDes = (EditText) findViewById(R.id.serviceDes);

        mFixedPrice = (Button) findViewById(R.id.fixedPrice);
//        mBidding = (Button) findViewById(R.id.bidding);

        calendar = Calendar.getInstance();
        mHour =calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        //Fixed Price booking add into database
        mFixedPrice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String SCategory = mCategory.getSelectedItem().toString();
                final String SAddress = mServiceAddress.getText().toString();
                final String SDate = mServiceDate.getText().toString();
                final String STime = mServiceTime.getText().toString();
                final String SDes = mServiceDes.getText().toString();

                if (SCategory.isEmpty() || SAddress.isEmpty() || SDate.isEmpty() || STime.isEmpty() || SDes.isEmpty()) {
                    Toast.makeText(RequestService.this, "Please fill in all field above before setting the fixed price.", Toast.LENGTH_LONG).show();
                }
                else {
                    showAlertDialog();
                }
            }
        });

        mServiceDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mCurrentDate = Calendar.getInstance();
                mYear = mCurrentDate.get(Calendar.YEAR);
                mMonth = mCurrentDate.get(Calendar.MONTH);
                mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(RequestService.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
                        mServiceDate.setText(sdf.format(myCalendar.getTime()));
                        mDay = selectedday;
                        mMonth = selectedmonth;
                        mYear = selectedyear;
                        if (mYear<2019 || mYear>2019)
                        {
                            Toast.makeText(RequestService.this,"Please select this year", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, mYear, mMonth, mDay
                );

                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() + 604800000);
                mDatePicker.show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mServiceDate.setShowSoftInputOnFocus(false);
            mServiceTime.setShowSoftInputOnFocus(false);
        } else {
            try {
                final Method method = EditText.class.getMethod(
                        "setShowSoftInputOnFocus"
                        , new Class[]{boolean.class});
                method.setAccessible(true);
                method.invoke(mServiceDate, false);
                method.invoke(mServiceTime, false);
            } catch (Exception e) {
                // ignore
            }
        }
    }



    public void showAlertDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RequestService.this);
        alertDialog.setTitle("One More Step!");
        alertDialog.setMessage("Enter your Fixed Price of this service");

        final EditText edtFixedPrice = new EditText(RequestService.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtFixedPrice.setLayoutParams(lp);
        edtFixedPrice.setKeyListener(new DigitsKeyListener());
        alertDialog.setView(edtFixedPrice); // Add edit text to alert dialog
        alertDialog.setIcon(R.drawable.ic_notifications_black_24dp);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            SharedPreferences sharedPreferences = getSharedPreferences("User_data",Context.MODE_PRIVATE);
            String CustomerName = sharedPreferences.getString("name","");
            String CustomerID = sharedPreferences.getString("userID","");
            String service_ID = String.valueOf(System.currentTimeMillis());
            final String SCategory = mCategory.getSelectedItem().toString();
            final String SAddress = mServiceAddress.getText().toString();
            final String SDate = mServiceDate.getText().toString();
            final String STime = mServiceTime.getText().toString();
            final String SDes = mServiceDes.getText().toString();



            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (edtFixedPrice.getText().toString().isEmpty()) {
                    Toast.makeText(RequestService.this,"Fixed Price cannot be empty!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    showAlertDialog();
                }
                else if (!edtFixedPrice.getText().toString().matches("\\d+(?:\\.\\d+)?")) {
                    Toast.makeText(RequestService.this,"Fixed Price only accept numeric numbers!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    showAlertDialog();
                }
                else {

//                saveData(service_ID,CustomerName,"Accepted",SCategory,SAddress,SDate,STime,SDes,edtFixedPrice.getText().toString(),"0");

                    //submit to Firebase
                    //using System.CurrentMilli as Primary key(which is "service_ID")
                    service.child(service_ID);
                    model = new Services(service_ID, CustomerName, CustomerID, "", "", "Pending", SCategory, SAddress, SDate, STime, SDes, edtFixedPrice.getText().toString(), "0", "");
                    service.child(service_ID).setValue(model);

                    Toast.makeText(RequestService.this, "Service is Made", Toast.LENGTH_LONG).show();
                    Intent a = new Intent(RequestService.this, CustomerMenu.class);
                    startActivity(a);
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

//    public void saveData(String serviceID, String customerName, String serviceStatus, String sCategory, String sAddress, String sDate, String sTime,String sDes, String sFixedPrice,String sBiddingPrice )
//    {
//        SharedPreferences sharedPreferences = getSharedPreferences("Service1", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("serviceID",serviceID);
//        editor.putString("customerName",customerName);
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

//    public saveToDB (String customerName, String contractorName, String serviceStatus, String category, String serviceAddress, String serviceDate, String serviceTime, String serviceDescription, String fixedPrice, String biddingPrice){
//        DatabaseReference serviceDB = service;
//        Services newService = new Services(customerName,contractorName,serviceStatus,category,serviceAddress,serviceDate,serviceTime,serviceDescription,fixedPrice,biddingPrice);
//        serviceDB.child(String.valueOf(System.currentTimeMillis())).setValue(newService);
//        Toast.makeText(RequestService.this, "Service is Made", Toast.LENGTH_LONG).show();
//        Intent a = new Intent(RequestService.this, CustomerMenu.class);
//        startActivity(a);
//        finish();
//
//    }


    public class RangeTimePickerDialog extends TimePickerDialog {

        private int minHour = 0;
        private int minMinute =0;

        private int maxHour = 24;
        private int maxMinute = 30;

        private int currentHour = 0;
        private int currentMinute = 0;

        private Calendar calendar = Calendar.getInstance();
        private DateFormat dateFormat;


        public RangeTimePickerDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
            super(context, callBack, hourOfDay, minute, is24HourView);
            currentHour = hourOfDay;
            currentMinute = minute;
            dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

            try {
                Class<?> superclass = getClass().getSuperclass();
                Field mTimePickerField = superclass.getDeclaredField("mTimePicker");
                mTimePickerField.setAccessible(true);
                TimePicker mTimePicker = (TimePicker) mTimePickerField.get(this);
                mTimePicker.setOnTimeChangedListener(this);
            } catch (NoSuchFieldException e) {
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            }
        }

        public void setMin(int hour, int minute) {
            minHour = hour;
            minMinute = minute;
        }

        public void setMax(int hour, int minute) {
            maxHour = hour;
            maxMinute = minute;
        }

        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

            boolean validTime = true;
            if (hourOfDay < minHour || (hourOfDay == minHour && minute < minMinute)){
                validTime = false;
            }

            if (hourOfDay  > maxHour || (hourOfDay == maxHour && minute > maxMinute)){
                validTime = false;
            }
            if (hourOfDay< 7)
            {
                Toast.makeText(RequestService.this,"Sorry, services will be start at 7am", Toast.LENGTH_LONG).show();
            }
            else if (hourOfDay>17 && minute>30 ||hourOfDay>18)
            {
                Toast.makeText(RequestService.this,"Sorry, latest booking is 5.30pm", Toast.LENGTH_LONG).show();
            }
            if (validTime) {
                currentHour = hourOfDay;
                currentMinute = minute;
            }

            updateTime(currentHour, currentMinute);
            updateDialogTitle(view, currentHour, currentMinute);
        }

        private void updateDialogTitle(TimePicker timePicker, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            mServiceTime.setText(dateFormat.format(calendar.getTime()));
        }
    }

    public void setTime(View view) {
        showDialog(998);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub

        if (id == 998) {
            return new RangeTimePickerDialog(this, myTimeListener, mHour, mMinute, true);
        }
        return null;
    }
    private RangeTimePickerDialog.OnTimeSetListener myTimeListener = new
            RangeTimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(
                        TimePicker arg0,int arg1, int arg2) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showTime(arg0,arg1, arg2);

                }

            };
    private void showTime(TimePicker timePicker, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
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
