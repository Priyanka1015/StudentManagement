package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddFacultyActivity extends AppCompatActivity {

    private EditText addfacid;
    private EditText addfacname;
    private Button submit;
    private Button cancel;
    DatabaseReference sfdref;
    StoredFacultyData sfd;
    long c=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_faculty);

        sfd= new StoredFacultyData();
        sfdref= FirebaseDatabase.getInstance().getReference().child("StoredFacultyData");
        addfacid= (EditText) findViewById(R.id.addfacid);
        addfacname= (EditText) findViewById(R.id.addfacname);
        submit= (Button) findViewById(R.id.submit);
        cancel= (Button) findViewById(R.id.cancel);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=addfacid.getText().toString().toUpperCase().trim();
                String name=addfacname.getText().toString().toUpperCase().trim();
                if(sfdref.child(id)!=null){
                    sfdref.child(id).addValueEventListener(new ValueEventListener() {
                        String id=addfacid.getText().toString().toUpperCase().trim();
                        String name=addfacname.getText().toString().toUpperCase().trim();
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            StoredFacultyData sfd=dataSnapshot.getValue(StoredFacultyData.class);
                            if(dataSnapshot.exists()){
                                if(id.equalsIgnoreCase(sfd.getFacid()) && name.equalsIgnoreCase(sfd.getFacname())) {
                                    if(c==1)
                                        Toast.makeText(AddFacultyActivity.this, "Faculty already added..!", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(AddFacultyActivity.this,"Faculty added!",Toast.LENGTH_SHORT).show();

                                }
                                else
                                    Toast.makeText(AddFacultyActivity.this,"Enter correct details",Toast.LENGTH_SHORT).show();
                            }
                            else c=0;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                if(c==0){
                    sfd.setFacid(id);
                    sfd.setFacname(name);
                    sfdref.child(id).setValue(sfd);
                    startActivity(new Intent(AddFacultyActivity.this,AdminHomeActivity.class));

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addfacid.setText("");
                addfacname.setText("");
            }
        });

    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(AddFacultyActivity.this,AdminHomeActivity.class));
    }
}
