package com.network.android.networkcapital;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AcceptActivity extends Activity {

    private DatabaseReference mNUserDatabase;
    private FirebaseUser mCurrentUser;
    private TextView mName, mLoc, mPost, mWork, mHelp, mEd, mLook;
    private ImageView img;

    private ImageView mProfileImage;
    private TextView mProfileName, mProfileStatus, mProfileFriendsCount;
    private Button mProfileSendReqBtn, mDeclineBtn;

    private DatabaseReference mUsersDatabase;

    private ProgressDialog mProgressDialog;

    private DatabaseReference mFriendReqDatabase;
    private DatabaseReference mFriendDatabase;
    private DatabaseReference mNotificationDatabase,mCheckDatabase;

    private DatabaseReference mRootRef;

    private FirebaseUser mCurrent_user;

    private String mCurrent_state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);
        final String current_id = getIntent().getStringExtra("user_id");


        mName = (TextView) findViewById(R.id.textView);
        mLoc = (TextView) findViewById(R.id.textView3);
        mPost = (TextView) findViewById(R.id.textView2);
        mWork = (TextView) findViewById(R.id.txt4);
        mHelp = (TextView) findViewById(R.id.txt6);
        mEd = (TextView) findViewById(R.id.txt2);
        mLook = (TextView) findViewById(R.id.txt8);
        img = (ImageView) findViewById(R.id.image2);
        final TextView ts = (TextView) findViewById(R.id.text3);
        mProfileSendReqBtn=(Button)findViewById(R.id.profile_send_req_btn);
        mDeclineBtn=(Button)findViewById(R.id.profile_decline_btn);


        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_id);

        mCheckDatabase= FirebaseDatabase.getInstance().getReference().child("Check").child(current_id);
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String loc = dataSnapshot.child("location").getValue().toString();
                String pos = dataSnapshot.child("position").getValue().toString();
                String work = dataSnapshot.child("work").getValue().toString();
                String help = dataSnapshot.child("help").getValue().toString();
                String ed = dataSnapshot.child("education").getValue().toString();
                String look = dataSnapshot.child("look").getValue().toString();
                String image = dataSnapshot.child("thumb_image").getValue().toString();
                Picasso.with(AcceptActivity.this).load(image).placeholder(R.drawable.user).into(img);
                String tscore = dataSnapshot.child("TrustScore").getValue().toString();

                mName.setText(name);
                ts.setText(tscore);
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

        mProfileSendReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsersDatabase.child("Verified").setValue("1");
                mUsersDatabase.child("Reject").setValue("0");
                mCheckDatabase.child("location").setValue(null);
                mCheckDatabase.child("position").setValue(null);
                mCheckDatabase.child("work").setValue(null);
                mCheckDatabase.child("help").setValue(null);
                mCheckDatabase.child("Verified").setValue(null);
                mCheckDatabase.child("email").setValue(null);
                mCheckDatabase.child("look").setValue(null);
                mCheckDatabase.child("education").setValue(null);
                mCheckDatabase.child("rating").setValue(null);
                mCheckDatabase.child("thumb_image").setValue(null);
                mCheckDatabase.child("device_token").setValue(null);
                mCheckDatabase.child("TrustScore").setValue(null);
                mCheckDatabase.child("count").setValue(null);
                mCheckDatabase.child("First Login").setValue(null);
                mCheckDatabase.child("name").setValue(null);
                mCheckDatabase.child("Reject").setValue(null);
                mCheckDatabase.child("Card").setValue(null);


            }
        });
        mDeclineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsersDatabase.child("Verified").setValue("false");
                mUsersDatabase.child("Reject").setValue("1");
                mCheckDatabase.child("location").setValue(null);
                mCheckDatabase.child("Verified").setValue(null);
                mCheckDatabase.child("email").setValue(null);
                mCheckDatabase.child("position").setValue(null);
                mCheckDatabase.child("work").setValue(null);
                mCheckDatabase.child("help").setValue(null);
                mCheckDatabase.child("look").setValue(null);
                mCheckDatabase.child("education").setValue(null);
                mCheckDatabase.child("rating").setValue(null);
                mCheckDatabase.child("thumb_image").setValue(null);
                mCheckDatabase.child("device_token").setValue(null);
                mCheckDatabase.child("TrustScore").setValue(null);
                mCheckDatabase.child("count").setValue(null);
                mCheckDatabase.child("First Login").setValue(null);
                mCheckDatabase.child("name").setValue(null);
                mCheckDatabase.child("Reject").setValue(null);
                mCheckDatabase.child("Card").setValue(null);


            }
        });

    }
}