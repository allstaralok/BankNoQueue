package com.example.alok.banknoqueue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button guestUser;
    private Button registered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guestUser = (Button) findViewById(R.id.button3);
        registered = (Button) findViewById(R.id.registerBtn);

        guestUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGuest();
            }
        });

        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);

                startActivity(intent);
            }
        });






    }

    private void startGuest() {

        Intent intent = new Intent(this,BankAndServiceSelection.class);
        startActivity(intent);
    }
}
