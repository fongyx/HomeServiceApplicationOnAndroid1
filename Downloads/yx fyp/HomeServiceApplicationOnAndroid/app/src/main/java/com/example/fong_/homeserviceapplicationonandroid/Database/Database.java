package com.example.fong_.homeserviceapplicationonandroid.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.fong_.homeserviceapplicationonandroid.Model.Services;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="HomeServiceDB.db";
    private static final int DB_VER=1;
    public Database(Context context) { super(context, DB_NAME, null, DB_VER); }

    public List<Services> getBooking()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"serviceID","customerName","contractorName","ServiceStatus","Category","ServiceAddress","ServiceDate","ServiceTime","ServiceDescription","FixedPrice", "BiddingPrice"};
        String sqlTable = "ServiceDetails";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect,null,null,null,null,null);

        final List<Services> result = new ArrayList<>();
        if (c.moveToFirst())
        {
            do {
                result.add(new Services(
                        c.getString(c.getColumnIndex("serviceID")),
                        c.getString(c.getColumnIndex("customerName")),
                        c.getString(c.getColumnIndex("contractorName")),
                        c.getString(c.getColumnIndex("ServiceStatus")),
                        c.getString(c.getColumnIndex("Category")),
                        c.getString(c.getColumnIndex("ServiceAddress")),
                        c.getString(c.getColumnIndex("ServiceDate")),
                        c.getString(c.getColumnIndex("ServiceTime")),
                        c.getString(c.getColumnIndex("ServiceDescription")),
                        c.getString(c.getColumnIndex("FixedPrice")),
                        c.getString(c.getColumnIndex("BiddingPrice"))

                ));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addServices (Services services)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO ServiceDetails(serviceID,customerName,contractorName,ServiceStatus,Category, ServiceAddress, ServiceDate, ServiceTime, ServiceDescription,FixedPrice,BiddingPrice) " +
                        "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');",
                services.getServiceID(),
                services.getCustomerName(),
                services.getContractorName(),
                services.getServiceStatus(),
                services.getCategory(),
                services.getServiceAddress(),
                services.getServiceDate(),
                services.getServiceTime(),
                services.getServiceDescription(),
                services.getFixedPrice(),
                services.getBiddingPrice());

        db.execSQL(query);
    }

    public void cancelServices()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM ServiceDetails");
        db.execSQL(query);
    }
}
