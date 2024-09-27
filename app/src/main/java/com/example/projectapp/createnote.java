package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createnote extends AppCompatActivity {
    EditText createtitleofnote,createcontentofnote;
    FloatingActionButton savenote;
   FirebaseAuth firebaseAuth;
   FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ProgressBar progressbarofcreatenote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);
        savenote=findViewById(R.id.savenote);
        createcontentofnote=findViewById(R.id.createcontentofnote);
        progressbarofcreatenote=findViewById(R.id.progressbarofcreatenote);
        createtitleofnote=findViewById(R.id.createtitleofnote);
        Toolbar toolbar=findViewById(R.id.toolbarofcreatenote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        savenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=createtitleofnote.getText().toString();
                String content=createcontentofnote.getText().toString();
                if(title.isEmpty()||content.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Both Fields Are Required", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressbarofcreatenote.setVisibility(view.VISIBLE);
                    DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document();
                    Map<String,Object> note =new HashMap<>();
                    note.put("title",title);
                    note.put("content",content);
                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(),"Note Created Successfully", Toast.LENGTH_SHORT).show();
                            progressbarofcreatenote.setVisibility(view.INVISIBLE);
                            startActivity(new Intent(createnote.this, homepage.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failed To Create Note", Toast.LENGTH_SHORT).show();
                            progressbarofcreatenote.setVisibility(view.INVISIBLE);

                        }
                    });
                }
            }
        });

    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}