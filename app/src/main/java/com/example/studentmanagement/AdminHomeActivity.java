package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminHomeActivity extends AppCompatActivity {

    private Button list;
    private Button addlist;
    private Button faclist;
    private Button stdlist;
    private Button logout;
    private Button addfac;
    private Button addadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_home);

        list = (Button) findViewById(R.id.textcourses);
        addlist = (Button) findViewById(R.id.textaddcourses);
        faclist = (Button) findViewById(R.id.faclist);
        stdlist = (Button) findViewById(R.id.stdlist);
        logout = (Button) findViewById(R.id.logout);
        addfac = (Button) findViewById(R.id.addfaculty);
        addadmin = (Button) findViewById(R.id.addadmin);



        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this,AdmincourseslistActivity.class));
            }
        });
        addlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this,AddcoursesActivity.class));

            }
        });
        faclist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this,FacultyListActivity.class));
            }
        });
        stdlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this,StudentListActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this,LoginActivity.class));
            }
        });
        addfac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this,AddFacultyActivity.class));
            }
        });
        addadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this,AddAdminActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed(){
        Toast.makeText(AdminHomeActivity.this,"Click logout button to exit",Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(AdminHomeActivity.this,LoginActivity.class));
    }
}
