package com.adityalprabhu.workouttracker.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.adityalprabhu.workouttracker.model.WorkoutDetails;
//import com.google.gson.Gson;

public class WorkoutDetailsTable {

    public enum Column {

        ID("id"),
        WORKOUT("workout"),
        TIME("time"),
        CALORIES("calories"),
        DISTANCE("distance"),
        PACE("pace"),
        INCLINATION("inclination"),
        DATE("date");

        Column(String mColumnName) {
            this.mColumnName = mColumnName;
        }

        String mColumnName;

        public String getmColumnName() {
            return mColumnName;
        }
    }

    public static final String WORKOUT_DETAILS_TABLE = "workoutDetailsTable";

    private static final String DATABASE_CREATE = "CREATE TABLE if not exists "
            + WORKOUT_DETAILS_TABLE + " ("
            + Column.ID.getmColumnName() + " INTEGER PRIMARY KEY , "
            + Column.WORKOUT.getmColumnName() + " TEXT , "
            + Column.TIME.getmColumnName() + " TEXT , "
            + Column.CALORIES.getmColumnName() + " TEXT , "
            + Column.DISTANCE.getmColumnName() + " TEXT , "
            + Column.PACE.getmColumnName() + " TEXT , "
            + Column.INCLINATION.getmColumnName() + " TEXT,  "
            + Column.DATE.getmColumnName() + " TEXT  "
            + " )";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion,
                                 int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_DETAILS_TABLE);
        onCreate(db);
    }

    public static long createWorkoutList(SQLiteDatabase db, WorkoutDetails workoutDetails) {
        ContentValues values = new ContentValues();
        values.put(Column.WORKOUT.getmColumnName(), workoutDetails.getWorkout());
        values.put(Column.TIME.getmColumnName(), workoutDetails.getTime());
        values.put(Column.CALORIES.getmColumnName(), workoutDetails.getCalories());
        values.put(Column.DISTANCE.getmColumnName(), workoutDetails.getDistance());
        values.put(Column.PACE.getmColumnName(), workoutDetails.getPace());
        values.put(Column.INCLINATION.getmColumnName(), workoutDetails.getInclination());
        values.put(Column.DATE.getmColumnName(), workoutDetails.getDate());

        return db.insert(WORKOUT_DETAILS_TABLE, null, values);
    }

    public static int updateWorkoutList(SQLiteDatabase db, WorkoutDetails workoutDetails) {

        ContentValues values = new ContentValues();
        values.put(Column.WORKOUT.getmColumnName(), workoutDetails.getWorkout());
        values.put(Column.TIME.getmColumnName(), workoutDetails.getTime());
        values.put(Column.CALORIES.getmColumnName(), workoutDetails.getCalories());
        values.put(Column.DISTANCE.getmColumnName(), workoutDetails.getDistance());
        values.put(Column.PACE.getmColumnName(), workoutDetails.getPace());
        values.put(Column.INCLINATION.getmColumnName(), workoutDetails.getInclination());
        values.put(Column.DATE.getmColumnName(), workoutDetails.getDate());

        return db.update(WORKOUT_DETAILS_TABLE, values, Column.ID.getmColumnName() + " = ?",
                new String[]{String.valueOf(workoutDetails.getId())});
    }

    public static int deleteProfile(SQLiteDatabase db, WorkoutDetails workoutDetails){

        return db.delete(WORKOUT_DETAILS_TABLE, Column.ID.getmColumnName() + " = ?",
                new String[]{String.valueOf(workoutDetails.getId())});
    }

}