package com.network.android.networkcapital.Modules;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.network.android.networkcapital.CommentNC;
import com.network.android.networkcapital.R;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {

    private DatabaseReference mNotificationDatabase,MpostDatabase;
    private DatabaseReference mNUserDatabase;
    private FirebaseUser mCurrentUser;
    private TextView mName,mLoc,mPost,mWork,mHelp,mEd,mLook;
    private ImageView img;

    private ImageView mProfileImage;
    private TextView mProfileName, mProfileStatus, mProfileFriendsCount;
    private Button mProfileSendReqBtn, mDeclineBtn;

    private DatabaseReference mUsersDatabase;

    private ProgressDialog mProgressDialog;

    private DatabaseReference mFriendReqDatabase;
    private DatabaseReference mFriendDatabase;


    private DatabaseReference mRootRef,mNcDatabase;

    private FirebaseUser mCurrent_user;

    private String mCurrent_state,uid;
    private String uNid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        final String c_id = getIntent().getStringExtra("Puser_id");

        mName=(TextView)findViewById(R.id.textView);
        mLoc=(TextView)findViewById(R.id.textView3);
        mPost=(TextView)findViewById(R.id.textView2);
        mWork=(TextView)findViewById(R.id.txt4);
        mHelp=(TextView)findViewById(R.id.txt6);
        mEd=(TextView)findViewById(R.id.txt2);
        mLook=(TextView)findViewById(R.id.txt8);
        img=(ImageView)findViewById(R.id.image2);



        MpostDatabase = FirebaseDatabase.getInstance().getReference().child("Post").child(c_id);
        MpostDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uid=dataSnapshot.child("user_id").getValue().toString();
                System.out.print("String user id" + uid.toString());

                Toast.makeText(getApplicationContext(),uid.toString(),Toast.LENGTH_LONG).show();
                detail();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void detail()
    {
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                String loc=dataSnapshot.child("location").getValue().toString();
                String pos=dataSnapshot.child("position").getValue().toString();
                String work=dataSnapshot.child("work").getValue().toString();
                String help=dataSnapshot.child("help").getValue().toString();
                String ed=dataSnapshot.child("education").getValue().toString();
                String look=dataSnapshot.child("look").getValue().toString();
                String image=dataSnapshot.child("thumb_image").getValue().toString();
                Picasso.with(UserProfile.this).load(image).placeholder(R.drawable.user).into(img);


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
