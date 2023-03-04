package com.example.aidietician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {


    EditText editTextEmail,editTextPassword,editTextPno,editTextDob,editTextFullname;

    Button signUpBtn;
    FirebaseAuth mAuth;
    FirebaseFirestore f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth=FirebaseAuth.getInstance();
        f=FirebaseFirestore.getInstance();
        editTextEmail = findViewById(R.id.edttxtUsername);
        editTextPassword = findViewById(R.id.edttxtPassword);
        editTextDob=findViewById(R.id.edttxtDOB);
        editTextPno = findViewById(R.id.edttxtPhone);
        signUpBtn=findViewById(R.id.btnSignUp);
        editTextFullname = findViewById(R.id.edttxtName);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password,phoneno,fullname,dob;
                email = String.valueOf(editTextEmail.getText());
                password= String.valueOf(editTextPassword.getText());
                fullname= String.valueOf(editTextFullname.getText());
                phoneno = String.valueOf(editTextPno.getText());
                dob = String.valueOf(editTextPno.getText());
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(Signup.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(Signup.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(phoneno))
                {
                    Toast.makeText(Signup.this, "Enter Phone number", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(fullname))
                {
                    Toast.makeText(Signup.this, "Enter Fullname", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(dob))
                {
                    Toast.makeText(Signup.this, "Enter Date of birth", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    String userID = mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference= f.collection( "users" ).document(userID);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("email",email);
                                    user.put("fname",fullname);
                                    user.put("pno",phoneno);
                                    user.put("dob",dob);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                           // Log.d(TAG,"OnSuccess:user profile created for:"+userID);
                                        }
                                    });


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Signup.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                Intent intent= new Intent(getApplicationContext(),Details.class );
                startActivity(intent);
                finish();
            }
        });
    }

    public void onBtnNextClick(View v){
        Button btnSignup = findViewById(R.id.btnSignUp);
        startActivity(new Intent(this,Details.class));
        finish();
    }
    //Error in xml :-@color/material_dynamic_primary50
    public void onAcntClick(View v2){
        startActivity(new Intent(this, LoginPage.class));
    }
}