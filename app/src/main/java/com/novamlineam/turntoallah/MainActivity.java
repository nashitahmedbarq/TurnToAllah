package com.novamlineam.turntoallah;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.aigestudio.wheelpicker.WheelPicker;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private WheelPicker mywheelPicker;
    private Button submitButton;
    private String sendString;
    private View view;
    private MotionEvent event;

    Cursor c = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Please grant Storage Permissions for in-app screenshots \n \n Go to [SETTINGS] > [PERMISSIONS]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setDeniedCloseButtonText("CLOSE APP")
                .setGotoSettingButtonText("SETTINGS")
                .check();


        ArrayList<String> names = new ArrayList<String>();


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();


        names.add("Anticipated");
        names.add("Anxious");
        names.add("Arrogant");
        names.add("Confused");
        names.add("Determined");
        names.add("Distressed");
        names.add("Doubtful");
        names.add("Grateful");
        names.add("Guilty");
        names.add("Happy");
        names.add("Helpless");
        names.add("Hurt");
        names.add("Impatient");
        names.add("Jealous");
        names.add("Lazy");
        names.add("Lonely");
        names.add("Sad");
        names.add("Scared");
        names.add("Uneasy");
        names.add("Weak");




        submitButton = findViewById(R.id.submitButton);

       // submitButton.setTextSize(getResources().getDimension(R.dimen._13sdp));


        mywheelPicker = findViewById(R.id.wheelPicker);

        mywheelPicker.setData(names);

        mywheelPicker.setAtmospheric(true);

        mywheelPicker.setItemAlign(1);


        if(sendString == null) {
            sendString = "anticipated";
        }



       // mywheelPicker.setCurved(true);
        mywheelPicker.setCyclic(true);
        mywheelPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {


                sendString = data.toString().toLowerCase();



            }
        });



    }

    public void passEmotion(View view) {

        YoYo.with(Techniques.Flash)
                .duration(700)
                .repeat(0)
                .playOn(submitButton);



        Intent i = new Intent(MainActivity.this, receiveEmotion.class);
        i.putExtra("emotion_id", sendString);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);




    }

    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            //Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            MainActivity.this.finishAffinity();

        }


    };



}
