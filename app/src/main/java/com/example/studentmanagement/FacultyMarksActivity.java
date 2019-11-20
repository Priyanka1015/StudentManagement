package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FacultyMarksActivity extends AppCompatActivity {
    private Button addmarks;
    private Button viewmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faculty_marks);

        addmarks=(Button) findViewById(R.id.addmarks);
        viewmarks=(Button) findViewById(R.id.viewmarks);

        addmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fid=getIntent().getStringExtra("id");
                Intent i=(new Intent(FacultyMarksActivity.this,FacultyMarksCoursesActivity.class));
                i.putExtra("facultyid",fid);
                startActivity(i);
            }
        });
        viewmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=getIntent().getStringExtra("id");
                Intent intent=(new Intent(FacultyMarksActivity.this,FacultyVMarksCoursesActivity.class));
                intent.putExtra("facultyid",id);
                startActivity(intent);
            }
        });
    }
}
