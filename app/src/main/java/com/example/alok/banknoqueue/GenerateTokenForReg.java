package com.example.alok.banknoqueue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class GenerateTokenForReg extends AppCompatActivity {

    //Firebase Items
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private Spinner branchSpinner;
    private Spinner serviceSpineer;

    private Button genToken;


    final String TAG = "GenerateTokenForReg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_token_for_reg);

        Intent intent = getIntent();
        final String UserId = intent.getStringExtra("UserId");

        databaseReference = FirebaseDatabase.getInstance().getReference();

        branchSpinner = (Spinner) findViewById(R.id.branchSpinner);
        serviceSpineer = (Spinner) findViewById(R.id.serviceSpinner);
        genToken = (Button) findViewById(R.id.generateToken);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final DataSnapshot user = dataSnapshot.child("USER").child(UserId);
                final DataSnapshot bank = dataSnapshot.child("BRANCHES");

                genToken.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        generateToken(user,bank,UserId);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void generateToken(DataSnapshot user, DataSnapshot branch, String userID)
    {

        if(user.getValue(UserInformation.class).getTokenInd().toString().equals("N")  )
        {
            //User Data
            String BranchName = branchSpinner.getSelectedItem().toString();
            String Name = user.getValue(UserInformation.class).getName().toString();
            String Service = serviceSpineer.getSelectedItem().toString();

            //Branch Data
            branch = branch.child(BranchName);
            String tokenno  = branch.getValue(Branch.class).getTokenNo().toString();

            int token =  Integer.parseInt(tokenno);
            token++;

            int time = 950;
            time = time + token*10 ;

            String stime= String.valueOf(time);
            String hh = stime.substring(0,2);
            String mm = stime.substring(2,4);
            String _24hour = hh+":"+mm;

            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            String _12hour = _12HourSDF.format(_24hour);



            if(token < 50) {
                //Start Updation
                UserInformation userInformation = new UserInformation(BranchName, Name, Service, "Y", String.valueOf(token), _12hour);
                Branch branch1 = new Branch(String.valueOf("1"), String.valueOf(token));
                databaseReference.child("USER").child(userID).setValue(userInformation);
                databaseReference.child("BRANCHES").child(BranchName).setValue(branch1);
                finish();
                Intent intent = new Intent(GenerateTokenForReg.this,Profile.class);
                startActivity(intent);
            }

            else {
                Toast.makeText(this,"Queue Full For Today Try Next Day",Toast.LENGTH_LONG).show();
            }

            Log.d(TAG,BranchName + " "+ Name + " " + Service + " "+ user.getValue(UserInformation.class).getTokenInd().toString() + " "
                    + tokenno
            );


            //databaseReference.child("USERS").child(userID).setValue(UserInformation.class.);


        }

    }
}
