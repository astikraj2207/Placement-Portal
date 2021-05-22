package com.example.placement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompanyProfileActivity extends AppCompatActivity implements View.OnClickListener {


    TextView Welcome;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootnode;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        mAuth= FirebaseAuth.getInstance();
        Welcome = (TextView) findViewById(R.id.welcome);
        ShowMessage();
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
    }

    private void ShowMessage() {
        FirebaseUser user= mAuth.getCurrentUser();
        rootnode= FirebaseDatabase.getInstance();
        mref=rootnode.getReference("users");
        mref=mref.child(user.getUid());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass userdata = snapshot.getValue(UserHelperClass.class);
                String Name ="WELCOME " + userdata.getName() + "!";
                Welcome.setText(Name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mref.addValueEventListener(postListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button4:
                mAuth.signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.button5:
                startActivity(new Intent(this, CompanyCreateJob.class));
                break;
        }

    }
}