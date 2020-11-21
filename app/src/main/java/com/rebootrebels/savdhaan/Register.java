package com.rebootrebels.savdhaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mEmail,mPassword,mUsername,mPhone;
        Button mSignupbtn;
        TextView mloginbtn;
        ProgressBar pbar;
        FirebaseFirestore fstore;
        String userID;
        private FirebaseAuth fAuth;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
             mEmail=(EditText)findViewById(R.id.Second);
            mPassword=(EditText)findViewById(R.id.Third);
            mSignupbtn=(Button)findViewById(R.id.button2);
            mloginbtn=(TextView)findViewById(R.id.textView7);
            pbar=(ProgressBar)findViewById(R.id.progressBar);
            fAuth=FirebaseAuth.getInstance();
            mUsername=(EditText)findViewById(R.id.First);
            mPhone=(EditText)findViewById(R.id.editText8);
            fstore=FirebaseFirestore.getInstance();



            if(fAuth.getCurrentUser()!=null)
            {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();

            }

            mSignupbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String email = mEmail.getText().toString();
                    String password = mPassword.getText().toString();
                    final String username=mUsername.getText().toString();
                    final String phone=mPhone.getText().toString();
                    if (email.isEmpty()){
                        mEmail.setError("Email is Required");
                        mEmail.requestFocus();
                        return;
                    }
                    if (password.isEmpty()) {
                        mEmail.setError("Password is Required");
                        mPassword.requestFocus();
                        return;
                    }
                    if (password.length() < 6) {
                        mPassword.setError("Password Must be >= 6 characters");
                        return;
                    }
                    pbar.setVisibility(View.VISIBLE);

                    //register the user in firebase
                    if (!(email.isEmpty() && password.isEmpty())) {
                        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    //
                                    FirebaseUser fuser=fAuth.getCurrentUser();
                                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                          Toast.makeText(Register.this,"verification email has been sent",Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Register.this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    Toast.makeText(Register.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                                    userID=fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference=fstore.collection("users").document(userID);
                                    Map<String,Object> user=new HashMap<>();
                                    user.put("uname",username);
                                    user.put("email",email);
                                    user.put("phone",phone);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG,"onSuccess :+ user Profile is created for " + userID);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG,"onFailure :" + e.toString());
                                        }
                                    });
                                    startActivity(new Intent(Register.this,HomeActivity.class));

                                } else {
                                    Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(Register.this, "Error in connection", Toast.LENGTH_LONG).show();
                    }
                }
            });
            mloginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Register.this,LoginActivity.class));
                }
            });
        }
    }


