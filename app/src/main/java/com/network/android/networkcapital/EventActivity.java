package com.network.android.networkcapital;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventActivity extends AppCompatActivity {


    private RecyclerView mUsersList;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mUsersDatabase;
    private static DatabaseReference mLoginData;
    private LinearLayoutManager mLayoutManager;
    String s;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mLayoutManager = new LinearLayoutManager(this);


        mUsersList = (RecyclerView) findViewById(R.id.user_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(mLayoutManager);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();


        String current_id = mCurrentUser.getUid();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Event");

        mLoginData = FirebaseDatabase.getInstance().getReference().child("Users").child(current_id);



    }

    protected void onStart() {

        super.onStart();

        final FirebaseRecyclerAdapter<EUsers, HUsersViewHolder> freebaseRecyclerAdapter = new FirebaseRecyclerAdapter<EUsers, HUsersViewHolder>(

                EUsers.class,

                R.layout.help_user_single,
                HUsersViewHolder.class,
                mUsersDatabase

        ) {

            protected void populateViewHolder(final HUsersViewHolder HusersViewHolder, final EUsers Husers, int position) {




                final String user_id = getRef(position).getKey();

                HusersViewHolder.setDisplayName(Husers.getName());

                HusersViewHolder.setPosition(Husers.getPosition());

                HusersViewHolder.setUserImage(Husers.getThumb_image(), getApplicationContext());


                HusersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent profileIntent = new Intent(EventActivity.this,DetailEvent.class);
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


        public void setDisplayName(String name) {
            TextView username = (TextView) mView.findViewById(R.id.help_single_name);
            username.setText(name);

        }
        public void setUserImage(String thumb_image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.help_single_image);

            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.nc).into(userImageView);


        }
        public void setPosition(String name) {
            TextView username = (TextView) mView.findViewById(R.id.help_single_status);
            username.setText(name);

        }



    }
}