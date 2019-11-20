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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseslistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Courses,CourseViewHolder> adapter;
    DatabaseReference cref;
    DatabaseReference rref;
    DatabaseReference srcref;
    RegisteredCourses rc;
    StudentwithRegisteredcourses src;
    long count=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_courseslist);

        rc =new RegisteredCourses();
        src = new StudentwithRegisteredcourses();
        srcref = FirebaseDatabase.getInstance().getReference().child("StudentwithRegisteredcourses");
        cref = FirebaseDatabase.getInstance().getReference().child("Courses");
        rref = FirebaseDatabase.getInstance().getReference().child("RegisteredCourses");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        showlist();
    }

    private void showlist(){
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Courses>().setQuery(cref,Courses.class).build();
        adapter = new FirebaseRecyclerAdapter<Courses, CourseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CourseViewHolder courseViewHolder, int i, @NonNull final Courses courses) {

                courseViewHolder.textcourse.setText(courses.getCourse());
                courseViewHolder.textfac.setText(courses.getFacname());
                courseViewHolder.textdate.setText(courses.getStartdate());
                courseViewHolder.textdur.setText(courses.getCoursedur());
                courseViewHolder.textstarttime.setText(courses.getStarttime());
                courseViewHolder.textendtime.setText(courses.getEndtime());
                courseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    String course=courses.getCourse();
                    String fac=courses.getFacname();
                    String date=courses.getStartdate();
                    String dur=courses.getCoursedur();
                    String starttime=courses.getStarttime();
                    String endtime=courses.getEndtime();
                    String uid = getIntent().getStringExtra("uid");
                    @Override
                    public void onClick(View v) {
                            if(rref.child(uid).child(course)!=null) {
                                rref.child(uid).child(course).addValueEventListener(new ValueEventListener() {
                                    String course = courses.getCourse();

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        RegisteredCourses reg = dataSnapshot.getValue(RegisteredCourses.class);
                                        if (dataSnapshot.exists()) {
                                            if (course.toUpperCase().equals(reg.getRegcourse().toUpperCase())) {
                                                if(count==1)
                                                    Toast.makeText(CourseslistActivity.this, "Course already registered!", Toast.LENGTH_SHORT).show();
                                                else
                                                    Toast.makeText(CourseslistActivity.this, "Course registered", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else {
                                            count = 0;
                                            Toast.makeText(CourseslistActivity.this, "not yet registered", Toast.LENGTH_SHORT).show();

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                if (count == 0) {
                                    rc.setRegcourse(course);
                                    rc.setFacname(fac);
                                    rc.setStartdate(date);
                                    rc.setCoursedur(dur);
                                    rc.setStarttime(starttime);
                                    rc.setEndtime(endtime);
                                    rref.child(uid).child(rc.getRegcourse()).setValue(rc);
                                    src.setId(uid);
                                    srcref.child(course).child(uid).setValue(src);
                                    //startActivity(new Intent(CourseslistActivity.this,HomeActivity.class));
                                    //count = 1;
                                }

                            }
                    }
                });
            }

            @NonNull
            @Override
            public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.course_info,parent, false);
                return new CourseViewHolder(view);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(CourseslistActivity.this,HomeActivity.class));
    }
}
