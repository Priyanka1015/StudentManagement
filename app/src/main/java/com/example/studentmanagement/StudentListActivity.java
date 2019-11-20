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

public class StudentListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Student,StudentViewHolder> adapter;
    DatabaseReference sref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_student_list);

        sref = FirebaseDatabase.getInstance().getReference().child("Student");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        showlist();
    }
    private void showlist(){
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Student>().setQuery(sref,Student.class).build();
        adapter = new FirebaseRecyclerAdapter<Student, StudentViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i, @NonNull final Student student) {

                studentViewHolder.txtstdid.setText(student.getStid());
                studentViewHolder.txtstdname.setText(student.getStname());
                studentViewHolder.del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id=student.getStid();
                        DatabaseReference sref = FirebaseDatabase.getInstance().getReference("Student").child(id);
                        DatabaseReference member = FirebaseDatabase.getInstance().getReference("Member").child(id);
                        sref.removeValue();
                        member.removeValue();

                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("RegisteredCourses").child(id);
                        databaseReference.removeValue();

                        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference().child("StudentAttendance");
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            String id=student.getStid();
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                                    dataSnapshot1.child(id).getRef().removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference().child("StudentMarks");
                        databaseReference2.addValueEventListener(new ValueEventListener() {
                            String id=student.getStid();
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                                    dataSnapshot1.child(id).getRef().removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        DatabaseReference databaseReference3= FirebaseDatabase.getInstance().getReference().child("StudentwithRegisteredcourses");
                        databaseReference3.addValueEventListener(new ValueEventListener() {
                            String id=student.getStid();
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                                    dataSnapshot1.child(id).getRef().removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });
            }

            @NonNull
            @Override
            public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.student_info,parent,false);
                return new StudentViewHolder(view);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(StudentListActivity.this,AdminHomeActivity.class));
    }
}
