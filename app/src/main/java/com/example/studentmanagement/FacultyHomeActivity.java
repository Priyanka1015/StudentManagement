package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FacultyHomeActivity extends AppCompatActivity {

    private Button list;
    private Button addcourses;
    private Button attendance;
    private Button marks;
    private Button stdlist;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faculty_home);

        list=(Button) findViewById(R.id.textcourses);
        addcourses=(Button) findViewById(R.id.textaddcourses);
        attendance=(Button) findViewById(R.id.attendance);
        marks=(Button) findViewById(R.id.marks);
        stdlist=(Button) findViewById(R.id.stdlist);
        logout = (Button) findViewById(R.id.logout);

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyHomeActivity.this,FacultycourseslistActivity.class));
            }
        });
        addcourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyHomeActivity.this,FacultyAddCoursesActivity.class));
            }
        });
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid=getIntent().getStringExtra("facid");
                Intent intent=(new Intent(FacultyHomeActivity.this,FacultyAttendanceActivity.class));
                intent.putExtra("id",uid);
                startActivity(intent);
            }
        });
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid=getIntent().getStringExtra("facid");
                Intent i=(new Intent(FacultyHomeActivity.this,FacultyMarksActivity.class));
                i.putExtra("id",uid);
                startActivity(i);
            }
        });
        stdlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyHomeActivity.this,FacultyStudentListActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyHomeActivity.this,LoginActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed(){
        Toast.makeText(FacultyHomeActivity.this,"Click logout button to exit",Toast.LENGTH_SHORT).show();
    }

}
