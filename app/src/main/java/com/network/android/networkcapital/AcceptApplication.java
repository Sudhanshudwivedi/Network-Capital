package com.network.android.networkcapital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AcceptApplication extends AppCompatActivity {
    private Button mbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_application);

        mbtn=(Button)findViewById(R.id.Aslogout);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent startIntent = new Intent(AcceptApplication.this, LoginActivity.class);
                startActivity(startIntent);
                finish();
            }
        });
    }
}
