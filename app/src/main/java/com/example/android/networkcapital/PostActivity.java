package com.example.android.networkcapital;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {


    private EditText pst;
    private TextView nam;
    private Button btn;
    private ImageView iv;

    String post;
    private DatabaseReference mPDatabase;
    private DatabaseReference mPDatabase2;
    private String saveCurrentDate;

    private String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);



        pst=(EditText)findViewById(R.id.post);
        btn=(Button)findViewById(R.id.Submit);
        iv=(ImageView)findViewById(R.id.user_img);
        nam=(TextView)findViewById(R.id.user_name);

        Image();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });
    }


    public void Image()
    {
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();


        mPDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        //mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Post").child(uid);


        mPDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String name=dataSnapshot.child("name").getValue().toString();

                nam.setText(name);
                image=dataSnapshot.child("thumb_image").getValue().toString();

                Picasso.with(PostActivity.this).load(image).placeholder(R.drawable.user).into(iv);

              //  Picasso.with(PostActivity.this).load(image).placeholder(R.drawable.user).into(iv);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    public void post()
    {
        Calendar calFordDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());



        final String randomName = UUID.randomUUID().toString();
        mPDatabase2 = FirebaseDatabase.getInstance().getReference().child("Post").child(randomName);
        mPDatabase2.child("thumb_image").setValue(image);
        post=pst.getText().toString().trim();
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mPDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        //mPDatabase.child("Post").setValue(post);
//        HashMap postMap = new HashMap();
//        postMap.put("user_id", uid);
//        postMap.put("post", post);
//        mDatabase2.child("Post").setValue(postMap);
        mPDatabase2.child("description").setValue(post);
        mPDatabase2.child("timestamp").setValue(saveCurrentDate);

        mPDatabase2.child("name").setValue(nam.getText());
        mPDatabase2.child("user_id").setValue(uid);
        //mDatabase2.child("user_id").setValue(post);

    }
}
