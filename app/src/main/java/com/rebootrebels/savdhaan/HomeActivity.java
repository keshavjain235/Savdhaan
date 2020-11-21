package com.rebootrebels.savdhaan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1 ;
    private DrawerLayout drawer;
    View mview;
    private ImageView panic;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    TextView hdemail,hduname;
    String text,userID;
    DatabaseReference reff;

    TextView pro,loca,contacts,send,msg,call,home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Members");
        Toolbar toolbar = findViewById(R.id.toolbar);
        panic=(ImageView)findViewById(R.id.panic);
        send= (TextView) findViewById(R.id.Send);
        loca=(TextView)findViewById(R.id.location);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        contacts=(TextView)findViewById(R.id.con);
        pro=(TextView)findViewById(R.id.profile);
        call=(TextView)findViewById(R.id.call);
        msg=(TextView)findViewById(R.id.ip);
        home=(TextView)findViewById(R.id.help);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mview=navigationView.getHeaderView(0);
        hdemail=(TextView)mview.findViewById(R.id.email1);
        hduname=(TextView)mview.findViewById(R.id.uname1);
        userID=fAuth.getCurrentUser().getUid();
        final DocumentReference documentReference=fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {

                hdemail.setText(documentSnapshot.getString("email"));
                hduname.setText(documentSnapshot.getString("uname"));

            }
        });
        final MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.alarm);
        panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sending();
                makeCall();
            }
        });
        setSupportActionBar(toolbar);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,Help.class));
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        loca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,MapsActivity.class));
            }
        });
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(HomeActivity.this,Contacts.class));
            }
        });
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.log:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
            case R.id.mic:
                speak();
                break;
            case R.id.notification:
                getnotification();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void getnotification() {
        String message="Hello,I am From Savdhhaan";
        NotificationCompat.Builder builder=new NotificationCompat.Builder(HomeActivity.this).setSmallIcon(R.drawable.ic_chat_black_24dp)
                .setContentTitle("New Notification")
                .setContentText(message).setAutoCancel(true);
        Intent intent=new Intent(HomeActivity.this,Notification.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Message",message);
        PendingIntent pendingIntent=PendingIntent.getActivity(HomeActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hello, Savdhaan is here to help you");

        try{
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }catch (Exception e){
            Toast.makeText(HomeActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_SPEECH_INPUT && resultCode==RESULT_OK)
        {
            ArrayList<String> matrix=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
           msg.setText(matrix.get(0).toString());

            if(msg.getText().toString()=="call" || msg.getText().toString()=="CALL")
            {
                makeCall();
            }
            else if (msg.getText().toString()=="send" || msg.getText().toString()=="SEND")
            {
                sending();
            }
            msg.clearComposingText();


        }
        
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void makeCall()
    {   String p11="123456789";
        Intent intent1=new Intent(Intent.ACTION_CALL);
        intent1.setData(Uri.parse("tel:"+p11));
        if(ActivityCompat.checkSelfPermission(this , Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        startActivity(intent1);
    }
    public void sending()
    {
        String p1 = Contacts.getnum1();
        String p2 = Contacts.getnum2();
        String p3 = Contacts.getnum3();
        String msgsm = "Hello , I am from Savdhaan \n Your lovely one needs your help";
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},PackageManager.PERMISSION_GRANTED);
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(p1,null,msgsm,null,null);
        sms.sendTextMessage(p2,null,msgsm,null,null);
        sms.sendTextMessage(p3,null,msgsm,null,null);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        if(id==R.id.nav_feedback) {
            startActivity(new Intent(this, feedback.class));
        }
        if(id==R.id.nav_logout)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        if(id==R.id.nav_profile)
        {
            startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
        }
        if(id==R.id.nav_chat)
        {
            startActivity(new Intent(HomeActivity.this,Chat.class));
        }
        if(id==R.id.nav_call)
        {
            makeCall();
        }

        return false;
    }
}
