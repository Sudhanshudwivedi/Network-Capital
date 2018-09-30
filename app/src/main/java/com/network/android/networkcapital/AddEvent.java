package com.network.android.networkcapital;

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
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class AddEvent extends AppCompatActivity {

    private ImageView mEventImage;
    private EditText Eday,Etime,Edate,Ename,Edetails,Elink,Elocation;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog mProgressDialog;
    private FirebaseUser mCurrentUser;
    private StorageReference mImageStorage;
    private DatabaseReference mEvent;
    private Button mbtn;
    private String uid;

    private Uri mainImageURI = null;
    private static final int GALLERY_PICK = 1;
    private String randomName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        firebaseAuth= FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mImageStorage= FirebaseStorage.getInstance().getReference();
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        uid = current_user.getUid();
        Eday=(EditText)findViewById(R.id.day);
        Etime=(EditText)findViewById(R.id.time);
        Edate=(EditText)findViewById(R.id.date);
        Ename=(EditText)findViewById(R.id.Ename);
        Edetails=(EditText)findViewById(R.id.description);
        Elink=(EditText)findViewById(R.id.ticket);
        Elocation=(EditText)findViewById(R.id.Elocation);
        mEventImage=(ImageView)findViewById(R.id.EventImage);
        randomName = UUID.randomUUID().toString();

        mbtn=(Button)findViewById(R.id.Esubmit);
        mEventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);
            }
        });

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {

       String name=Ename.getText().toString().trim();
        String location=Elocation.getText().toString().trim();
        String day=Eday.getText().toString().trim();
        String date=Edate.getText().toString().trim();
        String time=Etime.getText().toString().trim();
        String detail=Edetails.getText().toString().trim();
        String link=Elink.getText().toString().trim();

        String position=day+","+date;
        mEvent = FirebaseDatabase.getInstance().getReference().child("Event").child(randomName);


        mEvent.child("name").setValue(name);
        mEvent.child("location").setValue(location);
        mEvent.child("day").setValue(day);
        mEvent.child("date").setValue(date);
        mEvent.child("time").setValue(time);
        mEvent.child("detail").setValue(detail);
        mEvent.child("link").setValue(link);
        mEvent.child("position").setValue(position);




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


                mProgressDialog = new ProgressDialog(AddEvent.this);
                mProgressDialog.setTitle("Uploading Image...");
                mProgressDialog.setMessage("Please wait while we upload and process the image.");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();


                Uri resultUri = result.getUri();

                File thumb_filePath = new File(resultUri.getPath());

                String current_user_id = mCurrentUser.getUid();


                Bitmap thumb_bitmap = new Compressor(this)
                        .setMaxWidth(1500)
                        .setMaxHeight(1500)
                        .setQuality(275)

                        .compressToBitmap(thumb_filePath);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] thumb_byte = baos.toByteArray();


                StorageReference filepath = mImageStorage.child("Event_images").child(current_user_id + ".jpg");
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


                            mEvent = FirebaseDatabase.getInstance().getReference().child("Event").child(randomName);


                            mEvent.child("thumb_image").setValue(download_url);

                            //mEUserDatabase2 = FirebaseDatabase.getInstance().getReference().child("Post").child(uid);
                            // mEUserDatabase2.child("thumb_image").setValue(download_url);



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
