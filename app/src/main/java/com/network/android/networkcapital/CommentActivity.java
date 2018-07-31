package com.network.android.networkcapital;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.network.android.networkcapital.Modules.posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.googlecode.mp4parser.authoring.Edit;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends AppCompatActivity {

    private ImageButton imgbtn;
    private RecyclerView mRecyclerView;
    private EditText mText;
    private ProgressDialog pd;
    private FirebaseAuth firebaseAuth;
    private String saveCurrentDate;
    private String user_id,uid,uNid,Puserr_id;
    private int countCmnt;
    private DatabaseReference mNcDatabase;

    private DatabaseReference mCDatabase,mCUserDatabase,mCUsersDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mRecyclerView=(RecyclerView)findViewById(R.id.Clist);
        firebaseAuth = FirebaseAuth.getInstance();
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
        uid=current_user.getUid();
        user_id = getIntent().getStringExtra("user_id");
        Puserr_id=getIntent().getStringExtra("user_id");
        mCUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Post").child(user_id).child("Comment");
        Query query =  mCUsersDatabase.orderByChild("time");
        display_user_post(query);

        mText=(EditText)findViewById(R.id.comment);
        imgbtn=(ImageButton)findViewById(R.id.btn);
        pd = new ProgressDialog(this);

        Calendar calFordDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());


        //mText.setText(user_id);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setTitle("Logging In");
                pd.setMessage("Please wait while we check your credentials");
                pd.setCanceledOnTouchOutside(false);
                pd.show();
                final String randomName = UUID.randomUUID().toString();
                mCDatabase = FirebaseDatabase.getInstance().getReference().child("Post").child(user_id).child("Comment").child(randomName);
                final String desc = mText.getText().toString();
                mCUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                mCUserDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name=dataSnapshot.child("name").getValue().toString();

                        String image=dataSnapshot.child("thumb_image").getValue().toString();


                        mCDatabase.child("uid").setValue(uid);
                        mCDatabase.child("name").setValue(name);
                        mCDatabase.child("thumb_image").setValue(image);
                        mCDatabase.child("timestamp").setValue(saveCurrentDate);
                        mCDatabase.child("comment").setValue(desc);
                        mCDatabase.child("time").setValue(ServerValue.TIMESTAMP);
                        pd.hide();





                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                mText.getText().clear();



            }

        });




    }

    public void display_user_post(Query query) {


        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(

                Users.class,
                R.layout.comment_layout,
                UsersViewHolder.class,
                query

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder usersViewHolder, Users users, final int position) {

                usersViewHolder.setName(users.getName());
                usersViewHolder.setComment(users.getComment());
                usersViewHolder.setTimestamp(users.getTimestamp());
                usersViewHolder.setThumb_image(users.getThumb_image(), getApplicationContext());


                final String user_id = getRef(position).getKey();
                mNcDatabase=FirebaseDatabase.getInstance().getReference().child("Post").child(Puserr_id).child("Comment").child(user_id);
                mNcDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        uNid=dataSnapshot.child("uid").getValue().toString();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        Intent profileIntent = new Intent(CommentActivity.this, CommentNC.class);
                        profileIntent.putExtra("Cuser_id", uNid);
                        startActivity(profileIntent);

                    }
                });

            }
        };


        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }


    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setName(String name){

            TextView userNameView = (TextView) mView.findViewById(R.id.cmt_users_name);
            userNameView.setText(name);

        }

        public void setTimestamp(String timestamp){

            TextView userStatusView = (TextView) mView.findViewById(R.id.ctime);
            userStatusView.setText(timestamp);


        }
        public void setComment(String comment){

            TextView userStatusView = (TextView) mView.findViewById(R.id.cmt_desc);
            userStatusView.setText(comment);


        }


        public void setThumb_image(String thumb_image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.cmt_profile_img);

            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.user).into(userImageView);

        }


    }
}