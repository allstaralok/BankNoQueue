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

import java.text.SimpleDateFormat;
import java.util.Date;

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
        int time = 950;
            time = time + Integer.parseInt(token)*10 ;

        String stime= String.valueOf(time);
        String hh = stime.substring(0,2);
        String mm = stime.substring(2,4);
        String _24HourTime = hh+":"+mm;

        String _12hour = "";

        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            _12hour = _12HourSDF.format(_24HourDt);

        }
        catch (Exception e)
        {
            Log.d("Guest Time", "Error while conversion");

        }


        mticketNumber = (TextView) findViewById(R.id.ticketNoTextView);
        mbranch = (TextView) findViewById(R.id.branchTextView);
        mService = (TextView) findViewById(R.id.serviceEditText);
        mTime = (TextView) findViewById(R.id.timeEditText);

        mticketNumber.setText(token);
        mbranch.setText(branch);
        mService.setText(service);
        mTime.setText(_12hour);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Gusestdata");
        String id = mDatabaseReference.push().getKey();
        GuestData mguestData = new GuestData(email,phone,branch,service);
        mDatabaseReference.child(id).setValue(mguestData);


    }
}
