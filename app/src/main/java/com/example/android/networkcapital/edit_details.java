package com.example.android.networkcapital;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class edit_details extends AppCompatActivity {

    private EditText loc, role, company, degree, inst, cls, wr, wc, h1, h2, h3, l1, l2, l3, s1, s2, s3;
    private DatabaseReference mDatabase;
    private Button btn;
    FirebaseAuth mAuth;
    String RegisteredUserID;
    private ImageView mDisplayImage;
    private StorageReference mImageStorage;
    private Button mImagebtn;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog mProgressDialog;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mUserDatabase;

    private Uri mainImageURI = null;
    private static final int GALLERY_PICK = 1;

    String lo, ro, co, de, in, cl, wor, woc, ho1, ho2, ho3, lo1, lo2, lo3, so1, so2, so3;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

       firebaseAuth= FirebaseAuth.getInstance();
       storageReference=FirebaseStorage.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mImageStorage= FirebaseStorage.getInstance().getReference();

        mImagebtn = (Button) findViewById(R.id.upload);
        mDisplayImage = (ImageView) findViewById(R.id.image2);
        loc = (EditText) findViewById(R.id.loca);
        role = (EditText) findViewById(R.id.edit_job);
        company = (EditText) findViewById(R.id.edit_job1);
        degree = (EditText) findViewById(R.id.edit_edu);
        inst = (EditText) findViewById(R.id.edit_edu1);
        cls = (EditText) findViewById(R.id.edit_edu2);
        wr = (EditText) findViewById(R.id.edit_work1);
        wc = (EditText) findViewById(R.id.edit_work2);
        h1 = (EditText) findViewById(R.id.edit_help);
        h2 = (EditText) findViewById(R.id.edit_help1);
        h3 = (EditText) findViewById(R.id.edit_help2);
        l1 = (EditText) findViewById(R.id.edit_looking);
        l2 = (EditText) findViewById(R.id.edit_looking1);
        l3 = (EditText) findViewById(R.id.edit_looking2);
        s1 = (EditText) findViewById(R.id.edit_goals);
        s2 = (EditText) findViewById(R.id.edit_goals1);
        s3 = (EditText) findViewById(R.id.edit_goals2);


        mImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);
            }
        });


        btn = (Button) findViewById(R.id.save_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }



    public void save() {
        lo = loc.getText().toString().trim();
        ro = role.getText().toString().trim();
        co = company.getText().toString().trim();
        de = degree.getText().toString().trim();
        in = inst.getText().toString().trim();
        cl = cls.getText().toString().trim();
        wor = wr.getText().toString().trim();
        woc = wc.getText().toString().trim();
        ho1 = h1.getText().toString().trim();
        ho2 = h2.getText().toString().trim();
        ho3 = h3.getText().toString().trim();
        lo1 = l1.getText().toString().trim();
        lo2 = l2.getText().toString().trim();
        lo3 = l3.getText().toString().trim();
        so1 = s1.getText().toString().trim();
        so2 = s2.getText().toString().trim();
        so3 = s3.getText().toString().trim();

        ro = ro + "," + co;
        de = de + " " + in;
        wor = wor + "-" + woc;
        ho1 = ho1 + "," + ho2 + "," + ho3;
        lo1 = lo1 + "," + lo2 + "," + lo3;


        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);


        mDatabase.child("location").setValue(lo);
        mDatabase.child("position").setValue(ro);
        mDatabase.child("work").setValue(wor);
        mDatabase.child("help").setValue(ho1);
        mDatabase.child("look").setValue(lo1);
        mDatabase.child("education").setValue(de);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK){

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .setMinCropWindowSize(500, 500)
                    .start(this);

            //Toast.makeText(SettingsActivity.this, imageUri, Toast.LENGTH_LONG).show();

        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {


                mProgressDialog = new ProgressDialog(edit_details.this);
                mProgressDialog.setTitle("Uploading Image...");
                mProgressDialog.setMessage("Please wait while we upload and process the image.");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();


                Uri resultUri = result.getUri();

                File thumb_filePath = new File(resultUri.getPath());

                String current_user_id = mCurrentUser.getUid();


                Bitmap thumb_bitmap = new Compressor(this)
                        .setMaxWidth(200)
                        .setMaxHeight(200)
                        .setQuality(75)

                        .compressToBitmap(thumb_filePath);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] thumb_byte = baos.toByteArray();


                StorageReference filepath = mImageStorage.child("profile_images").child(current_user_id + ".jpg");
                //final StorageReference thumb_filepath = mImageStorage.child("profile_images").child("thumbs").child(current_user_id + ".jpg");



                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful()){

                            final String download_url = task.getResult().getDownloadUrl().toString();

                          //  UploadTask uploadTask = thumb_filepath.putBytes(thumb_byte);
                           /// uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {

                             //   public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {


                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();


                            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                                    mUserDatabase.child("image").setValue(download_url);

                                    mUserDatabase.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            String image=dataSnapshot.child("image").getValue().toString();
                                            Picasso.with(edit_details.this).load(image).placeholder(R.drawable.user).into(mDisplayImage);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    mProgressDialog.dismiss();







                                }
                            };






                });



            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }


    }
}
