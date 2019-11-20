package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {

    private Button list;
    private Button aview;
    private Button mview;
    private Button reglist;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);




        list = (Button) findViewById(R.id.textcourses);
        aview= (Button) findViewById(R.id.viewattendance);
        mview= (Button) findViewById(R.id.viewmarks);
        reglist = (Button) findViewById(R.id.textregcourses);
        logout = (Button) findViewById(R.id.logout);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = getIntent().getStringExtra("userid");
                Intent in =(new Intent(HomeActivity.this,CourseslistActivity.class));
                in.putExtra("uid",uid);
                startActivity(in);
            }
        });
        aview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sid = getIntent().getStringExtra("userid");
                Intent in =(new Intent(HomeActivity.this,StudentARegisteredcoursesActivity.class));
                in.putExtra("sid",sid);
                startActivity(in);
            }
        });
        mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tid = getIntent().getStringExtra("userid");
                Intent in =(new Intent(HomeActivity.this,StudentMRegisteredcoursesActivity.class));
                in.putExtra("tid",tid);
                startActivity(in);
            }
        });
        reglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usid = getIntent().getStringExtra("userid");
                Intent in =(new Intent(HomeActivity.this,RegisteredcoursesActivity.class));
                in.putExtra("usid",usid);
                startActivity(in);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }
        });


    }
    @Override
    public void onBackPressed(){
        Toast.makeText(HomeActivity.this,"Click logout button to exit",Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(HomeActivity.this,LoginActivity.class));
    }
}
