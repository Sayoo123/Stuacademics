package com.example.stuacademics;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class Registeration extends AppCompatActivity {
    EditText email,password,confirm_password;
    Button register;
    ProgressBar bar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        email=findViewById(R.id.Register_email);
        password=findViewById(R.id.Register_password);
        confirm_password=findViewById(R.id.Register_confirmpassword);
        register=findViewById(R.id.Register);
        mAuth= FirebaseAuth.getInstance();
        bar=findViewById(R.id.progress_bar);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        TextView tologin=findViewById(R.id.backtologin);
        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registeration.this, Login.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                String emailauth,passwordauth,cr_password_auth;
                emailauth=String.valueOf(email.getText());
                passwordauth=String.valueOf(password.getText());
                cr_password_auth=String.valueOf(confirm_password.getText());
                System.out.println(emailauth+passwordauth+cr_password_auth);
                if(TextUtils.isEmpty(emailauth))
                {
                    Toast.makeText(Registeration.this,"Fill Email field",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordauth))
                {
                    Toast.makeText(Registeration.this,"Fill Password field",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(passwordauth.equals(cr_password_auth)))
                {
                    Toast.makeText(Registeration.this,"Password mismatch",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(emailauth, passwordauth)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                bar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Registeration.this, "Registered Successfully.",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Registeration.this, Login.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Registeration.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
}