package com.network.android.networkcapital;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.network.android.networkcapital.Modules.FriendsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.network.android.networkcapital.Modules.Rating;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference mUserRef,mLUserDatabase,mData;

    private FirebaseUser mCurrentUser,mDataUser;
    private FirebaseAuth mAuth;
    private TextView mdName,mEmail;
    private ImageView mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager pager = (ViewPager) findViewById(R.id.vp_pages);
        PagerAdapter pagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tbl_pages);
        tabLayout.setupWithViewPager(pager);
        mAuth = FirebaseAuth.getInstance();


        mEmail=(TextView)findViewById(R.id.user_emailid);
        mImage=(ImageView)findViewById(R.id.image2);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_id = mCurrentUser.getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(current_id);


        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String name=dataSnapshot.child("First Login").getValue().toString();
                String help=dataSnapshot.child("help").getValue().toString();
                String[] helpList = help.split(",");
                for(String hp : helpList){
                    mLUserDatabase = FirebaseDatabase.getInstance().getReference().child(hp).child(current_id);
                   String nme=dataSnapshot.child("name").getValue().toString();
                    String img=dataSnapshot.child("thumb_image").getValue().toString();
                    String post=dataSnapshot.child("position").getValue().toString();
                    mLUserDatabase.child("name").setValue(nme);
                    mLUserDatabase.child("thumb_image").setValue(img);
                    mLUserDatabase.child("position").setValue(post);

                    //mEmail.setText(Demail);

                    //Picasso.with(MainActivity.this).load(image).placeholder(R.drawable.user).into(mImage);

                }


                if(name.equals("true"))
                {


                    Intent startIntent = new Intent(MainActivity.this, edit_details.class);
                    startActivity(startIntent);
                    mUserRef.child("First Login").setValue("False");
                    finish();
                }

                //  Picasso.with(PostActivity.this).load(image).placeholder(R.drawable.user).into(iv);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeaderView=navigationView.getHeaderView(0);
        mdName = (TextView)navHeaderView.findViewById(R.id.userdata_name);
        mEmail=(TextView)navHeaderView.findViewById(R.id.user_emailid);
        mImage=(ImageView)navHeaderView.findViewById(R.id.image2);

        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
            }

    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mDataUser = FirebaseAuth.getInstance().getCurrentUser();

        final String cur_id = mDataUser.getUid();

        mData= FirebaseDatabase.getInstance().getReference().child("Users").child(cur_id);
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String nme=dataSnapshot.child("name").getValue().toString();
                String emai=dataSnapshot.child("email").getValue().toString();
                String img=dataSnapshot.child("thumb_image").toString();
                mdName.setText(nme);
                mEmail.setText(emai);
                String image=dataSnapshot.child("thumb_image").getValue().toString();
                Picasso.with(MainActivity.this).load(image).placeholder(R.drawable.user).into(mImage);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        if(currentUser == null){

            sendToStart();

        } else {

            mUserRef.child("online").setValue("true");

        }

    }
    protected void onStop() {
        super.onStop();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

        }

    }




    private void sendToStart() {

        Intent startIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startIntent);
        finish();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Intent mainActivity = new Intent(Intent.ACTION_MAIN);
        mainActivity.addCategory(Intent.CATEGORY_HOME);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivity);
        finish();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
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
            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
            FirebaseAuth.getInstance().signOut();
            sendToStart();

        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action}
          if (id == R.id.nav_card) {
            Intent intent2 = new Intent(MainActivity.this, NetworkCard.class);
            startActivity(intent2);

        } else if (id == R.id.nav_edit_details) {
            Intent intent = new Intent(MainActivity.this,edit_details.class);
            startActivity(intent);
        
        } else if (id == R.id.nav_logout) {
            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
            FirebaseAuth.getInstance().signOut();
            sendToStart();

        }
          else if (id == R.id.nav_connectionRequest) {
              Intent intent = new Intent(MainActivity.this, RequestActivity.class);
              startActivity(intent);

          }else if (id == R.id.nav_chat) {
            Intent intent = new Intent(MainActivity.this, ChatListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_friends) {
            Intent intent = new Intent(MainActivity.this, FriendsActivity.class);
            startActivity(intent);
        } else if(id == R.id.nav_ratings){
              Intent intent = new Intent(MainActivity.this, Rating.class);
              startActivity(intent);
          }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new SecondFragment();
                case 1:
                    return new PrimaryFragment();
//                case 2:
//                  return new FriendsFragment();
//                case 3:
//                    return new ChatsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                //
                //Your tab titles
                //
                case 0:return "Newsfeed";
                case 1:return "Connections";
//                case 2:return "Connections";
//                case 3:return "Chats";

                default:return null;
            }
        }
    }
}
