package com.network.android.networkcapital;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CheckActivity extends AppCompatActivity {


    private RecyclerView mUsersList;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mUsersDatabase;
    private static DatabaseReference mLoginData;
    private LinearLayoutManager mLayoutManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        mLayoutManager = new LinearLayoutManager(this);


        mUsersList = (RecyclerView) findViewById(R.id.user_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(mLayoutManager);
        Bundle bundle = getIntent().getExtras();

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_id = mCurrentUser.getUid();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Check");



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

                        Intent profileIntent = new Intent(CheckActivity.this,AcceptActivity.class);
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




                    TextView userNameView = (TextView) mView.findViewById(R.id.help_single_name);
                    userNameView.setText(name);







        }
        public void setPosition(final String position) {



            TextView pose= (TextView) mView.findViewById(R.id.help_single_status);
            pose.setText(position);

        }
        public void setUserImage(final String thumb_image, final Context ctx){


            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.help_single_image);

            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.user).into(userImageView);

        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            FirebaseAuth.getInstance().signOut();
            Intent startIntent = new Intent(CheckActivity.this, LoginActivity.class);
            startActivity(startIntent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}