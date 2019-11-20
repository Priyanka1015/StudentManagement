package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class FacultyAddCoursesActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog timeSetListener;
    private EditText addcourse;
    private EditText facname;
    private EditText startdate;
    private EditText coursedur;
    private EditText startime;
    private EditText endtime;
    DatabaseReference cref;
    DatabaseReference fcref;
    Courses cr;
    FacultyCourses fc;
    long count=1;
    String ampm="";
    String tstartsata="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faculty_add_courses);

        cr = new Courses();
        fc= new FacultyCourses();
        fcref= FirebaseDatabase.getInstance().getReference().child("FacultyCourses");
        cref = FirebaseDatabase.getInstance().getReference().child("Courses");
        addcourse = (EditText) findViewById(R.id.textaddcor);
        facname = (EditText) findViewById(R.id.textaddfac);
        facname.onEditorAction(EditorInfo.IME_ACTION_DONE);
        startdate= findViewById(R.id.textadddate);

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        FacultyAddCoursesActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d("Date","onDateSet: mm-dd-yyyy: "+ month + "-" + dayOfMonth + "-" + year);
                String temp = month + "/" + dayOfMonth + "/" + year;
                startdate.setText(temp);
            }
        };

        coursedur=(EditText) findViewById(R.id.textadddur);
        startime = (EditText) findViewById(R.id.textaddstime);

        startime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog timedialog = new TimePickerDialog(FacultyAddCoursesActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay>=12){
                            ampm="PM";
                            hourOfDay=hourOfDay-12;
                        }
                        else ampm="AM";
                        startime.setText(String.format("%02d:%02d",hourOfDay,minute)+" "+ampm);
                    }
                },hour,minute,false);
                timedialog.show();
            }
        });


        endtime = (EditText) findViewById(R.id.textaddetime);

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog timedialog = new TimePickerDialog(FacultyAddCoursesActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String temp=startime.getText().toString();
                        String arr[]=temp.split(":");
                        if(hourOfDay>=12){
                            ampm = "PM";
                            hourOfDay=hourOfDay-12;
                        }
                        else ampm="AM";
                        if(Math.abs(Integer.parseInt(arr[0])- hourOfDay) >= 01)
                            endtime.setText(String.format("%02d:%02d",hourOfDay,minute)+" "+ampm);
                        else{
                            endtime.setError("Enter duration difference of atleast one hour");
                            endtime.requestFocus();
                        }
                    }
                },hour,minute,false);
                timedialog.show();
            }
        });

        Button addbtn = (Button) findViewById(R.id.textaddcorbutton);
        Button cancel = (Button) findViewById(R.id.cancel_action);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coursedata = addcourse.getText().toString().toUpperCase();
                String facdata = facname.getText().toString().toUpperCase();
                String datedata = startdate.getText().toString().toUpperCase();
                String durdata = coursedur.getText().toString().toUpperCase();
                String startdata = startime.getText().toString().toUpperCase();
                String enddata = endtime.getText().toString().toUpperCase();
                if(coursedata.isEmpty()) {
                    addcourse.setError("Enter course to add");
                    addcourse.requestFocus();
                } else if(facdata.isEmpty()) {
                    facname.setError("Enter faculty");
                    facname.requestFocus();
                } else if(datedata.isEmpty()){
                    startdate.setError("set the date");
                    startdate.requestFocus();
                } else if(durdata.isEmpty()){
                    coursedur.setError("Enter course Duration");
                    coursedur.requestFocus();
                } else if(startdata.isEmpty()) {
                    startime.setError("Enter timings");
                    startime.requestFocus();
                } else if(enddata.isEmpty()){
                    endtime.setError("Enter timings");
                    endtime.requestFocus();
                }
                else{
                    if(cref.child(addcourse.getText().toString().toUpperCase()) !=null) {
                        cref.child(addcourse.getText().toString().toUpperCase()).addValueEventListener(new ValueEventListener() {

                            String coursedata = addcourse.getText().toString().toUpperCase();

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Courses cr = dataSnapshot.getValue(Courses.class);
                                if (dataSnapshot.exists()) {
                                    if ((coursedata.toUpperCase()).equalsIgnoreCase(cr.getCourse())) {
                                        if(count==1)
                                            Toast.makeText(FacultyAddCoursesActivity.this, "Course already added!", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(FacultyAddCoursesActivity.this, "Course added!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    count = 0;
                                    Toast.makeText(FacultyAddCoursesActivity.this, "Course not found in the list", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    if (count == 0) {

                        String arr[]=datedata.split("/");
                        String arr1[]=new String[arr.length];
                        if(arr[0].length()==1)
                            arr1[0]='0'+arr[0];
                        else arr1[0]=arr[0];
                        if(arr[1].length()==1)
                            arr1[1]='0'+arr[1];
                        else arr1[1]=arr[1];
                        arr1[2]=arr[2];
                        for(int i=0;i<arr1.length-1;i++)
                            tstartsata+=arr1[i]+"/";
                        tstartsata+=arr1[2];
                        cr.setCourse(coursedata);
                        cr.setFacname(facdata);
                        cr.setStartdate(tstartsata);
                        cr.setCoursedur(durdata);
                        cr.setStarttime(startdata);
                        cr.setEndtime(enddata);
                        cref.child(cr.getCourse()).setValue(cr);
                        fc.setCourse(coursedata);
                        fcref.child(facdata).child(fc.getCourse()).setValue(fc);
                        startActivity(new Intent(FacultyAddCoursesActivity.this, FacultycourseslistActivity.class));
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addcourse.setText("");
                facname.setText("");
                startdate.setText("");
                coursedur.setText("");
                startime.setText("");
                endtime.setText("");

            }
        });
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(FacultyAddCoursesActivity.this,FacultyHomeActivity.class));
    }
}
