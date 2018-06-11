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

public class PostActivity extends AppCompatActivity {


    private EditText pst;
    private TextView nam;
    private Button btn;
    private ImageView iv;

    String post;
    private DatabaseReference mDatabase;

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


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String name=dataSnapshot.child("name").getValue().toString();

                nam.setText(name);
                String image=dataSnapshot.child("image").getValue().toString();
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
        post=pst.getText().toString().trim();
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        mDatabase.child("Post").setValue(post);
    }
}
