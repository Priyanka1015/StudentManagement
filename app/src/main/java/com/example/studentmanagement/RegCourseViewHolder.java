package com.example.studentmanagement;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RegCourseViewHolder extends RecyclerView.ViewHolder {
    public TextView textregcourse;
    public TextView textfac;
    public TextView textdate;
    public TextView textdur;
    public TextView textstarttime;
    public TextView textendtime;


    public RegCourseViewHolder(@NonNull View itemView) {
        super(itemView);
        textregcourse = (TextView) itemView.findViewById(R.id._regcourse);
        textfac = (TextView) itemView.findViewById(R.id.textview_faculty);
        textdate = (TextView) itemView.findViewById(R.id.textview_sdate);
        textdur = (TextView) itemView.findViewById(R.id.textview_duration);
        textstarttime = (TextView) itemView.findViewById(R.id.textview_stime);
        textendtime = (TextView) itemView.findViewById(R.id.textview_etime);
    }
}
