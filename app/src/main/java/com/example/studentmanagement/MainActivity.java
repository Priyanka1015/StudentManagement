package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class  MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText name;
    private EditText email;
    private EditText userid;
    private EditText password;

    DatabaseReference ref,aref,fref,sref,adref,facref;
    Member member;
    Admin adm;
    Faculty fac;
    Student std;
    StoredAdminData sad;
    StoredFacultyData sfd;
    String spn="";
    long flag=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        member = new Member();
        adm = new Admin();
        fac=new Faculty();
        std=new Student();
        sad=new StoredAdminData();
        sfd=new StoredFacultyData();
        ref = FirebaseDatabase.getInstance().getReference().child("Member");
        aref = FirebaseDatabase.getInstance().getReference().child("Admin");
        fref=FirebaseDatabase.getInstance().getReference().child("Faculty");
        sref=FirebaseDatabase.getInstance().getReference().child("Student");
        adref=FirebaseDatabase.getInstance().getReference().child("StoredAdminData");
        facref=FirebaseDatabase.getInstance().getReference().child("StoredFacultyData");
        name = (EditText) findViewById(R.id.textname);
        email = (EditText) findViewById(R.id.textemail);
        userid = (EditText) findViewById(R.id.textregid);
        password = (EditText) findViewById(R.id.textpassword);
        Button register = (Button) findViewById(R.id.textregbutton);
        Button cancel = (Button) findViewById(R.id.textcancelbutton);
        TextView info = (TextView) findViewById(R.id.textreglog);


        final Spinner spinner = findViewById(R.id.textspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.usertype,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = name.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String id = userid.getText().toString().trim().toUpperCase();
                String pass = password.getText().toString();
                String spnr = spn;
                if (uname.isEmpty()) {
                    name.setError("please enter username");
                    name.requestFocus();
                } else if (mail.isEmpty()) {
                    email.setError("please enter mail");
                    email.requestFocus();
                } else if (id.isEmpty()) {
                    userid.setError("please enter id");
                    userid.requestFocus();
                } else if (pass.isEmpty()) {
                    password.setError("please enter password");
                    password.requestFocus();
                }
                if (!(uname.isEmpty() && id.isEmpty() && mail.isEmpty() && pass.isEmpty())) {
                    if (ref.child(id) != null) {
                        ref.child(id).addValueEventListener(new ValueEventListener() {

                            String ename = name.getText().toString().trim();
                            String mail = email.getText().toString().trim();
                            String pass = password.getText().toString();
                            String spnrr = spn;
                            String id = userid.getText().toString().trim().toUpperCase();

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Member member = dataSnapshot.getValue(Member.class);
                                if (dataSnapshot.exists()) {
                                    if (ename.equalsIgnoreCase(member.getName()) && mail.equals(member.getEmail()) && id.equals(member.getUserid()) && pass.equals(member.getPassword()) && spnrr.equals(member.getUsertype())) {
                                        Toast.makeText(MainActivity.this, "You have already registered! Please login", Toast.LENGTH_LONG).show();
                                    } else {
                                        if (!spnrr.equalsIgnoreCase(member.getUsertype()))
                                            Toast.makeText(MainActivity.this, "You already registered as a different user", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(MainActivity.this, "Enter correct details..!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else flag=0;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                if(flag==0) {
                    if (spnr.equals("Student")) {
                        if (adref.child(id) != null) {
                            adref.child(id).addValueEventListener(new ValueEventListener() {
                                String aname = name.getText().toString().trim();
                                String aid = userid.getText().toString().trim().toUpperCase();

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    StoredAdminData sad = dataSnapshot.getValue(StoredAdminData.class);
                                    if (dataSnapshot.exists()) {
                                        if (aname.equalsIgnoreCase(sad.getAdminname()) && aid.equals(sad.getAdminid()))
                                            Toast.makeText(MainActivity.this, "You're an admin, you cant register as student ", Toast.LENGTH_LONG).show();
                                        else
                                            Toast.makeText(MainActivity.this, "Enter correct admin details..!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (facref.child(aid) != null) {
                                            facref.child(aid).addValueEventListener(new ValueEventListener() {
                                                String fname = name.getText().toString().trim();
                                                String fid = userid.getText().toString().trim().toUpperCase();
                                                String mail = email.getText().toString().trim();
                                                String pass = password.getText().toString();

                                                String spnr = spn;

                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    StoredFacultyData sfd = dataSnapshot.getValue(StoredFacultyData.class);
                                                    if (dataSnapshot.exists()) {
                                                        if (fname.equalsIgnoreCase(sfd.getFacname()) && fid.equals(sfd.getFacid()))
                                                            Toast.makeText(MainActivity.this, "You're a lecturer, you cant register as student", Toast.LENGTH_LONG).show();
                                                        else
                                                            Toast.makeText(MainActivity.this, "Enter correct faculty details..!", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        std.setStid(aid);
                                                        std.setStname(fname);
                                                        std.setSpinnervalue(spnr);
                                                        sref.child(std.getStid()).setValue(std);
                                                        Member member1 = new Member();

                                                        member1.setName(fname);
                                                        member1.setEmail(mail);
                                                        member1.setUserid(aid);
                                                        member1.setPassword(pass);
                                                        member1.setUsertype(spnr);
                                                        ref.child(member1.getUserid()).setValue(member1);
//                                                            saflag = 1;
//                                                            sfflag = 1;
                                                        Toast.makeText(MainActivity.this, "You have Successfully Registered.please login", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    } else if (spnr.equals("Admin")) {
                        if (adref.child(id) != null) {
                            adref.child(id).addValueEventListener(new ValueEventListener() {
                                String aname = name.getText().toString().trim();
                                String aid = userid.getText().toString().trim().toUpperCase();
                                String mail = email.getText().toString().trim();
                                String pass = password.getText().toString();
                                String spnrr = spn;

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    StoredAdminData sad = dataSnapshot.getValue(StoredAdminData.class);
                                    if (dataSnapshot.exists()) {
                                        if (aname.equalsIgnoreCase(sad.getAdminname()) && aid.equals(sad.getAdminid())) {
                                            //aflag = 0;
                                            adm.setUid(aid);
                                            adm.setSpinnerval(spnrr);
                                            aref.child(adm.getUid()).setValue(adm);
                                            Member member1 = new Member();

                                            member1.setName(aname);
                                            member1.setEmail(mail);
                                            member1.setUserid(aid);
                                            member1.setPassword(pass);
                                            member1.setUsertype(spnrr);
                                            ref.child(member1.getUserid()).setValue(member1);
                                            Toast.makeText(MainActivity.this, "You have Successfully Registered. please login", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this, LoginActivity.class));

                                        } else
                                            Toast.makeText(MainActivity.this, "Enter correct admin details..!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "You are not an admin", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    } else if (spnr.equals("Faculty")) {
                        if (facref.child(id) != null) {
                            facref.child(id).addValueEventListener(new ValueEventListener() {
                                String fname = name.getText().toString().trim();
                                String fid = userid.getText().toString().trim().toUpperCase();
                                String mail = email.getText().toString().trim();
                                String pass = password.getText().toString();
                                String spnrr = spn;

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    StoredFacultyData sfd = dataSnapshot.getValue(StoredFacultyData.class);
                                    if (dataSnapshot.exists()) {
                                        if (fname.equalsIgnoreCase(sfd.getFacname()) && fid.equals(sfd.getFacid())) {
                                            //fflag = 0;
                                            fac.setFacid(fid);
                                            fac.setFacname(fname);
                                            fac.setSpinnervalue(spnrr);
                                            fref.child(fac.getFacid()).setValue(fac);
                                            Member member1 = new Member();

                                            member1.setName(fname);
                                            member1.setEmail(mail);
                                            member1.setUserid(fid);
                                            member1.setPassword(pass);
                                            member1.setUsertype(spnrr);
                                            ref.child(member1.getUserid()).setValue(member1);
                                            //fflag = 1;
                                            Toast.makeText(MainActivity.this, "You have Successfully Registered.please login", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                        } else
                                            Toast.makeText(MainActivity.this, "Enter correct faculty details..!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "You are not one of the faculty member", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
                }
                else Toast.makeText(MainActivity.this,"Registration error",Toast.LENGTH_SHORT).show();
            }

        });


        info.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                email.setText("");
                userid.setText("");
                password.setText("");
                flag=1;
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        spn = text;
        Toast.makeText(parent.getContext(), text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getWindow().setBackgroundDrawableResource(R.drawable.custom_button);
    }

}
