package com.example.studentmanagement;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminCourseViewHolder extends RecyclerView.ViewHolder {
    public TextView textcourse;
    public TextView textfac;
    public TextView textdate;
    public TextView textdur;
    public TextView textstarttime;
    public TextView textendtime;
    public Button del;

    public AdminCourseViewHolder(@NonNull View itemView) {
        super(itemView);

        textcourse = (TextView) itemView.findViewById(R.id.textview_course);
        textfac = (TextView) itemView.findViewById(R.id.textview_faculty);
        textdate = (TextView) itemView.findViewById(R.id.textview_sdate);
        textdur = (TextView) itemView.findViewById(R.id.textview_duration);
        textstarttime = (TextView) itemView.findViewById(R.id.textview_stime);
        textendtime = (TextView) itemView.findViewById(R.id.textview_etime);
        del = (Button) itemView.findViewById(R.id.delete);

    }
}
