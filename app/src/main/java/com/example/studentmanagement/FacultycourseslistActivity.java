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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FacultycourseslistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Courses,AdminCourseViewHolder> adapter;
    DatabaseReference cref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_facultycourseslist);

        cref = FirebaseDatabase.getInstance().getReference().child("Courses");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        showlist();
    }
    private void showlist(){
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Courses>().setQuery(cref,Courses.class).build();
        adapter = new FirebaseRecyclerAdapter<Courses, AdminCourseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminCourseViewHolder adminCourseViewHolder, int i, @NonNull final Courses courses) {

                adminCourseViewHolder.textcourse.setText(courses.getCourse());
                adminCourseViewHolder.textfac.setText(courses.getFacname());
                adminCourseViewHolder.textdate.setText(courses.getStartdate());
                adminCourseViewHolder.textdur.setText(courses.getCoursedur());
                adminCourseViewHolder.textstarttime.setText(courses.getStarttime());
                adminCourseViewHolder.textendtime.setText(courses.getEndtime());

            }

            @NonNull
            @Override
            public AdminCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.course_info,parent, false);
                return new AdminCourseViewHolder(view);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(FacultycourseslistActivity.this,FacultyHomeActivity.class));
    }
}
