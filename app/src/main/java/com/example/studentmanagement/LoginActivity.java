package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText email;
    private EditText userid;
    private EditText password;
    DatabaseReference ref;
    DatabaseReference aref;
    DatabaseReference fref;
    DatabaseReference adref;
    DatabaseReference facref;
    Member member;
    Admin adm;
    Faculty fac;
    StoredAdminData sad;
    StoredFacultyData sfd;
    String spn="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        member = new Member();
        adm =  new Admin();
        fac=new Faculty();
        sad=new StoredAdminData();
        sfd=new StoredFacultyData();
        ref = FirebaseDatabase.getInstance().getReference().child("Member");
        aref = FirebaseDatabase.getInstance().getReference().child("Admin");
        fref=FirebaseDatabase.getInstance().getReference().child("Faculty");
        adref=FirebaseDatabase.getInstance().getReference().child("StoredAdminData");
        facref=FirebaseDatabase.getInstance().getReference().child("StoredFacultyData");
        email = (EditText) findViewById(R.id.textlogemail);
        userid = (EditText) findViewById(R.id.textlogid);
        password = (EditText) findViewById(R.id.textlogpassword);
        Button login = (Button) findViewById(R.id.textlogbutton);
        Button cancel = (Button) findViewById(R.id.textcanbutton);
        TextView info = (TextView) findViewById(R.id.textlogreg);
        email.setText("");
        userid.setText("");
        password.setText("");


        final Spinner spinner = findViewById(R.id.textspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.usertype,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        login.setOnClickListener(new View.OnClickListener() {
            String mail;
            String pass;
            String uid;
            String spnrr;
            @Override
            public void onClick(View view) {
                mail=email.getText().toString().trim();
                uid = userid.getText().toString().toUpperCase().trim();
                pass = password.getText().toString();
                spnrr=spn;
                if(ref.child(uid) !=null) {
                    ref.child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Member member = dataSnapshot.getValue(Member.class);
                            if(dataSnapshot.exists()) {
                                if (mail.equals(member.getEmail())&& pass.equals(member.getPassword()) && spnrr.equals(member.getUsertype())) {
                                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    if(member.getUsertype().equals("Admin"))
                                        startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                                    else if(member.getUsertype().equals("Student")) {
                                        Intent intent = (new Intent(LoginActivity.this, HomeActivity.class));
                                        intent.putExtra("userid",uid.toUpperCase());
                                        startActivity(intent);
                                    }
                                    else if(member.getUsertype().equals("Faculty")){
                                        String id=uid.toUpperCase();
                                        Intent i=(new Intent(LoginActivity.this, FacultyHomeActivity.class));
                                        i.putExtra("facid",id);
                                        startActivity(i);
                                    }
                                } else {
                                    if(!spnrr.equals(member.getUsertype()))
                                        Toast.makeText(LoginActivity.this, "you were registered as different user", Toast.LENGTH_SHORT).show();
                                    else Toast.makeText(LoginActivity.this, "Enter correct details...", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Login Error..! please register to login", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setText("");
                userid.setText("");
                password.setText("");
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text =adapterView.getItemAtPosition(i).toString();
        spn = text;
        Toast.makeText(adapterView.getContext(), text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }
}
