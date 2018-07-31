package com.network.android.networkcapital;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class LikeActivity extends AppCompatActivity {


    private RecyclerView mUsersList;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mUsersDatabase;
    private static DatabaseReference mLoginData;
    private LinearLayoutManager mLayoutManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        mLayoutManager = new LinearLayoutManager(this);


        mUsersList = (RecyclerView) findViewById(R.id.likes_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(mLayoutManager);
        Bundle bundle = getIntent().getExtras();
        String search = bundle.getString("user_id");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_id = mCurrentUser.getUid();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Likes").child(search).child("LIKES");
        mLoginData = FirebaseDatabase.getInstance().getReference().child("Users").child(current_id);



    }

    protected void onStart() {

        super.onStart();

        final FirebaseRecyclerAdapter<HUsers, HUsersViewHolder> freebaseRecyclerAdapter = new FirebaseRecyclerAdapter<HUsers, HUsersViewHolder>(

                HUsers.class,

                R.layout.likes_single,
                HUsersViewHolder.class,
                mUsersDatabase

        ) {
            @Override
            protected void populateViewHolder(final HUsersViewHolder HusersViewHolder, final HUsers Husers, int position) {




                final String user_id = getRef(position).getKey();

                HusersViewHolder.setName(Husers.getName());

                //HusersViewHolder.setPosition(Husers.getPosition());

                HusersViewHolder.setThumb_image(Husers.getThumb_image(), getApplicationContext());


                HusersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent profileIntent = new Intent(LikeActivity.this,ProfileActivity.class);
                        profileIntent.putExtra("user_id", user_id);
                        startActivity(profileIntent);

                    }
                });

            }
        };


        mUsersList.setAdapter(freebaseRecyclerAdapter);

    }


    public static class HUsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public HUsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;


        }

        public void setName(String name) {
            TextView username = (TextView) mView.findViewById(R.id.Lname);
            username.setText(name);

        }

        public void setThumb_image(String thumb_image, Context ctx) {

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.Limage);

            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.user).into(userImageView);


        }

    }}