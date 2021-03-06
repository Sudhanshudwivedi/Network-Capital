package com.network.android.networkcapital;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText user, email, pass, cpass,positon,location,education,wrkexperience,help,looking;
    String user1, email1, pass1, cpass1,pos,loc,edu,wrk,hel,look;
    Button btn;
    TextView login;
    FirebaseAuth mAuth;
    CheckBox mcbshowpass;

    Button card_btn;




    private ProgressDialog mProgress;
    private DatabaseReference mRDatabase,mCheckDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

//        positon = (EditText) findViewById(R.id.positon);
//        location = (EditText) findViewById(R.id.location);
//        education = (EditText) findViewById(R.id.education);
//        education = (EditText) findViewById(R.id.education);
//        wrkexperience = (EditText) findViewById(R.id.wrkexperience);
//        help = (EditText) findViewById(R.id.help);
//        looking = (EditText) findViewById(R.id.looking);
        user = (EditText) findViewById(R.id.fullName);
        email = (EditText) findViewById(R.id.userEmailId);
        pass = (EditText) findViewById(R.id.password);
        cpass = (EditText) findViewById(R.id.confirmPassword);
        btn = (Button) findViewById(R.id.signUpBtn);
        login = (TextView) findViewById(R.id.already_user);

        mcbshowpass = (CheckBox) findViewById(R.id.cbShowPwd);

//        card_btn = (Button) findViewById(R.id.network_btn);


        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        mcbshowpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    cpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    cpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        /*card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, edit_details.class);
                startActivity(intent);
            }
        });*/


    }

    public void register() {
        initialize();
    }

    public void initialize() {
        user1 = user.getText().toString().trim();
        email1 = email.getText().toString().trim();
        pass1 = pass.getText().toString().trim();
        cpass1 = cpass.getText().toString().trim();
//        hel=help.getText().toString().trim();
//        look=looking.getText().toString().trim();
//        pos=positon.getText().toString().trim();
//        loc=location.getText().toString().trim();
//        edu=education.getText().toString().trim();
//        wrk=wrkexperience.getText().toString().trim();


        if (!validate()) {
            Toast.makeText(this, "Signup has failed!!!!!", Toast.LENGTH_SHORT).show();
        } else {
            onsignupsucess(user1);
        }
    }

    public boolean validate()
    {
        boolean valid=true;
        if(user1.isEmpty()||user1.length()>32)
        {
            user.setError("Please enter Valid Username!!");
            valid=false;
        }
        if(email1.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email1).matches())
        {
            email.setError("Please enter valid email Address!!");
            valid=false;
        }
        if(pass1.length()<8)
        {
            pass.setError("Password must be of minimum 8 characters!!");
            valid=false;
        }
        if(!cpass1.matches(pass1))
        {
            cpass.setError("Confirm Password must match with Password!!!!");
            valid=false;
        }

        return valid;
    }

    public void onsignupsucess(final String user1) {


        mProgress.setTitle("Registering User");
        mProgress.setMessage("Please wait while we create your account !");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();
        mAuth.createUserWithEmailAndPassword(email1, pass1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                    String uid=current_user.getUid();
                    String deviceToken= FirebaseInstanceId.getInstance().getToken();
                    mRDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                    mCheckDatabase= FirebaseDatabase.getInstance().getReference().child("Check").child(uid);

                    HashMap<String,String> userMap=new HashMap<>();

                    userMap.put("name",user1);
                    userMap.put("email",email1);
                    userMap.put("help"," ");
                    userMap.put("look"," ");
                    userMap.put("education"," ");
                    userMap.put("work"," ");
                    userMap.put("location"," ");
                    userMap.put("position"," ");
                    userMap.put("thumb_image","default");
                    userMap.put("rating","0");
                    userMap.put("TrustScore","0");
                    userMap.put("count","0");
                    //userMap.put("Verified","False");
                    //userMap.put("Reject","0");
                    //userMap.put("Card","False");
                    userMap.put("Card","True");
                    userMap.put("Verified","1");
                    userMap.put("Reject","0");


                    userMap.put("device_token",deviceToken);
                    String details_fill = "true";
                    userMap.put("First Login",details_fill);
                    mCheckDatabase.setValue(userMap);

                    mRDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(RegisterActivity.this, "Registration Sucessful", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(intent);

                            mProgress.dismiss();
                            finish();


                        }
                    });




                } else {
                    mProgress.dismiss();

                    Toast.makeText(RegisterActivity.this, "Unsucessful", Toast.LENGTH_LONG).show();
                }
            }
        });

    }



}


