package com.example.watercontrolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText Name, Email, Password, RePassword, Phone;
    private Button SignUp;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private FloatingActionButton back ;
    SignInButton GoogleButton ;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        //Identifier les champs
        Name = findViewById(R.id.editTextTextPersonName2);
        Email = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword3);
        RePassword = findViewById(R.id.editTextTextPassword4);
        Phone = findViewById(R.id.editTextPhone);
        SignUp = findViewById(R.id.button2);
        back = findViewById(R.id.floatingActionButton);
        GoogleButton = findViewById(R.id.sign_in_button);
        //Retour au page de login
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(j);
            }
        });

        //aller au la page signIn
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //using Auth Firebase
                String user = Email.getText().toString().trim();
                String pass = Password.getText().toString().trim();
                if (user.isEmpty()){
                    Email.setError("Email cannot be empty");
                }
                if (pass.isEmpty()){
                    Password.setError("Password cannot be empty");
                }else {
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(SignUpActivity.this, "SignUp successful", Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                           }else{
                               Toast.makeText(SignUpActivity.this, "SignUp Failed"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
                }
                Intent k = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(k);
            }
        });
        // signup avec google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        GoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.sign_in_button:
                        signUpGoogle();
                        break;
                }
            }
        });


    }
    void signUpGoogle (){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            navigateToSecondActivity();
        }catch (ApiException e){
            Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(SignUpActivity.this, ConfirmationSignUpEmailActivity.class);
        startActivity(intent);
    }
}