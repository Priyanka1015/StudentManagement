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

public class FacultyVMarksCoursesStudentListActivity extends AppCompatActivity {

    RecyclerView recycler_View;
    RecyclerView.LayoutManager layout_Manager;
    FirebaseRecyclerAdapter<StudentwithRegisteredcourses,FacultyCourseStudentListViewHolder> adapter;
    DatabaseReference srcref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faculty_vmarks_courses_student_list);

        String cname=getIntent().getStringExtra("cname");
        assert cname != null;
        srcref = FirebaseDatabase.getInstance().getReference().child("StudentwithRegisteredcourses").child(cname);
        recycler_View = (RecyclerView) findViewById(R.id.recyclerview1);
        recycler_View.setHasFixedSize(true);
        layout_Manager = new LinearLayoutManager(FacultyVMarksCoursesStudentListActivity.this);
        recycler_View.setLayoutManager(layout_Manager);
        showlist();

    }
    private void showlist(){
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<StudentwithRegisteredcourses>().setQuery(srcref,StudentwithRegisteredcourses.class).build();
        adapter = new FirebaseRecyclerAdapter<StudentwithRegisteredcourses, FacultyCourseStudentListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FacultyCourseStudentListViewHolder facultyCourseStudentListViewHolder, int i, @NonNull final StudentwithRegisteredcourses studentwithRegisteredcourses) {

                facultyCourseStudentListViewHolder.id.setText(studentwithRegisteredcourses.getId());
                facultyCourseStudentListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    String cname=getIntent().getStringExtra("cname");
                    @Override
                    public void onClick(View view) {
                        ArrayList<String> al=new ArrayList<String>();
                        al.add(cname);
                        al.add(studentwithRegisteredcourses.getId());
                        Intent intent=new Intent(FacultyVMarksCoursesStudentListActivity.this,FacultyViewStudentMarksActivity.class);
                        intent.putStringArrayListExtra("temp",al);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public FacultyCourseStudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.facultycourses_studentlist_info,parent, false);
                return new FacultyCourseStudentListViewHolder(view);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recycler_View.setAdapter(adapter);

    }
}
