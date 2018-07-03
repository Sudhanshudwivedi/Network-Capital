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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HelpUsers extends AppCompatActivity {


    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase;
    private LinearLayoutManager mLayoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_users);

        mLayoutManager = new LinearLayoutManager(this);


        mUsersList = (RecyclerView) findViewById(R.id.user_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(mLayoutManager);
        Bundle bundle = getIntent().getExtras();
        String search = bundle.getString("Search");
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child(search);

       Toast.makeText(HelpUsers.this, search, Toast.LENGTH_LONG).show();


    }

    protected void onStart() {

        super.onStart();

        FirebaseRecyclerAdapter<HUsers, HUsersViewHolder> freebaseRecyclerAdapter = new FirebaseRecyclerAdapter<HUsers, HUsersViewHolder>(

                HUsers.class,

                R.layout.users_single_layout,
                HUsersViewHolder.class,
                mUsersDatabase

        ) {
            @Override
            protected void populateViewHolder(HUsersViewHolder HusersViewHolder, HUsers Husers, int position) {

                HusersViewHolder.setDisplayName(Husers.getName());

                HusersViewHolder.setUserImage(Husers.getThumb_image(), getApplicationContext());
                final String user_id = getRef(position).getKey();

                HusersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Intent profileIntent = new Intent(List.this,PatientActivity.class);
                        //profileIntent.putExtra("user_id", user_id);
                        //startActivity(profileIntent);

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

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);

        }
        public void setUserImage(String thumb_image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);

            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.user).into(userImageView);

        }
    }
}