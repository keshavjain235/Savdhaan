package com.rebootrebels.savdhaan;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Feedback extends AppCompatActivity {
    Button sub,ba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        sub=(Button)findViewById(R.id.submit);
        ba=(Button)findViewById(R.id.back);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submit the feedback in database
            }
        });
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Feedback.this,HomeActivity.class));
            }
        });
    }
}