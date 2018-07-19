package com.example.android.networkcapital;

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

public class HelpUsers extends AppCompatActivity {


    private RecyclerView mUsersList;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mUsersDatabase;
    private static DatabaseReference mLoginData;
    private LinearLayoutManager mLayoutManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_users);

        mLayoutManager = new LinearLayoutManager(this);


        mUsersList = (RecyclerView) findViewById(R.id.user_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(mLayoutManager);
        Bundle bundle = getIntent().getExtras();
        String search = bundle.getString("button_select_text");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_id = mCurrentUser.getUid();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child(search);
        mLoginData = FirebaseDatabase.getInstance().getReference().child("Users").child(current_id);



    }

    protected void onStart() {

        super.onStart();

        final FirebaseRecyclerAdapter<HUsers, HUsersViewHolder> freebaseRecyclerAdapter = new FirebaseRecyclerAdapter<HUsers, HUsersViewHolder>(

                HUsers.class,

                R.layout.help_user_single,
                HUsersViewHolder.class,
                mUsersDatabase

        ) {
            @Override
            protected void populateViewHolder(final HUsersViewHolder HusersViewHolder, final HUsers Husers, int position) {




                final String user_id = getRef(position).getKey();

                HusersViewHolder.setDisplayName(Husers.getName());

                HusersViewHolder.setPosition(Husers.getPosition());

                HusersViewHolder.setUserImage(Husers.getThumb_image(), getApplicationContext());


                HusersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent profileIntent = new Intent(HelpUsers.this,ProfileActivity.class);
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

        public void setDisplayName(final String name) {

            mLoginData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String Name=dataSnapshot.child("name").getValue().toString();
                    if(Name.equals(name)){
                        ////TextView userNameView = (TextView) mView.findViewById(R.id.help_single_name);
                        //userNameView.setText(null);
                       // mView.setVisibility(View.);



                    }
                    else
                    {
                        TextView userNameView = (TextView) mView.findViewById(R.id.help_single_name);
                        userNameView.setText(name);

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        public void setPosition(final String position) {
            mLoginData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String Position=dataSnapshot.child("position").getValue().toString();
                    if(Position.equals(position)){
                        TextView pose= (TextView) mView.findViewById(R.id.help_single_status);
                        //pose.setText(null);



                    }
                    else
                    {
                        TextView pose= (TextView) mView.findViewById(R.id.help_single_status);
                        pose.setText(position);

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            TextView pose= (TextView) mView.findViewById(R.id.help_single_status);
            pose.setText(position);

        }
        public void setUserImage(final String thumb_image, final Context ctx){
            mLoginData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String Image=dataSnapshot.child("thumb_image").getValue().toString();
                    if(Image.equals(thumb_image)){
                        CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.help_single_image);
                       //userImageView.setImageDrawable(null);

                        // Picasso.with(ctx).load(null).placeholder(R.drawable.user).into(userImageView);



                    }
                    else
                    {
                        CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.help_single_image);

                        Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.user).into(userImageView);

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.help_single_image);

            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.user).into(userImageView);

        }
    }
}