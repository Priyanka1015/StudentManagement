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
import java.util.Iterator;

public class RegisteredcoursesActivity extends AppCompatActivity {

    RecyclerView recycler_View;
    RecyclerView.LayoutManager layout_Manager;
    FirebaseRecyclerAdapter<RegisteredCourses,RegCourseViewHolder> adapter;
    DatabaseReference rref;
    long count=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registeredcourses);

        String usid=getIntent().getStringExtra("usid");
        assert usid!=null;
        rref= FirebaseDatabase.getInstance().getReference().child("RegisteredCourses").child(usid);
        recycler_View = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_View.setHasFixedSize(true);
        layout_Manager = new LinearLayoutManager(this);
        recycler_View.setLayoutManager(layout_Manager);



        showlist();
    }
    private void showlist(){
        FirebaseRecyclerOptions opt = new FirebaseRecyclerOptions.Builder<RegisteredCourses>().setQuery(rref,RegisteredCourses.class).build();
        adapter = new FirebaseRecyclerAdapter<RegisteredCourses, RegCourseViewHolder>(opt) {
            @Override
            protected void onBindViewHolder(@NonNull final RegCourseViewHolder regCourseViewHolder, int i, @NonNull RegisteredCourses registeredCourses) {

                regCourseViewHolder.textregcourse.setText(registeredCourses.getRegcourse());
                regCourseViewHolder.textfac.setText(registeredCourses.getFacname());
                regCourseViewHolder.textdate.setText(registeredCourses.getStartdate());
                regCourseViewHolder.textdur.setText(registeredCourses.getCoursedur());
                regCourseViewHolder.textstarttime.setText(registeredCourses.getStarttime());
                regCourseViewHolder.textendtime.setText(registeredCourses.getEndtime());
            }

            @NonNull
            @Override
            public RegCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.registeredcourse_info,parent,false);
                return new RegCourseViewHolder(view);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recycler_View.setAdapter(adapter);
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(RegisteredcoursesActivity.this,HomeActivity.class));
    }
}
