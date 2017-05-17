package com.example.alok.banknoqueue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {


    String TAG = "ProfileActivity";
    //Firebase Items
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private TextView userIdText;
    private TextView branchName;
    private TextView tokenNumber;
    private TextView serviceTextView;
    private TextView welcome;

    private TextView token;
    private TextView branch;
    private TextView service;
    private TextView time;

    private Button signout;
    private Button update;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        token = (TextView) findViewById(R.id.token);
        branch = (TextView) findViewById(R.id.branch);
        service = (TextView) findViewById(R.id.service);
        time = (TextView) findViewById(R.id.time);

        tokenNumber = (TextView) findViewById(R.id.tokenCountTextView);
        update = (Button) findViewById(R.id.update);
        signout = (Button) findViewById(R.id.SignoutBtn);
        branchName = (TextView) findViewById(R.id.branchNameTextView);
        serviceTextView = (TextView) findViewById(R.id.serviceTextView);
        welcome = (TextView) findViewById(R.id.welcomeMessageTextView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Data, Please Wait !");
        progressDialog.show();



        firebaseAuth = FirebaseAuth.getInstance();
        userIdText = (TextView) findViewById(R.id.user);

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        final String userID  = firebaseAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                final DataSnapshot user = dataSnapshot.child("USER").child(userID);
                final DataSnapshot bank = dataSnapshot.child("BRANCHES");
                Log.d(TAG, "Value is--> " + user.getValue(UserInformation.class).getName());
                Log.d(TAG,"Token Ind" + user.getValue(UserInformation.class).getTokenInd().toString());
                userIdText.setText("Welcome " + user.getValue(UserInformation.class).getName());

                if(user.getValue(UserInformation.class).getTokenInd().toString().equals("Y")) {
                    progressDialog.dismiss();
                    branchName.setText(user.getValue(UserInformation.class).getBranch());
                    tokenNumber.setText(user.getValue(UserInformation.class).getTokenNo());
                    serviceTextView.setText(user.getValue(UserInformation.class).getService());
                    time.setText(user.getValue(UserInformation.class).getTime());
                    welcome.setText("Your Ticket Details Are as Follows:");
                    update.setVisibility(View.INVISIBLE);
                }
                else {
                    //Hiding Unwanted Item

                    branchName.setVisibility(View.INVISIBLE);
                    tokenNumber.setVisibility(View.INVISIBLE);
                    serviceTextView.setVisibility(View.INVISIBLE);
                    branch.setVisibility(View.INVISIBLE);
                    service.setVisibility(View.INVISIBLE);
                    token.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);

                    progressDialog.dismiss();

                    update.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      Intent intent = new Intent(Profile.this, GenerateTokenForReg.class);
                                                      intent.putExtra("UserId",userID);
                                                      finish();
                                                      startActivity(intent);
                                                  }
                                              }
                    );
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });





        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();

                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });



    }


}
