package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FacultyViewStudentAttendanceActivity extends AppCompatActivity {

    private RadioButton present;
    private RadioButton absent;
    private TextView pview;
    private ProgressBar pbar;
    private TextView aview;
    private ProgressBar abar;
    StudentAttendance sa;
    DatabaseReference ref;
    int count=0;
    double pvalue=0,avalue=0;
    String course="";
    String id="";
    String pdata="";
    String adata="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faculty_view_student_attendance);

        ArrayList<String> al;
        al=getIntent().getStringArrayListExtra("temp");
        assert al != null;
        course=al.get(0);
        id=al.get(1);

        sa=new StudentAttendance();
        ref= FirebaseDatabase.getInstance().getReference().child("StudentAttendance").child(course);

        present=(RadioButton) findViewById(R.id.presentbutton);
        absent= (RadioButton) findViewById(R.id.absentbutton);
        pview= (TextView) findViewById(R.id.presentpercent);
        aview= (TextView) findViewById(R.id.absentpercent);
        pbar= (ProgressBar) findViewById(R.id.pvprogressbar);
        abar= (ProgressBar) findViewById(R.id.avprogressbar);

        if(ref.child(id)!=null){
            ref.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        count = (int) dataSnapshot.getChildrenCount();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            StudentAttendance sa = dataSnapshot1.getValue(StudentAttendance.class);
                            pdata = sa.getPresent();
                            String parr[] = pdata.split(" ");
                            pvalue+= Double.parseDouble(parr[0]);
                            adata = sa.getAbsent();
                            String aarr[] = adata.split(" ");
                            avalue+= Double.parseDouble(aarr[0]);
                            //Toast.makeText(FacultyViewStudentAttendanceActivity.this,String.format("%d",count),Toast.LENGTH_SHORT).show();
                        }

                        pvalue = pvalue / count;
                        avalue = avalue / count;
                        present.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                pview.setText(pvalue + " %");
                                pbar.setProgress((int)pvalue);
                            }
                        });
                        absent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                aview.setText(avalue + " %");
                                abar.setProgress((int)avalue);
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}
