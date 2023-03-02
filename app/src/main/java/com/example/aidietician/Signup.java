package com.example.aidietician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {

    EditText editTextEmail,editTextPassword,editTextPno,editTextDob;

    Button signUpBtn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Signup");
        mAuth=FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.edttxtUsername);
        editTextPassword = findViewById(R.id.edttxtPassword);
        editTextDob=findViewById(R.id.edttxtDOB);
        editTextPno = findViewById(R.id.edttxtPhone);
        signUpBtn=findViewById(R.id.btnSignUp);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                email = String.valueOf(editTextEmail.getText());
                password= String.valueOf(editTextPassword.getText());
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
                /*if(TextUtils.isEmpty(phoneno))
                {
                    Toast.makeText(Signup.this, "Enter Phone number", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(phoneno))
                {
                    Toast.makeText(Signup.this, "Enter Phone number", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Signup.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });
    }

    public void onBtnNextClick(View v){
        Button btnSignup = findViewById(R.id.btnSignUp);
        startActivity(new Intent(this,Details.class));
    }

    public void onAcntClick(View v2){
        startActivity(new Intent(this,MainActivity.class));
    }
}