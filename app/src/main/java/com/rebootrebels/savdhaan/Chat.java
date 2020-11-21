package com.rebootrebels.savdhaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Chat extends AppCompatActivity {
    ImageView wap,insta,t,fb;
    String urlTw="https://twitter.com/";
    String appIs="http://instagram.com/";
    String urlIs="http://instagram.com/_u/{instagram_page_name}";
    String appFb="fb://page/{fb_page_numerical_id}";
    String urlFb="https://www.facebook.com/{fb_page_name}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        wap=(ImageView)findViewById(R.id.wp);
        fb=(ImageView)findViewById(R.id.f);
        insta=(ImageView)findViewById(R.id.ins);
        t=(ImageView)findViewById(R.id.twit);
        wap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                startActivity(i);

            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                    startActivity(i);


            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(urlTw));
                    intent.setPackage("com.twitter.android");
                    startActivity(intent);
                } catch (ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(urlTw)));

                }
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                startActivity(i);

            }
        });
    }
}
