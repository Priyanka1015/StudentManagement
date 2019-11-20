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

public class AddAdminActivity extends AppCompatActivity {
    private EditText addadminid;
    private EditText addadminname;
    private Button submit;
    private Button cancel;
    DatabaseReference sadref;
    StoredAdminData sad;
    long c=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_admin);

        sad=new StoredAdminData();
        sadref= FirebaseDatabase.getInstance().getReference().child("StoredAdminData");
        addadminid= (EditText) findViewById(R.id.addadminid);
        addadminname=(EditText) findViewById(R.id.addadminname);
        submit= (Button) findViewById(R.id.submit);
        cancel= (Button) findViewById(R.id.cancel);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = addadminid.getText().toString().toUpperCase().trim();
                String name = addadminname.getText().toString().toUpperCase().trim();
                if(sadref.child(id)!=null){
                    sadref.child(id).addValueEventListener(new ValueEventListener() {
                        String id = addadminid.getText().toString().toUpperCase().trim();
                        String name = addadminname.getText().toString().toUpperCase().trim();
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            StoredAdminData sfd= dataSnapshot.getValue(StoredAdminData.class);
                            if(dataSnapshot.exists()){
                                if(id.equalsIgnoreCase(sfd.getAdminid()) && name.equalsIgnoreCase(sfd.getAdminname())) {
                                    if(c==1)
                                        Toast.makeText(AddAdminActivity.this, "Admin already added..!", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(AddAdminActivity.this,"Admin added!",Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(AddAdminActivity.this,"Enter correct details",Toast.LENGTH_SHORT).show();
                            }
                            else c=0;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                if(c==0){
                    sad.setAdminid(id);
                    sad.setAdminname(name);
                    sadref.child(id).setValue(sad);
                    startActivity(new Intent(AddAdminActivity.this,AdminHomeActivity.class));
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addadminid.setText("");
                addadminname.setText("");
            }
        });
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(AddAdminActivity.this,AdminHomeActivity.class));
    }
}
