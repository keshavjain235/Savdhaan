package com.rebootrebels.savdhaan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        TextView textView=(TextView)findViewById(R.id.not);
        String msg=getIntent().getStringExtra("Message");
        textView.setText(msg);
    }
}
