package com.example.watercontrolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private EditText Email, password;
    private TextView SignUp;
    private Button SignIn;
    private FirebaseAuth auth;
    TextView passForget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword);
        passForget = findViewById(R.id.textView2);
        SignIn = findViewById(R.id.button);
        SignUp = findViewById(R.id.textView4);
        //Using Firebase Auth
        auth = FirebaseAuth.getInstance();
        //aller au page signup
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
        //aller au page principal
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String email =Email.getText().toString();
                 String pass =password.getText().toString();
                 if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                     if (!pass.isEmpty()){
                         auth.signInWithEmailAndPassword(email, pass)
                                 .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                     @Override
                                     public void onSuccess(AuthResult authResult) {
                                         Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                         startActivity(new Intent(MainActivity.this, ShowStatusActivity.class));
                                         finish();
                                     }
                                 }).addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception e) {
                                         Toast.makeText(MainActivity.this, "LoginFailed", Toast.LENGTH_SHORT).show();
                                     }
                                 });
                     }else {
                         password.setError("Password cannot be empty");
                     }
                 }else if (email.isEmpty()){
                     Email.setError("Email cannot be empty");
                 }else {
                     Email.setError("Please enter valid email");
                 }

            }
        });
    passForget.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot, null);
            EditText emailBox = dialogView.findViewById(R.id.emailbox);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userEmail = emailBox.getText().toString();
                    if(TextUtils.isEmpty(userEmail)&& !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                        Toast.makeText(MainActivity.this, "Enter your registered email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(MainActivity.this, "Unable to send , Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            if (dialog.getWindow() !=null){
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
            }
            dialog.show();
        }
    });
    }
}