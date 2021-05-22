package com.example.placement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText EDTemail1, EDTpassword1;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootnode;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();
        EDTemail1 = (EditText) findViewById(R.id.newEmail);
        EDTpassword1 = (EditText) findViewById(R.id.newPassword);

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.textview3).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            finish();

            startActivity(new Intent(this,StudentProfileActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview3:
                finish();
                startActivity(new Intent(this, SignupPage.class));
                break;

            case R.id.button:
                LoginUser();
                break;
        }
    }

    private void LoginUser() {
        String email = EDTemail1.getText().toString().trim();
        String password = EDTpassword1.getText().toString().trim();

        if(email.isEmpty()){
            EDTemail1.setError("Email is required!");
            EDTemail1.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            EDTemail1.setError("Please enter a valid email");
            EDTemail1.requestFocus();
            return;
        }

        if(password.isEmpty()){
            EDTpassword1.setError("Password is required!");
            EDTpassword1.requestFocus();
            return;
        }

        if(password.length()<6) {
            EDTpassword1.setError("Password must consists of atleast 6 characters!");
            EDTpassword1.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user= mAuth.getCurrentUser();
                    rootnode= FirebaseDatabase.getInstance();
                    mref=rootnode.getReference("users");
                    mref=mref.child(user.getUid());

                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserHelperClass userdata = snapshot.getValue(UserHelperClass.class);
                            int v =userdata.getStudent();
                            if (v==1)
                            {
                                Intent intent= new Intent(getApplicationContext(), StudentProfileActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else if (v==2)
                            {
                                Intent intent= new Intent(getApplicationContext(), CompanyProfileActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Intent intent= new Intent(getApplicationContext(), ModeratorProfileActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    };
                    mref.addValueEventListener(postListener);
                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}