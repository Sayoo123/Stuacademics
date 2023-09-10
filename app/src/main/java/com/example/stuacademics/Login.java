package com.example.stuacademics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class Login extends AppCompatActivity {
EditText email,password;
ProgressBar bar;
Button logintomain;
FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(Login.this, MainActivity.class));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        TextView reg=findViewById(R.id.Signup);
        email=findViewById(R.id.Loginemail);
        password=findViewById(R.id.Loginpassword);
       // bar=findViewById(R.id.progress_bar);
        logintomain=findViewById(R.id.Login);
        mAuth=FirebaseAuth.getInstance();
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registeration.class));
            }
        });
        logintomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bar.setVisibility(View.VISIBLE);
                String emailauth,passwordauth;
                emailauth=String.valueOf(email.getText());
                passwordauth=String.valueOf(password.getText());

                if(TextUtils.isEmpty(emailauth))
                {
                    Toast.makeText(Login.this,"Fill Email field",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordauth))
                {
                    Toast.makeText(Login.this,"Fill Password field",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(emailauth, passwordauth)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //bar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "LOGGED IN",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, MainActivity.class));

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Login.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }

}