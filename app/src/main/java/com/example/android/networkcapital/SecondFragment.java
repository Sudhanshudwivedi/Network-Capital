package com.example.android.networkcapital;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.networkcapital.Modules.posts;
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

public class SecondFragment extends Fragment {

    ImageView imageView;
    TextView post,timestamp;
    DatabaseReference mSUserDatabase, mSPostDatabase;
    private RecyclerView postList;
    private static Context context = null;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_second, null);
        context = getActivity();

        imageView = (ImageView) v.findViewById(R.id.user_image);
        post = (TextView) v.findViewById(R.id.post);
        //timestamp=(TextView)v.findViewById(R.id.time);

        postList = (RecyclerView) v.findViewById(R.id.post_recycler);
        postList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        postList.setLayoutManager(linearLayoutManager);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        mSPostDatabase = FirebaseDatabase.getInstance().getReference().child("Post");
        mSPostDatabase.keepSynced(true);

        display_user_post();

        Image();



        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),PostActivity.class );
                startActivity(i);
            }
        });

        return v;
    }

    public void display_user_post() {


        FirebaseRecyclerAdapter<posts, postsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<posts, postsViewHolder>(
                posts.class,
                R.layout.posts_layout,
                postsViewHolder.class,
                mSPostDatabase
        ) {


            protected void populateViewHolder(postsViewHolder viewHolder, posts model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setTimestamp(model.getTimestamp());
                viewHolder.setThumb_image(model.getThumb_image(), getContext());
                final String user_id = getRef(position).getKey();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getContext(), CommentActivity.class);
                        intent.putExtra("user_id", user_id);

                        startActivity(intent);



                    }
                });
            }
        };

        postList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class postsViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public postsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDescription(String description) {
            TextView desc = (TextView) mView.findViewById(R.id.post_desc);
            desc.setText(description);
        }
        public void setTimestamp(String timestamp) {
            TextView desc = (TextView) mView.findViewById(R.id.time);

            desc.setText(timestamp);


        }

        public void setName(String name) {
            TextView username = (TextView) mView.findViewById(R.id.post_users_name);
            username.setText(name);

        }
        public void setThumb_image(String thumb_image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.post_profile_img);

            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.user).into(userImageView);

        }


    }


    public void Image()
    {
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();


        mSUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
//        mPostDatabase = FirebaseDatabase.getInstance().getReference().child("Post").child(uid);


        mSUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String image=dataSnapshot.child("thumb_image").getValue().toString();
                Context c = getActivity().getApplicationContext();
                Picasso.with(c).load(image).placeholder(R.drawable.user).into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}