package com.example.studentmanagement;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FacultyCourseStudentListViewHolder extends RecyclerView.ViewHolder {

    public TextView id;
    public FacultyCourseStudentListViewHolder(@NonNull View itemView) {
        super(itemView);
        id=(TextView) itemView.findViewById(R.id.student);
    }
}
