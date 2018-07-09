package com.example.android.networkcapital;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.sax.StartElementListener;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {

    private Button lgnacnt;
    private EditText lgnEmail;
    private EditText lgnPassword;
    private Button lgnButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private ProgressDialog pd;
    private RadioButton chk1,chk2;
    private DatabaseReference mUserDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out

        }

        setContentView(R.layout.activity_login);
        if (!isTaskRoot())
        {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        firebaseAuth = FirebaseAuth.getInstance();
        lgnEmail = (EditText) findViewById(R.id.login_emailid);
        lgnPassword = (EditText) findViewById(R.id.login_password);
        lgnButton = (Button) findViewById(R.id.loginBtn);
        mUserDatabase=FirebaseDatabase.getInstance().getReference().child("Users");

        TextView text = (TextView) findViewById(R.id.createAccount);
        pd = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        /*
        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){

                    // show password
                    lgnPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    Log.i("checker", "true");
                }

                else{
                    Log.i("checker", "false");

                    // hide password
                    lgnPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });
        */



        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        lgnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signin();
            }
        });
    }

    private void Signin() {
        final String email, pass;

        email = lgnEmail.getText().toString();
        pass = lgnPassword.getText().toString();

        pd.setTitle("Logging In");
        pd.setMessage("Please wait while we check your credentials");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        if (TextUtils.isEmpty(email))
            if (TextUtils.isEmpty(pass)) {
                Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_LONG).show();
                return;
            }
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    pd.dismiss();
                    String current_userId=firebaseAuth.getCurrentUser().getUid();
                    String deviceToken= FirebaseInstanceId.getInstance().getToken();
                    mUserDatabase.child(current_userId).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Intent intent = (new Intent(LoginActivity.this, MainActivity.class));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                    });


                }



                else {
                    pd.hide();
                    Toast.makeText(LoginActivity.this, "Invalid Email/Password", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
}