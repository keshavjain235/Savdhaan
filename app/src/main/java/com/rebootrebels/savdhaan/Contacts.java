package com.rebootrebels.savdhaan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Contacts extends AppCompatActivity {
    private static String p1, p2, p3;
    EditText e1,e2,e3;
     DatabaseReference reff;
     Button save;
    String currentuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        e1=(EditText)findViewById(R.id.First);
        e2=(EditText)findViewById(R.id.Second);
        e3=(EditText)findViewById(R.id.Third);
        save=(Button)findViewById(R.id.button);
        reff= FirebaseDatabase.getInstance().getReference().child("Members");

       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               p1 = e1.getText().toString();
               p2 = e2.getText().toString();
               p3 = e3.getText().toString();

               if(p1.length() < 10 || p2.length() < 10 || p3.length() < 10) {
                   Toast.makeText(Contacts.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
               }
               else {
                   reff.child(currentuid).child("mem1").setValue(p1);
                   reff.child(currentuid).child("mem2").setValue(p2);
                   reff.child(currentuid).child("mem3").setValue(p3);
               }
           }
       });

    }
    public static String getnum1(){
        return p1;
    }
    public static String getnum2(){
        return p2;
    }
    public static String getnum3(){
        return p3;
    }
}

