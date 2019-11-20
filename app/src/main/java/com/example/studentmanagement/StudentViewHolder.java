package com.example.studentmanagement;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentViewHolder extends RecyclerView.ViewHolder {
    public TextView txtstdid;
    public TextView txtstdname;
    public Button del;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);

        txtstdid = (TextView) itemView.findViewById(R.id.studentid);
        txtstdname = (TextView) itemView.findViewById(R.id.stdname);
        del = (Button) itemView.findViewById(R.id.delete);
    }
}
