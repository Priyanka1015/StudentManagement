package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FacultyAddedStudentMarksActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner sp;
    private EditText marks;
    private Button submit;
    StudentMarks sm;
    DatabaseReference smref;
    ArrayAdapter adp;
    int count=0;
    String duration="";
    String course="";
    String id="";
    String spn="";
    Courses cr;
    DatabaseReference cref;


    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faculty_added_student_marks);

        sp = (Spinner) findViewById(R.id.monthspinner);
        marks= (EditText) findViewById(R.id.marks);
        submit= (Button) findViewById(R.id.markssubmit);
        sm=new StudentMarks();
        smref= FirebaseDatabase.getInstance().getReference().child("StudentMarks");

        ArrayList<String> al;
        al = getIntent().getStringArrayListExtra("temp");
        assert al != null;
        course = al.get(0);
        id = al.get(1);


        cr = new Courses();
        cref = FirebaseDatabase.getInstance().getReference().child("Courses");
        if (cref.child(course) != null) {
            cref.child(course).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Courses cr = dataSnapshot.getValue(Courses.class);
                    if (dataSnapshot.exists()) {
                        duration = cr.getCoursedur();
                        String arr[] = duration.split(" ");
                        count = Integer.parseInt(arr[0])/4;
                        int c=1;
                        String[] xarr = new String[count];
                        for(int i=0;i<count;i++) {
                            if(c<=count) {
                                xarr[i] = "Test " + c;
                                c++;
                            }
                        }

                        adp=new ArrayAdapter(getApplicationContext(),R.layout.data_layout,R.id.dataview,xarr);
                        sp.setAdapter(adp);
                        sp.setOnItemSelectedListener(FacultyAddedStudentMarksActivity.this);


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        submit.setOnClickListener(new View.OnClickListener() {
            String rcourse=course;
            String rid=id;
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(marks.getText().toString()) <= 100) {
                    sm = new StudentMarks();
                    smref = FirebaseDatabase.getInstance().getReference().child("StudentMarks");
                    sm.setMarks(marks.getText().toString());
                    smref.child(rcourse).child(rid).child(spn).setValue(sm);
                    Toast.makeText(FacultyAddedStudentMarksActivity.this, "submitted..!", Toast.LENGTH_SHORT).show();
                    marks.setText("");
                }
                else
                    Toast.makeText(FacultyAddedStudentMarksActivity.this, "Marks should be between 0 to 100", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        spn = text;
        Toast.makeText(adapterView.getContext(), text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
