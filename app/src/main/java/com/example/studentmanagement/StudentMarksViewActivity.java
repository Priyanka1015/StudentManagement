package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentMarksViewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner sp;
    private Button view;
    private TextView marks;
    StudentMarks sm;
    DatabaseReference smref;
    ArrayAdapter adp;
    int count=0;
    String duration="";
    String spn="";
    Courses cr;
    DatabaseReference cref;
    String course="";
    String id="";
    String vmarks="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_student_marks_view);

        ArrayList<String> al;
        al=getIntent().getStringArrayListExtra("temp");
        assert al != null;
        course=al.get(0);
        id=al.get(1);

        sp = (Spinner) findViewById(R.id.monthspinner);
        view= (Button) findViewById(R.id.view);
        marks= (TextView) findViewById(R.id.marks);
        sm=new StudentMarks();
        smref= FirebaseDatabase.getInstance().getReference().child("StudentMarks").child(course).child(id);

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
                        sp.setOnItemSelectedListener(StudentMarksViewActivity.this);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(smref.child(spn)!=null){
                    smref.child(spn).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            StudentMarks sm=dataSnapshot.getValue(StudentMarks.class);
                            if(dataSnapshot.exists()) {
                                vmarks = sm.getMarks();
                                marks.setText(vmarks);
                            }
                            else marks.setText("");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
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
