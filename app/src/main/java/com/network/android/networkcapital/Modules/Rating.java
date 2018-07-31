package com.network.android.networkcapital.Modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.network.android.networkcapital.MainActivity;
import com.network.android.networkcapital.PostActivity;
import com.network.android.networkcapital.ProfileActivity;
import com.network.android.networkcapital.R;
import com.squareup.picasso.Picasso;

public class Rating extends AppCompatActivity{

    Button submit_btn;
    RatingBar user_rating;
    private DatabaseReference rDatabase,frDatabase;
    private ImageView img;
    private TextView nme;
    private float Nrating;
    private float Count=0;
    private String sd;

    float usr_login_rating;
    private String SumRating;
    private FirebaseUser mCurrentUser;


    private int n1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        final String current_id = getIntent().getStringExtra("user_id");

        submit_btn = (Button) findViewById(R.id.Submit);
        img=(ImageView)findViewById(R.id.user_image);
        nme=(TextView)findViewById(R.id.user_name);
        user_rating = (RatingBar) findViewById(R.id.ratings);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = mCurrentUser.getUid();
        rDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_id);
        rDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                String image=dataSnapshot.child("thumb_image").getValue().toString();
                 Count=Float.parseFloat(dataSnapshot.child("count").getValue().toString());

                Nrating = Float.parseFloat(dataSnapshot.child("rating").getValue().toString());
                Picasso.with(Rating.this).load(image).placeholder(R.drawable.user).into(img);
                nme.setText(name);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        frDatabase= FirebaseDatabase.getInstance().getReference().child("Friends").child(uid).child(current_id);

        frDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sd=dataSnapshot.child("date").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
                String rating=String.valueOf(usr_login_rating);
                float Rrating = Float.parseFloat(rating);
                Rrating= Float.parseFloat(String.valueOf(Rrating+Nrating));
                String s = Float.toString(Rrating);

                //String s=String.valueOf(n2);
                rDatabase.child("rating").setValue(s);
                Count=Count+1;
                rDatabase.child("count").setValue(Count);
                float Ts=Float.parseFloat(String.valueOf(Rrating/Count));

                float number2 = (int)Math.round(Ts * 100)/(float)100;
                String ts=Float.toString(number2);

                rDatabase.child("TrustScore").setValue(ts);



                if(sd.equals("True"))
                {
                    Toast.makeText(getApplicationContext(),"You have already Rated",Toast.LENGTH_LONG).show();


                }
                else
                {
                    frDatabase.child("date").setValue("True");
                    //Toast.makeText(getApplicationContext(),"Succesfully Rated",Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(Rating.this, MainActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;

                    startActivity(intent);
                    finish();
                }


                //Toast.makeText(getApplicationContext(), (int) Rrating, Toast.LENGTH_LONG).show();
            }
        });


    }

}
