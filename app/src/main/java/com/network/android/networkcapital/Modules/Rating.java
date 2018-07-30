package com.network.android.networkcapital.Modules;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.network.android.networkcapital.R;

public class Rating extends AppCompatActivity{

    Button submit_btn;
    RatingBar user_rating;

    float usr_login_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        submit_btn = (Button) findViewById(R.id.Submit);
        user_rating = (RatingBar) findViewById(R.id.ratings);


        user_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                usr_login_rating = rating; //ratings store in variable if it changes.
            }
        });



//        System.out.println(user_rating.getNumStars());
//
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), String.valueOf(usr_login_rating), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
