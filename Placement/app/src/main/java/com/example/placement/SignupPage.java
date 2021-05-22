package com.example.placement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupPage extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase rootnode;
    private DatabaseReference mref;
    int v=1;
    EditText EDTemail, EDTpassword, EDTname;
    ProgressBar progressBar;
    CheckBox checkbox1, checkbox2, checkbox3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        mAuth=FirebaseAuth.getInstance();

        EDTemail = (EditText) findViewById(R.id.newemail);
        EDTpassword = (EditText) findViewById(R.id.newpassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        EDTname = (EditText) findViewById(R.id.EditTextName);
        checkbox1= (CheckBox) findViewById(R.id.checkBox1);
        checkbox2= (CheckBox) findViewById(R.id.checkBox2);
        checkbox3= (CheckBox) findViewById(R.id.checkBox3);
        progressBar.setVisibility(View.INVISIBLE);

        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.gotologin).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button2 :
                String email= EDTemail.getText().toString().trim();
                String password= EDTpassword.getText().toString().trim();
                String name1 = EDTname.getText().toString().trim();
                rootnode= FirebaseDatabase.getInstance();
                mref=rootnode.getReference("users");

                RegisterUser(email, password,name1);
                break;

            case R.id.gotologin:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void RegisterUser(String email, String password, String name1) {
        if(name1.isEmpty()){
            EDTemail.setError("Name is required!");
            EDTemail.requestFocus();
            return;
        }
        if(email.isEmpty()){
            EDTemail.setError("Email is required!");
            EDTemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            EDTemail.setError("Please enter a valid email");
            EDTemail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            EDTpassword.setError("Password is required!");
            EDTpassword.requestFocus();
            return;
        }

        if(password.length()<6) {
            EDTpassword.setError("Password must consists of atleast 6 characters!");
            EDTpassword.requestFocus();
            return;
        }
        if (!checkbox1.isChecked() && !checkbox2.isChecked() && !checkbox3.isChecked())
        {
            checkbox1.setError("Please check at least one checkbox");
            checkbox1.requestFocus();
            checkbox2.requestFocus();
            checkbox3.requestFocus();
            return;
        }
        if (checkbox1.isChecked() && checkbox2.isChecked() || checkbox3.isChecked() &&
                checkbox2.isChecked() || checkbox1.isChecked() && checkbox3.isChecked() ||
                checkbox1.isChecked() && checkbox2.isChecked() && checkbox3.isChecked())
        {
            checkbox1.setError("Please check only one checkbox");
            checkbox1.requestFocus();
            checkbox2.requestFocus();
            checkbox3.requestFocus();
            return;
        }

        if(checkbox1.isChecked())
            v=1;
        if(checkbox2.isChecked())
            v=2;
        if(checkbox3.isChecked())
            v=3;
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()){

                    FirebaseUser user= mAuth.getCurrentUser();

                    //create entry in database
                    UserHelperClass UHC= new UserHelperClass(email, password,name1, v);
                    mref.child(user.getUid()).setValue(UHC);

                    if (v==1)
                    {
                        startActivity(new Intent(SignupPage.this, StudentProfileActivity.class));
                    }
                    else if(v==2) {
                        startActivity(new Intent(SignupPage.this, CompanyProfileActivity.class));
                    }
                    else if(v==3){
                        startActivity(new Intent(SignupPage.this, ModeratorProfileActivity.class));
                    }
                    finish();
                }
            }
        });

    }

}