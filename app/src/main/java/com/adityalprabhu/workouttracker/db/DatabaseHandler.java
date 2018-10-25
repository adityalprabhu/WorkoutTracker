package com.adityalprabhu.workouttracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adityalprabhu.workouttracker.model.WorkoutDetails;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context mContext;
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "WorkoutDetails";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        WorkoutDetailsTable.onCreate(db);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        WorkoutDetailsTable.onUpgrade(db, oldVersion, newVersion);

    }
}