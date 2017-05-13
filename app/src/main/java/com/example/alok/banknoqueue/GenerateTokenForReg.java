package com.example.alok.banknoqueue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

public class GenerateTokenForReg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_token_for_reg);
    }
/*

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

    }*/
}
