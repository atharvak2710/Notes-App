package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText mloginemail,mloginpassword;
    RelativeLayout mlogin,mgotosignup;
    TextView mgotoforgotpassword;
private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mloginemail=findViewById(R.id.loginemail);
        mloginpassword=findViewById(R.id.loginpassword);
        mlogin=findViewById(R.id.login);
        mgotosignup=findViewById(R.id.gotosignup);
        mgotoforgotpassword=findViewById(R.id.gotoforgotpassword);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        mgotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,signup.class));
            }
        });

        mgotoforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,forgotpassword.class));
            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String mail=mloginemail.getText().toString().trim();
                String password=mloginpassword.getText().toString().trim();

                if(mail.isEmpty()|| password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "All field  are required",Toast.LENGTH_SHORT).show();
                }
                else
                {
               firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       if(task.isSuccessful())
                       {

                           checkmailVerification();

                       }
                       else
                       {
                           Toast.makeText(getApplicationContext(),"Wrong Email or Password",Toast.LENGTH_SHORT).show();

                       }
                   }
               });
                }
            }
        });
    }
    private void checkmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified() == true)
        {

            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this, homepage.class));

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Verfiy Your Mail First",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();

        }

    }
}