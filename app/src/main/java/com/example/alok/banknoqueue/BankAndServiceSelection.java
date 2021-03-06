package com.example.alok.banknoqueue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankAndServiceSelection extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPhone;
    private Button mSubmit;
    private Spinner mBanchSpinner;
    private Spinner mServiceSpinner;
    private ProgressDialog progressDialog;
    boolean check = false;
    int Gtoken;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_and_service_selection);

        mEmail = (EditText) findViewById(R.id.emailEditText);
        mPhone = (EditText) findViewById(R.id.mobileEditText);
        mSubmit = (Button) findViewById(R.id.submit_btn);
        mBanchSpinner = (Spinner) findViewById(R.id.branch_spinner);
        mServiceSpinner = (Spinner) findViewById(R.id.service_spinner);
        progressDialog = new ProgressDialog(this);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();



        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String phone = mPhone.getText().toString();
                String branch = mBanchSpinner.getSelectedItem().toString();
                String service = mServiceSpinner.getSelectedItem().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone) && isEmailValid(email) && isValidMobile(phone)) {
                    progressDialog.setMessage("Generating Token");
                    progressDialog.show();
                    addToken(email, phone, branch, service);
                }
                else {
                    Toast.makeText(BankAndServiceSelection.this,"Enter Email and Phone",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void addToken(final String email, final String phone, final String branch, final String service){



        DatabaseReference mdatabaseReferenceBranch = FirebaseDatabase.getInstance().getReference().child("BRANCHES").child(branch);

        mdatabaseReferenceBranch.addValueEventListener(new ValueEventListener() {
            @Override
                public void onDataChange (DataSnapshot dataSnapshot){

                String tokenno = dataSnapshot.getValue(Branch.class).getTokenNo().toString();
                Log.d("Token No", tokenno);
                Gtoken = Integer.parseInt(tokenno);
                Log.d("Token Global",String.valueOf(Gtoken));
                update(branch,email,phone,service);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ERROR", "Failed to read value.", databaseError.toException());
            }
        });


    }

    private void update(String branch, String email, String phone, String service) {
        if(!check  && Gtoken <50)
        {
            check = true;
            Log.d("Token Check",String.valueOf(Gtoken));
            generateToken(Gtoken, branch, email, phone, service);
        }
        else if(Gtoken >=50){
            progressDialog.dismiss();
            Toast.makeText(BankAndServiceSelection.this,"Queue is Full for the day !",Toast.LENGTH_SHORT).show();
        }
    }


    private void generateToken(int tokenno,String branch, String email, String phone, String service) {
        int tok = Integer.parseInt(String.valueOf(tokenno));
        tok = tok + 1;
        Log.d("Token ++", String.valueOf(tok));
        if(tok<50)
        {
            int time = 950;
            time = time + tok*10 ;

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
                Log.d("GuestTime", "Error while conversion");

            }
            String s  = String.valueOf(tok);
            Branch branch1 = new Branch(_12hour, s);
            mDatabaseReference.child("BRANCHES").child(branch).setValue(branch1);

            Intent intent = new Intent(BankAndServiceSelection.this,GuestTimeSelection.class);
            intent.putExtra("Email",email);
            intent.putExtra("Phone",phone);
            intent.putExtra("Branch",branch);
            intent.putExtra("Service",service);
            intent.putExtra("Token",s);
            progressDialog.dismiss();
            finish();
            startActivity(intent);


        }
    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    private boolean isValidMobile(String phone) {
        boolean check=false;
            if(phone.length() != 10) {
                check = false;
            } else
                check = true;

        return check;
    }
}
