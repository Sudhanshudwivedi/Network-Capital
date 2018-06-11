package com.example.android.networkcapital;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class NetworkCard extends Activity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private TextView mName,mLoc,mPost,mWork,mHelp,mEd,mLook;
    private ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_card);
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_id = mCurrentUser.getUid();
        mName=(TextView)findViewById(R.id.textView);
        mLoc=(TextView)findViewById(R.id.textView3);
        mPost=(TextView)findViewById(R.id.textView2);
        mWork=(TextView)findViewById(R.id.txt4);
        mHelp=(TextView)findViewById(R.id.txt6);
        mEd=(TextView)findViewById(R.id.txt2);
        mLook=(TextView)findViewById(R.id.txt8);
        img=(ImageView)findViewById(R.id.image2);

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_id);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                String loc=dataSnapshot.child("location").getValue().toString();
               String pos=dataSnapshot.child("position").getValue().toString();
                String work=dataSnapshot.child("work").getValue().toString();
                String help=dataSnapshot.child("help").getValue().toString();
                String ed=dataSnapshot.child("education").getValue().toString();
                String look=dataSnapshot.child("look").getValue().toString();
                String image=dataSnapshot.child("image").getValue().toString();
                Picasso.with(NetworkCard.this).load(image).placeholder(R.drawable.user).into(img);


                mName.setText(name);

                mLoc.setText(loc);
               mPost.setText(pos);
                mWork.setText(work);
                mHelp.setText(help);
                mEd.setText(ed);
                mLook.setText(look);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
