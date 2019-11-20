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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StudentMRegisteredcoursesActivity extends AppCompatActivity {

    RecyclerView recycler_View;
    RecyclerView.LayoutManager layout_Manager;
    FirebaseRecyclerAdapter<RegisteredCourses,StudentCourseViewHolder> adapter;
    DatabaseReference rref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_student_mregisteredcourses);

        String tid=getIntent().getStringExtra("tid");
        rref= FirebaseDatabase.getInstance().getReference().child("RegisteredCourses").child(tid);
        recycler_View = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_View.setHasFixedSize(true);
        layout_Manager = new LinearLayoutManager(this);
        recycler_View.setLayoutManager(layout_Manager);

        showlist();
    }
    private void showlist(){
        FirebaseRecyclerOptions opt = new FirebaseRecyclerOptions.Builder<RegisteredCourses>().setQuery(rref,RegisteredCourses.class).build();
        adapter = new FirebaseRecyclerAdapter<RegisteredCourses, StudentCourseViewHolder>(opt) {
            @Override
            protected void onBindViewHolder(@NonNull final StudentCourseViewHolder studentCourseViewHolder, int i, @NonNull final RegisteredCourses registeredCourses) {

                studentCourseViewHolder.course.setText(registeredCourses.getRegcourse());
                studentCourseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    String tid=getIntent().getStringExtra("tid");
                    @Override
                    public void onClick(View view) {
                        ArrayList<String> al=new ArrayList<String>();
                        al.add(registeredCourses.getRegcourse());
                        al.add(tid);
                        Intent intent=new Intent(StudentMRegisteredcoursesActivity.this,StudentMarksViewActivity.class);
                        intent.putStringArrayListExtra("temp",al);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public StudentCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.studentcourses_info,parent,false);
                return new StudentCourseViewHolder(view);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recycler_View.setAdapter(adapter);
    }
}
