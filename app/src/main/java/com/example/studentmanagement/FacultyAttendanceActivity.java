package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FacultyAttendanceActivity extends AppCompatActivity {

    private Button addattendance;
    private Button viewattendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faculty_attendance);

        addattendance = (Button) findViewById(R.id.addattendance);
        viewattendance = (Button) findViewById(R.id.viewattendance);

        addattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fid=getIntent().getStringExtra("id");
                Intent i=(new Intent(FacultyAttendanceActivity.this,FacultyCoursesActivity.class));
                i.putExtra("facultyid",fid);
                startActivity(i);
            }
        });
        viewattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=getIntent().getStringExtra("id");
                Intent intent=(new Intent(FacultyAttendanceActivity.this,FacultyVCoursesActivity.class));
                intent.putExtra("facultyid",id);
                startActivity(intent);
            }
        });
    }
}
