package com.network.android.networkcapital;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.network.android.networkcapital.Modules.UserProfile;
import com.network.android.networkcapital.Modules.posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SecondFragment extends Fragment {

    ImageView imageView;
    TextView post,timestamp;
    DatabaseReference mSUserDatabase;
    DatabaseReference mSPostDatabase;
    DatabaseReference msLikeDatabase,mLikeDatabase;
    DatabaseReference cmnt;
    private RecyclerView postList;
    private static Context context = null;
    private ProgressDialog mProgressDialog;
    Boolean LikeChecker = false;
//    LinearLayout linearLayout;
    String uid;


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
        uid = current_user.getUid();

        mSPostDatabase = FirebaseDatabase.getInstance().getReference().child("Post");
        msLikeDatabase = FirebaseDatabase.getInstance().getReference().child("Likes");



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

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle("Loading Newsfeed");
        mProgressDialog.setMessage("Please wait while we load the user data.");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        Query query =  mSPostDatabase.orderByChild("time");


        FirebaseRecyclerAdapter<posts, postsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<posts, postsViewHolder>(
                posts.class,
                R.layout.posts_layout,
                postsViewHolder.class,
                query
        ) {


            protected void populateViewHolder(postsViewHolder viewHolder, posts model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setTimestamp(model.getTimestamp());
                viewHolder.setThumb_image(model.getThumb_image(), getContext());

                final String user_id = getRef(position).getKey();

                viewHolder.setLikeButtonStatus(user_id);
                viewHolder.setCommentCount(user_id);


                viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), UserProfile.class);
                        i.putExtra("Puser_id", user_id);
                        //Toast.makeText(getContext(), user_id.toString(), Toast.LENGTH_LONG).show();
                        //System.out.print("String user id" + user_id.toString());
                        startActivity(i);
                    }
                });


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getContext(), CommentActivity.class);
                        intent.putExtra("user_id", user_id);

                        startActivity(intent);



                    }
                });

                viewHolder.Likepostbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LikeChecker = true;
                        msLikeDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(LikeChecker.equals(true)){
                                    if(dataSnapshot.child(user_id).child("LIKES").hasChild(uid)){

                                        msLikeDatabase.child(user_id).child("LIKES").child(uid).child("bool").removeValue();
                                        msLikeDatabase.child(user_id).child("LIKES").child(uid).child("name").removeValue();
                                        msLikeDatabase.child(user_id).child("LIKES").child(uid).child("thumb_image").removeValue();
                                        LikeChecker = false;

                                    }else{
                                        mLikeDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                                        mLikeDatabase.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                String image=dataSnapshot.child("thumb_image").getValue().toString();
                                                String name=dataSnapshot.child("name").getValue().toString();

                                                msLikeDatabase.child(user_id).child("LIKES").child(uid).child("name").setValue(name);
                                                msLikeDatabase.child(user_id).child("LIKES").child(uid).child("thumb_image").setValue(image);

                                                msLikeDatabase.child(user_id).child("LIKES").child(uid).child("bool").setValue(true);


                                                LikeChecker = false;

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        };

        postList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class postsViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageButton Likepostbutton;
        TextView Commentbutton;
        TextView Displaynofolikes;
        TextView CommentLikes;
        LinearLayout linearLayout;

        int countlikes;
        int commentCount;
        String currentuserId;
        DatabaseReference likesref;
        DatabaseReference cmnt;


        public postsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            Likepostbutton = (ImageButton) mView.findViewById(R.id.dislike);
            Commentbutton = (TextView) mView.findViewById(R.id.comment);
            Displaynofolikes = (TextView) mView.findViewById(R.id.display_no_of_likes);
            CommentLikes=(TextView)mView.findViewById(R.id.comment);


            linearLayout = (LinearLayout) mView.findViewById(R.id.user_name);

            likesref = FirebaseDatabase.getInstance().getReference().child("Likes");
            currentuserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            cmnt=FirebaseDatabase.getInstance().getReference().child("Post");

        }
        public void setCommentCount(final String PostKey)
        {
            final DatabaseReference comment = cmnt.child(PostKey).child("Comment");
            comment.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    commentCount = (int) dataSnapshot.getChildrenCount();
                    CommentLikes.setText(Integer.toString(commentCount) + (" Comments"));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        public void setLikeButtonStatus(final String PostKey){
            likesref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(PostKey).child("LIKES").hasChild(currentuserId)){
                        countlikes = (int) dataSnapshot.child(PostKey).child("LIKES").getChildrenCount();
                        Likepostbutton.setImageResource(R.mipmap.likepost);
                        Likepostbutton.setColorFilter(Likepostbutton.getContext().getResources().getColor(R.color.background_color), PorterDuff.Mode.SRC_ATOP);

                        Displaynofolikes.setText(Integer.toString(countlikes) + (" Likes"));
                        Displaynofolikes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mView.getContext(), LikeActivity.class);
                                intent.putExtra("user_id", PostKey);

                                mView.getContext().startActivity(intent);

                            }
                        });
                    }
                    else {
                        countlikes = (int) dataSnapshot.child(PostKey).child("LIKES").getChildrenCount();
                        Likepostbutton.setImageResource(R.mipmap.dislike);
                        Displaynofolikes.setText(Integer.toString(countlikes) + (" Likes"));
                        Displaynofolikes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mView.getContext(), LikeActivity.class);
                                intent.putExtra("user_id", PostKey);

                                mView.getContext().startActivity(intent);

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
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
                if(!image.equals("default")) {

                    Picasso.with(context).load(image).placeholder(R.drawable.user).into(imageView);
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}