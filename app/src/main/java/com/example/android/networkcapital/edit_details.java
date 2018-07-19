package com.example.android.networkcapital;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class edit_details extends AppCompatActivity {

    private EditText loc, role, company, degree, inst, cls, wr, wc, h1, h2, h3, l1, l2, l3, s1, s2, s3;
    private DatabaseReference mEDatabase;
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
    private DatabaseReference mEUserDatabase;
    private DatabaseReference mEUserDatabase2;
    private DatabaseReference mLUserDatabase;
    private DatabaseReference mEHelpDatabase;
    private String uid;

    RelativeLayout rl1,rl2,rl3;
    ImageView mOptions,mOptions2;
    TextView mItemSelected,mItemSelected2,mItemSelected3;
    String[] listItems;
    String [] goalItems;
    boolean[] checkedItems;
    boolean[] checkedItems2;
    boolean [] checkedItems3;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    ArrayList<Integer> mUserItems2 = new ArrayList<>();
    ArrayList<Integer> mUserItems3 = new ArrayList<>();






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
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        uid = current_user.getUid();

        mOptions = (ImageView) findViewById(R.id.n1);
        mOptions2 = (ImageView) findViewById(R.id.n2);
        mItemSelected = (TextView) findViewById(R.id.help_text);
        mItemSelected2 = (TextView) findViewById(R.id.look_text);
//        mItemSelected3 = (TextView) findViewById(R.id.goal_text);
        rl1 = (RelativeLayout) findViewById(R.id.rl1);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
//        rl3 = (RelativeLayout) findViewById(R.id.rl3);

        mImagebtn = (Button) findViewById(R.id.upload);
        mDisplayImage = (ImageView) findViewById(R.id.image2);
        loc = (EditText) findViewById(R.id.loca);
        role = (EditText) findViewById(R.id.edit_job);
        company = (EditText) findViewById(R.id.edit_job1);
        degree = (EditText) findViewById(R.id.edit_edu);
        inst = (EditText) findViewById(R.id.edit_edu1);
//        cls = (EditText) findViewById(R.id.edit_edu2);
        wr = (EditText) findViewById(R.id.edit_work1);
        wc = (EditText) findViewById(R.id.edit_work2);


        mImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);
            }
        });


        listItems = getResources().getStringArray(R.array.helping_item);
        goalItems = getResources().getStringArray(R.array.goals_item);
        checkedItems = new boolean[listItems.length];
        checkedItems2 = new boolean[listItems.length];
        checkedItems3 = new boolean[goalItems.length];

//        Spannable word = new SpannableString("Your message");
//        word.setSpan(new Background, 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_details.this);
                builder.setTitle("I can help in...");
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            mUserItems.add(position);
                        }else {
                            mUserItems.remove(Integer.valueOf(position));
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {

                            String sd=listItems[mUserItems.get(i)];
                            //store(sd);

                            item = item + listItems[mUserItems.get(i)];



                            if (i != mUserItems.size() - 1) {
                                item = item + ",";
                            }
                        }
                        mItemSelected.setText(item);
                    }
                });

                builder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mItemSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });




        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_details.this);
                builder.setTitle("I am looking for...");
                builder.setMultiChoiceItems(listItems, checkedItems2, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            mUserItems2.add(position);
                        }else {
                            mUserItems2.remove(Integer.valueOf(position));
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems2.size(); i++) {
                            item = item + listItems[mUserItems2.get(i)];
                            if (i != mUserItems2.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mItemSelected2.setText(item);
                    }
                });

                builder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems2.length; i++) {
                            checkedItems2[i] = false;
                            mUserItems2.clear();
                            mItemSelected2.setText("");
                        }
                    }
                });

                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });


