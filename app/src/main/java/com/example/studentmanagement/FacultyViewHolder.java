package com.example.studentmanagement;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FacultyViewHolder extends RecyclerView.ViewHolder {
    public TextView txtfacname;
    public TextView txtfacid;
    public Button del;

    public FacultyViewHolder(@NonNull View itemView) {
        super(itemView);
        txtfacname = (TextView) itemView.findViewById(R.id.fac_name);
        txtfacid = (TextView) itemView.findViewById(R.id.fac_id);
        del = (Button) itemView.findViewById(R.id.delete);
    }
}
