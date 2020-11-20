package com.rebootrebels.savdhaan;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contacts extends AppCompatActivity {
    EditText e1,e2,e3;
    String p1,p2,p3;
    DatabaseReference reff;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        e1=(EditText)findViewById(R.id.First);
        e2=(EditText)findViewById(R.id.Second);
        e3=(EditText)findViewById(R.id.Third);
        save=(Button)findViewById(R.id.button);
        reff= FirebaseDatabase.getInstance().getReference().child("Member");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p1=e1.getText().toString();
                p2=e2.getText().toString();
                p3=e3.getText().toString();

            }
        });
        
    }
}