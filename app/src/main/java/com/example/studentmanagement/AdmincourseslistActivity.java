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

public class AdmincourseslistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Courses,AdminCourseViewHolder> adapter;
    DatabaseReference cref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_courseslist);

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
                adminCourseViewHolder.del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String cour = courses.getCourse();
                        DatabaseReference cref=FirebaseDatabase.getInstance().getReference("Courses").child(cour);
                        cref.removeValue();
                        startActivity(new Intent(AdmincourseslistActivity.this,AdminHomeActivity.class));

                        DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("FacultyCourses");
                        databaseReference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                    dataSnapshot1.child(cour).getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://studentmanagement-master.firebaseio.com/RegisteredCourses");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                    dataSnapshot1.child(cour).getRef().removeValue();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReferenceFromUrl("https://studentmanagement-master.firebaseio.com/StudentwithRegisteredcourses");
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.child(cour).getRef().removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        DatabaseReference databaseReference3= FirebaseDatabase.getInstance().getReference().child("StudentAttendance").child(cour);
                        databaseReference3.removeValue();

                        DatabaseReference databaseReference4= FirebaseDatabase.getInstance().getReference().child("StudentMarks").child(cour);
                        databaseReference4.removeValue();

                    }

                });


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
        startActivity(new Intent(AdmincourseslistActivity.this,AdminHomeActivity.class));
    }
}
