package com.example.alok.banknoqueue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GuestTimeSelection extends AppCompatActivity {

    private TextView mticketNumber;
    private TextView mbranch;
    private TextView mService;
    private TextView mTime;

    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_time_selection);

        //Take the passed data
        Intent intent = getIntent();
        String email  = intent.getStringExtra("Email");
        String phone = intent.getStringExtra("Phone");
        String branch = intent.getStringExtra("Branch");
        String service  = intent.getStringExtra("Service");
        String token = intent.getStringExtra("Token");
        int time = 1000;
            time = time + Integer.parseInt(token)*15 ;

        String sTime = String.valueOf(time);

        mticketNumber = (TextView) findViewById(R.id.ticketNoTextView);
        mbranch = (TextView) findViewById(R.id.branchTextView);
        mService = (TextView) findViewById(R.id.serviceEditText);
        mTime = (TextView) findViewById(R.id.timeEditText);

        mticketNumber.setText(token);
        mbranch.setText(branch);
        mService.setText(service);
        mTime.setText(sTime);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Gusestdata");
        String id = mDatabaseReference.push().getKey();
        GuestData mguestData = new GuestData(email,phone,branch,service);
        mDatabaseReference.child(id).setValue(mguestData);


    }
}
