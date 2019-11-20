package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FacultyListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Faculty,FacultyViewHolder> adapter;
    DatabaseReference fref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faculty_list);

        fref = FirebaseDatabase.getInstance().getReference().child("Faculty");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        showlist();
    }

    private void showlist(){
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Faculty>().setQuery(fref,Faculty.class).build();
        adapter = new FirebaseRecyclerAdapter<Faculty, FacultyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FacultyViewHolder facultyViewHolder, int i, @NonNull final Faculty faculty) {

                facultyViewHolder.txtfacid.setText(faculty.getFacid());
                facultyViewHolder.txtfacname.setText(faculty.getFacname());
                facultyViewHolder.del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id=faculty.getFacid();
                        DatabaseReference fref = FirebaseDatabase.getInstance().getReference("Faculty").child(id);
                        DatabaseReference member = FirebaseDatabase.getInstance().getReference("Member").child(id);

                        fref.removeValue();
                        member.removeValue();
                    }
                });
            }

            @NonNull
            @Override
            public FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.faculty_info,parent,false);
                return new FacultyViewHolder(view);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(FacultyListActivity.this,AdminHomeActivity.class));
    }
}
