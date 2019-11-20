package com.example.studentmanagement;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentCourseViewHolder extends RecyclerView.ViewHolder {
    public TextView course;

    public StudentCourseViewHolder(@NonNull View itemView) {
        super(itemView);
        course = (TextView) itemView.findViewById(R.id.Course);
    }
}
