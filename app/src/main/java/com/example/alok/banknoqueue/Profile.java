package com.example.alok.banknoqueue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.*;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {


    String TAG = "ProfileActivity";
    //Firebase Items
    private FirebaseAuth firebaseAuth;
    private TextView userIdText;
    private TextView branchName;
    private TextView tokenNumber;
    private DatabaseReference databaseReference;
    private Button update;


    private Button signout;
    private Spinner mBanchSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        signout = (Button) findViewById(R.id.SignoutBtn);
        mBanchSpinner = (Spinner) findViewById(R.id.branchSelector);
        branchName = (TextView) findViewById(R.id.branchNameTextView);
        tokenNumber = (TextView) findViewById(R.id.tokenCountTextView);
        update = (Button) findViewById(R.id.update);


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

                userIdText.setText("Welcome "+user.getValue(UserInformation.class).getName());
                branchName.setText(user.getValue(UserInformation.class).getBranch());
                tokenNumber.setText(user.getValue(UserInformation.class).getTokenNo());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        generateToken(user,bank,userID);
                    }
                });


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

    public void generateToken(DataSnapshot user, DataSnapshot branch, String userID)
    {

        if(user.getValue(UserInformation.class).getTokenInd().toString().equals("N")  )
        {
            //User Data
            String BranchName = mBanchSpinner.getSelectedItem().toString();
            String Name = user.getValue(UserInformation.class).getName().toString();
            String Service = user.getValue(UserInformation.class).getService().toString();

            //Branch Data
            branch = branch.child(BranchName);
            String tokenno  = branch.getValue(Branch.class).getTokenNo().toString();

            int token =  Integer.parseInt(tokenno);
            token++;


            //Start Updation
            UserInformation userInformation = new UserInformation(BranchName,Name,"Service","Y",String.valueOf(token),String.valueOf("1"));
            Branch branch1 = new Branch(String.valueOf("1"),String.valueOf(token));
            databaseReference.child("USER").child(userID).setValue(userInformation);
            databaseReference.child("BRANCHES").child(BranchName).setValue(branch1);


            Log.d(TAG,BranchName + " "+ Name + " " + Service + " "+ user.getValue(UserInformation.class).getTokenInd().toString() + " "
            + tokenno
            );


            //databaseReference.child("USERS").child(userID).setValue(UserInformation.class.);


        }

    }
}
