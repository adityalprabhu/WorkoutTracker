package com.adityalprabhu.workouttracker.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adityalprabhu.workouttracker.R;
import com.adityalprabhu.workouttracker.db.DatabaseHandler;
import com.adityalprabhu.workouttracker.db.WorkoutDetailsTable;
import com.adityalprabhu.workouttracker.model.WorkoutDetails;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by prabhadi on 06/06/2017.
 */

public class WorkoutItemAdapter extends RecyclerView.Adapter<WorkoutItemAdapter.ViewHolder> {
    private ArrayList<WorkoutDetails> workoutList;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView editProfile;
        ImageView removeProfile;
        TextView workoutType;
        TextView workoutTime;
        TextView workoutDistance;
        TextView workoutCalories;
        TextView workoutPace;

        public ViewHolder(View v) {
            super(v);
/*            editProfile = (ImageView) v.findViewById(R.id.ic_edit_profile);
            removeProfile = (ImageView) v.findViewById(R.id.ic_remove_profile);
            profileName = (TextView) v.findViewById(R.id.profileName);*/
            workoutType = (TextView) v.findViewById(R.id.workout_type);
            workoutTime = (TextView) v.findViewById(R.id.workout_time);
            workoutDistance = (TextView) v.findViewById(R.id.workout_distance);
            workoutCalories = (TextView) v.findViewById(R.id.calories);
            workoutPace = (TextView) v.findViewById(R.id.workout_pace);
        }
    }

//    public void removeAt(int position) {
//        profileList.remove(position);
//        notifyItemRemoved(position);
//    }

    public WorkoutItemAdapter(Context context) {
        this.context = context;
        this.workoutList = new ArrayList<WorkoutDetails>();
    }

    @Override
    public WorkoutItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_item, parent, false);
        WorkoutItemAdapter.ViewHolder vh = new WorkoutItemAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(WorkoutItemAdapter.ViewHolder holder, final int position) {
        Log.d("Testtt", "result data for workoutList is " + workoutList);
        holder.workoutType.setText(workoutList.get(position).getWorkout());
        holder.workoutTime.setText(workoutList.get(position).getTime());
        holder.workoutDistance.setText(workoutList.get(position).getDistance());
        holder.workoutCalories.setText(workoutList.get(position).getCalories());
        if(TextUtils.isEmpty(workoutList.get(position).getPace())){
            holder.workoutPace.setText(workoutList.get(position).getInclination());
        }
        if(TextUtils.isEmpty(workoutList.get(position).getInclination())){
            holder.workoutPace.setText(workoutList.get(position).getPace());
        }


        // Remove item from recyler View

/*        holder.removeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProfileInDB(workoutList.get(position));
                workoutList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, workoutList.size());
            }
        });*/

        //Edit from recyler view

/*        holder.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/
    }


//    Delete Item from Recyler View

/*    private void deleteProfileInDB(WorkoutDetails profileDetails) {
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        WorkoutDetailsTable.deleteProfile(db, profileDetails);
        db.close();
    }*/

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public void updateData(ArrayList<WorkoutDetails> workoutList) {
        this.workoutList = workoutList;
        notifyDataSetChanged();
    }

}


