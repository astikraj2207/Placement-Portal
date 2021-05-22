package com.example.placement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompanyCreateJob extends AppCompatActivity implements View.OnClickListener {
    EditText EDTposition, EDTdescription ,number_of_positions ;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootnode;
    private DatabaseReference mref;
    static String name_of_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_create_job);

        mAuth= FirebaseAuth.getInstance();
        EDTposition = (EditText) findViewById(R.id.editTextTextPersonName2);
        EDTdescription = (EditText) findViewById(R.id.editTextTextPersonName3);
        number_of_positions = (EditText) findViewById(R.id.editTextNumber2);

        findViewById(R.id.button6).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button6:
                rootnode= FirebaseDatabase.getInstance();
                mref=rootnode.getReference("jobs");
                CreateNewJob();
                break;
        }

    }
    public void CreateNewJob() {
        String position= EDTposition.getText().toString().trim();
        String description= EDTdescription.getText().toString().trim();
        int number= Integer.parseInt(number_of_positions.getText().toString().trim() );
        FirebaseUser user= mAuth.getCurrentUser();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass userdata = snapshot.getValue(UserHelperClass.class);
                String name_of_company = userdata.getName();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        mref.addValueEventListener(postListener);

        CompanyHelperClass CHC = new CompanyHelperClass(user.getUid(),name_of_company, position, description, number, false);
        mref.child(user.getUid() + System.currentTimeMillis()).setValue(CHC).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "job profile added", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Addition failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return;
    }
}