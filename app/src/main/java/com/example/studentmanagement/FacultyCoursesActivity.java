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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FacultyCoursesActivity extends AppCompatActivity {


    RecyclerView recycler_View;
    RecyclerView.LayoutManager layout_Manager;
    FirebaseRecyclerAdapter<FacultyCourses,FacultyCourseViewHolder> adapter;
    DatabaseReference fcref;
    DatabaseReference fref;
    Faculty fac;
    String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faculty_courses);

        String faccid=getIntent().getStringExtra("facultyid");
        fac= new Faculty();
        assert faccid != null;
        fref= FirebaseDatabase.getInstance().getReference().child("Faculty");
        if(fref.child(faccid)!=null){
            fref.child(faccid).addValueEventListener(new ValueEventListener() {
                String faccid=getIntent().getStringExtra("facultyid");
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Faculty fac= dataSnapshot.getValue(Faculty.class);
                    if(dataSnapshot.exists()) {
                        if(faccid.equalsIgnoreCase(fac.getFacid())) {
                            name = fac.getFacname().toUpperCase();
                            fcref = FirebaseDatabase.getInstance().getReference().child("FacultyCourses").child(name);

                            recycler_View = (RecyclerView) findViewById(R.id.recyclerview1);
                            recycler_View.setHasFixedSize(true);
                            layout_Manager = new LinearLayoutManager(FacultyCoursesActivity.this);
                            recycler_View.setLayoutManager(layout_Manager);

                            FirebaseRecyclerOptions opt = new FirebaseRecyclerOptions.Builder<FacultyCourses>().setQuery(fcref, FacultyCourses.class).build();
                            adapter = new FirebaseRecyclerAdapter<FacultyCourses, FacultyCourseViewHolder>(opt) {
                                @Override
                                protected void onBindViewHolder(@NonNull final FacultyCourseViewHolder facultyCourseViewHolder, int i, @NonNull final FacultyCourses facultyCourses) {

                                    facultyCourseViewHolder.course.setText(facultyCourses.getCourse());
                                    facultyCourseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String course=facultyCourses.getCourse();
                                            Intent in=new Intent(FacultyCoursesActivity.this,FacultyCoursesStudentListActivity.class);
                                            in.putExtra("cname",course);
                                            startActivity(in);
                                        }
                                    });

                                }

                                @NonNull
                                @Override
                                public FacultyCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.facultycourses_info, parent, false);
                                    return new FacultyCourseViewHolder(view);
                                }
                            };
                            adapter.startListening();
                            adapter.notifyDataSetChanged();
                            recycler_View.setAdapter(adapter);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
