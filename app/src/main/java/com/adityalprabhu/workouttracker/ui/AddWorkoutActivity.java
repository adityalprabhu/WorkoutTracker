package com.adityalprabhu.workouttracker.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adityalprabhu.workouttracker.R;
import com.adityalprabhu.workouttracker.db.DatabaseHandler;
import com.adityalprabhu.workouttracker.db.WorkoutDetailsTable;
import com.adityalprabhu.workouttracker.model.WorkoutDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.R.attr.startYear;

public class AddWorkoutActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public Button addWorkout, backButton, datePickerButton;
    private RecyclerView workoutItems;
    private WorkoutItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<WorkoutDetails> workoutList;
    private WorkoutDetails workoutItem;
    LinearLayout listLayout, noProfileLayout;
    public String todayDate;
    public Spinner workoutType;
    public EditText workoutTime;
    public EditText workoutDistance;
    public EditText workoutCalories;
    public EditText workoutPace;
    public EditText workoutIncline;
    private TextInputLayout incline;
    private TextInputLayout pace;
    public int seq = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(" Add Workout - " + getTodayDate());

        workoutList = new ArrayList<WorkoutDetails>();


        workoutItems = (RecyclerView) findViewById(R.id.today_workout_list);
        workoutItems.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        workoutItems.setLayoutManager(mLayoutManager);

        mAdapter = new WorkoutItemAdapter(getApplicationContext());
        workoutItems.setAdapter(mAdapter);

        addWorkout = (Button) findViewById(R.id.add_workout);
        backButton = (Button) findViewById(R.id.btn_back);
        datePickerButton = (Button) findViewById(R.id.datePicker);
        addWorkout.setOnClickListener(this);
        backButton.setOnClickListener(this);
        datePickerButton.setOnClickListener(this);

        workoutType = (Spinner) findViewById(R.id.workout_type);
        workoutTime = (EditText) findViewById(R.id.workout_time);
        workoutDistance = (EditText) findViewById(R.id.workout_distance);
        workoutCalories = (EditText) findViewById(R.id.calories);
        workoutPace = (EditText) findViewById(R.id.workout_pace);
        workoutIncline = (EditText) findViewById(R.id.workout_inclination);
        incline = (TextInputLayout) findViewById(R.id.incline_input_layout);
        pace = (TextInputLayout) findViewById(R.id.pace_input_layout);

        workoutPace.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    addItem();
                    workoutTime.setText("");
                    workoutDistance.setText("");
                    workoutCalories.setText("");
                    workoutPace.setText("");
                    workoutIncline.setText("");
                }
                return false;
            }
        });

        workoutType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedWorkoutType = workoutType.getSelectedItem().toString();
                if(selectedWorkoutType.equals("Hill Climb")){
                    pace.setVisibility(View.GONE);
                    incline.setVisibility(View.VISIBLE);
                    workoutPace.setText("");
                    workoutIncline.setText("");
                }else{
                    pace.setVisibility(View.VISIBLE);
                    incline.setVisibility(View.GONE);
                    workoutPace.setText("");
                    workoutIncline.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //noWorkoutLayout = (LinearLayout) findViewById(R.id.no_profile_layout);

        getTodayWorkoutFromDB();

    }

    public String getTodayDate() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }

    private void getTodayWorkoutFromDB() {
        SQLiteDatabase db = null;
        DatabaseHandler mDBHandler = new DatabaseHandler(this);

        todayDate = getTodayDate();

        String selectQuery = "SELECT * FROM " + WorkoutDetailsTable.WORKOUT_DETAILS_TABLE + " WHERE " + WorkoutDetailsTable.Column.DATE.getmColumnName() + "= '" + todayDate + "'";

        db = mDBHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
//            noProfileLayout.setVisibility(View.VISIBLE);
            //           listLayout.setVisibility(View.GONE);
            return;
        } else {
            //noProfileLayout.setVisibility(View.GONE);
            //listLayout.setVisibility(View.VISIBLE);
            WorkoutDetails tempWorkoutDetails;
            while (!cursor.isAfterLast()) {
                tempWorkoutDetails = new WorkoutDetails();
                tempWorkoutDetails.setId(cursor.getInt(cursor.getColumnIndex(WorkoutDetailsTable.Column.ID.getmColumnName())));
                tempWorkoutDetails.setWorkout(cursor.getString(cursor.getColumnIndex(WorkoutDetailsTable.Column.WORKOUT.getmColumnName())));
                tempWorkoutDetails.setTime(cursor.getString(cursor.getColumnIndex(WorkoutDetailsTable.Column.TIME.getmColumnName())));
                tempWorkoutDetails.setDistance(cursor.getString(cursor.getColumnIndex(WorkoutDetailsTable.Column.DISTANCE.getmColumnName())));
                tempWorkoutDetails.setCalories(cursor.getString(cursor.getColumnIndex(WorkoutDetailsTable.Column.CALORIES.getmColumnName())));
                if(TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(WorkoutDetailsTable.Column.PACE.getmColumnName()))) && !TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(WorkoutDetailsTable.Column.INCLINATION.getmColumnName())))){
                    tempWorkoutDetails.setInclination(cursor.getString(cursor.getColumnIndex(WorkoutDetailsTable.Column.INCLINATION.getmColumnName())));
                }
                if(!TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(WorkoutDetailsTable.Column.PACE.getmColumnName()))) && TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(WorkoutDetailsTable.Column.INCLINATION.getmColumnName())))){
                    tempWorkoutDetails.setPace(cursor.getString(cursor.getColumnIndex(WorkoutDetailsTable.Column.PACE.getmColumnName())));
                }

                workoutList.add(tempWorkoutDetails);
                cursor.moveToNext();
            }

            if (null != cursor) cursor.close();
            if (null != db) db.close();

            mAdapter.updateData(workoutList);
        }
    }

    public void addItem() {
        int seq1 = seq + 1;
        workoutItem = new WorkoutDetails();
        workoutItem.setId(seq1);
        workoutItem.setWorkout(workoutType.getSelectedItem().toString());
        workoutItem.setTime(workoutTime.getText().toString() + " min ");
        workoutItem.setDistance(workoutDistance.getText().toString() + " km ");
        workoutItem.setCalories(workoutCalories.getText().toString() + " cal ");
        if(TextUtils.isEmpty(workoutIncline.getText().toString()) && !TextUtils.isEmpty(workoutPace.getText().toString())){
            workoutItem.setPace(workoutPace.getText().toString() + " pace ");
        }

        Log.d("Testtt", "result data for workoutList isZZZZZZZZZZZZ " + workoutPace.getText().toString());

        if(TextUtils.isEmpty(workoutPace.getText().toString()) && !TextUtils.isEmpty(workoutIncline.getText().toString())){
            workoutItem.setPace(workoutIncline.getText().toString() + " inc ");
        }
        workoutItem.setDate(todayDate);

        workoutList.add(workoutItem);
        mAdapter.updateData(workoutList);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        SQLiteDatabase db = databaseHandler.getWritableDatabase();

/*
            Log.d("Testtttt", "country code is "+ profileDetails.getCountryCode().getCode() +
                    "country name is "+ profileDetails.getCountryCode().getCountryName());*/
        WorkoutDetailsTable.createWorkoutList(db, workoutItem);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_workout) {
            addItem();
        } else if (view.getId() == R.id.btn_back) {
        }else if(view.getId() ==R.id.datePicker){
            Calendar cal = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddWorkoutActivity.this, AddWorkoutActivity.this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) +1, cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        todayDate = year + "-" + (month+1) + "-" + day;
        Calendar formatedDate1 = Calendar.getInstance();
        formatedDate1.set(Calendar.YEAR, year);
        formatedDate1.set(Calendar.MONTH, month);
        formatedDate1.set(Calendar.DAY_OF_MONTH, day);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(formatedDate1.getTime());

        Log.d("Testtt", "DATE IZIZIZIZIZIZI " + formattedDate);
        setTitle(" Add Workout - " + formattedDate);
        datePickerButton.setText(formattedDate);

    }
}
