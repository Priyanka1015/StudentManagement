package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FacultyAddedStudentAttendanceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner sp;
    ArrayAdapter adp;
    private RadioButton present;
    private RadioButton absent;
    private Button submit;
    private TextView pview;
    private SeekBar pseekbar;
    private ProgressBar pbar;
    private TextView aview;
    private SeekBar aseekbar;
    private ProgressBar abar;
    Courses cr;
    DatabaseReference cref;
    StudentAttendance sa;
    DatabaseReference ref;
    String spn="";
    String course="";
    String id="";
    String dur="";
    int months=0;
    int count=1;
    int days=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faculty_added_student_attendance);

        ArrayList<String> al;
        al=getIntent().getStringArrayListExtra("temp");
        assert al != null;
        course=al.get(0);
        id=al.get(1);

        sp = (Spinner) findViewById(R.id.monthspinner);
        present=(RadioButton) findViewById(R.id.presentbutton);
        absent= (RadioButton) findViewById(R.id.absentbutton);
        submit= (Button) findViewById(R.id.submitbtn);
        pview= (TextView) findViewById(R.id.presentpercent);
        pbar= (ProgressBar) findViewById(R.id.presentprogressbar);
        pseekbar= (SeekBar) findViewById(R.id.presentseekbar);
        aview= (TextView) findViewById(R.id.absentpercent);
        abar= (ProgressBar) findViewById(R.id.absentprogressbar);
        aseekbar= (SeekBar) findViewById(R.id.absentseekbar);

        sa=new StudentAttendance();
        ref=FirebaseDatabase.getInstance().getReference().child("StudentAttendance");
        cr=new Courses();
        cref= FirebaseDatabase.getInstance().getReference().child("Courses");
        if(cref.child(course)!=null){
            cref.child(course).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Courses cr=dataSnapshot.getValue(Courses.class);
                    if(dataSnapshot.exists())
                        dur=cr.getCoursedur();
                    String arr[]=dur.split(" ");
                    months=Integer.parseInt(arr[0])/4;
                    days=20*months;
                    String xarr[]=new String[days];
                    for(int i=0;i<days;i++) {
                        xarr[i] = String.valueOf(count);
                        count++;
                    }
                    adp=new ArrayAdapter(getApplicationContext(),R.layout.data_layout,R.id.dataview,xarr);
                    sp.setAdapter(adp);
                    sp.setOnItemSelectedListener(FacultyAddedStudentAttendanceActivity.this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }



        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        pbar.setProgress(i);
                        pview.setText(i+" %");
                        absent.setClickable(false);
                        aseekbar.setEnabled(false);
                        aseekbar.setProgress(100-i);
                        abar.setProgress(100-i);
                        aview.setText((100-i)+" %");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }
        });
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        abar.setProgress(i);
                        aview.setText(i+" %");
                        present.setClickable(false);
                        pseekbar.setEnabled(false);
                        pseekbar.setProgress(100-i);
                        pbar.setProgress(100-i);
                        pview.setText((100-i)+" %");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parr[] = pview.getText().toString().split(" ");
                String aarr[] = aview.getText().toString().split(" ");
                if (Integer.parseInt(parr[0]) + Integer.parseInt(aarr[0]) == 100) {
                    sa = new StudentAttendance();
                    ref = FirebaseDatabase.getInstance().getReference().child("StudentAttendance");
                    sa.setPresent(pview.getText().toString());
                    sa.setAbsent(aview.getText().toString());
                    ref.child(course).child(id).child(spn).setValue(sa);
                    Toast.makeText(FacultyAddedStudentAttendanceActivity.this, "submitted..!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(FacultyAddedStudentAttendanceActivity.this, "Total attendance should sum to 100!!!", Toast.LENGTH_SHORT).show();
                present.setChecked(false);
                absent.setChecked(false);
                pseekbar.setEnabled(true);
                pseekbar.setProgress(0);
                pbar.setEnabled(true);
                pbar.setProgress(0);
                pview.setText("");
                aseekbar.setEnabled(true);
                aseekbar.setProgress(0);
                abar.setEnabled(true);
                abar.setProgress(0);
                aview.setText("");
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
