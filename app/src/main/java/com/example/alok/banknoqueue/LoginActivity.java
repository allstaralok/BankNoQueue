package com.example.alok.banknoqueue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private AutoCompleteTextView emailText;
    private EditText passwordText;
    private Button signinBtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), Profile.class));
        }

        emailText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        passwordText = (EditText) findViewById(R.id.passwordText);
        signinBtn = (Button) findViewById(R.id.singinBtn);
        progressDialog = new ProgressDialog(this);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString();

                progressDialog.setMessage("Signing IN Please Wait...");
                progressDialog.show();


                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful())
                                {
                                    progressDialog.dismiss();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(),Profile.class));
                                }

                                if(!task.isSuccessful())
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Wrong Email or Password",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


            }
        });



    }
}