//        rl3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(edit_details.this);
//                builder.setTitle("ShortTerm Goals...");
//                builder.setMultiChoiceItems(goalItems, checkedItems3, new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
//                        if(isChecked){
//                            mUserItems3.add(position);
//                        }else {
//                            mUserItems3.remove(Integer.valueOf(position));
//                        }
//                    }
//                });
//
//                builder.setCancelable(false);
//                builder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        String item = "";
//                        for (int i = 0; i < mUserItems3.size(); i++) {
//                            item = item + goalItems[mUserItems3.get(i)];
//                            if (i != mUserItems3.size() - 1) {
//                                item = item + ", ";
//                            }
//                        }
//                        mItemSelected3.setText(item);
//                    }
//                });
//
//                builder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//
//                builder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        for (int i = 0; i < checkedItems3.length; i++) {
//                            checkedItems3[i] = false;
//                            mUserItems3.clear();
//                            mItemSelected3.setText("");
//                        }
//                    }
//                });
//
//                AlertDialog mDialog = builder.create();
//                mDialog.show();
//            }
//        });





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
//        cl = cls.getText().toString().trim();
        wor = wr.getText().toString().trim();
        woc = wc.getText().toString().trim();
        ho1 = mItemSelected.getText().toString().trim();
//        ho2 = h2.getText().toString().trim();
//        ho3 = h3.getText().toString().trim();
        lo1 = mItemSelected2.getText().toString().trim();
//        lo2 = l2.getText().toString().trim();
//        lo3 = l3.getText().toString().trim();
//        so1 = mItemSelected3.getText().toString().trim();
//        so2 = s2.getText().toString().trim();
//        so3 = s3.getText().toString().trim();

//        boolean valid = check(lo,ro,wor,ho1,lo1,de);


        if(!co.isEmpty())
            ro = ro + "," + co;
        if(!in.isEmpty())
            de = de + " " + in;
        if(!woc.isEmpty())
            wor = wor + "-" + woc;


        if(!check(lo,ro,wor,ho1,lo1,de,in,co)){
            Toast.makeText(getApplicationContext(), "Please fill all the details", Toast.LENGTH_SHORT).show();
        }
        else {


            //ho1 = ho1 + "," + ho2 + "," + ho3;
            //lo1 = lo1 + "," + lo2 + "," + lo3;

            if(wor.isEmpty()){
                wor = "No previous work experience.";
            }


            mEDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);


            mEDatabase.child("location").setValue(lo);
            mEDatabase.child("position").setValue(ro);
            mEDatabase.child("work").setValue(wor);
            mEDatabase.child("help").setValue(ho1);
            mEDatabase.child("look").setValue(lo1);
            mEDatabase.child("education").setValue(de);


            Intent intent = new Intent(edit_details.this, MainActivity.class);

            startActivity(intent);
            finish();
        }


    }




    private boolean check(String lo, String ro, String wor, String ho1, String lo1, String de, String in, String co) {

        boolean valid = true;
        if(lo.isEmpty()){
            loc.setError("Please enter your location");
            valid=false;
        }
        if(ro.isEmpty()){
            role.setError("Please enter your current role");
            valid = false;
        }
//        if(wor.isEmpty()){
//            wr.setError("Please enter your work ex.");
//            valid = false;
//        }
//        if(ho1.isEmpty()){
//            mItemSelected.setError("Please select options");
//            valid = false;
//        }
//        if(lo1.isEmpty()){
//            mItemSelected2.setError("Please select options");
//            valid = false;
//        }
        if(de.isEmpty()){
            degree.setError("Please enter your education details");
            valid = false;
        }
        if(in.isEmpty()){
            inst.setError("Please enter your education details");
            valid = false;
        }
        if(co.isEmpty()){
            company.setError("Please enter the company name");
        }

        return valid;

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


                            mEUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            mEUserDatabase.child("thumb_image").setValue(download_url);

                            //mEUserDatabase2 = FirebaseDatabase.getInstance().getReference().child("Post").child(uid);
                            // mEUserDatabase2.child("thumb_image").setValue(download_url);


                            mEUserDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    String image=dataSnapshot.child("thumb_image").getValue().toString();
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