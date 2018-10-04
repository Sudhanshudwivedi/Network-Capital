package com.network.android.networkcapital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailEvent extends AppCompatActivity {

    private ImageView DtImage;
    private DatabaseReference EDetails;
    private EditText Ename,Eloc,Eday,Edate,Etime;
    private FirebaseAuth Emauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        final String current_id = getIntent().getStringExtra("user_id");
        //Toast.makeText(DetailEvent.this,current_id,Toast.LENGTH_LONG).show();

        DtImage=(ImageView)findViewById(R.id.EvImage);

        EDetails = FirebaseDatabase.getInstance().getReference().child("Event").child(current_id);

        EDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image=dataSnapshot.child("thumb_image").getValue().toString();
                Picasso.with(DetailEvent.this).load(image).placeholder(R.drawable.user).into(DtImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
