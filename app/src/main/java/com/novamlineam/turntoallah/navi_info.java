package com.novamlineam.turntoallah;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.HashMap;


public class navi_info extends AppCompatActivity {
    private Bundle extras;
    private String receivedString;
    private TextView navi_text, version, made, app_icon, project;
    private FloatingActionMenu mainFloat;
    private FloatingActionButton secFloat1;
    private FloatingActionButton secFloat2;
    private FloatingActionButton secFloat3;
    private ListView listView;
    private CustomListViewAdapter customListViewAdapter;
    private ImageView logoImage, novam_lineam_logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_info);

        navi_text = findViewById(R.id.navi_text);
        version = findViewById(R.id.version);
        made = findViewById(R.id.made);

        //FAB SETUP
        mainFloat = findViewById(R.id.menu);
        secFloat1 = findViewById(R.id.youtube);
        secFloat2 = findViewById(R.id.facebook);
        secFloat3 = findViewById(R.id.contactUs);

        //LOGO
        logoImage = findViewById(R.id.logo);

        //APP ICON
        app_icon = findViewById(R.id.app_icon);

        project = findViewById(R.id.project);

        novam_lineam_logo = findViewById(R.id.imageView);


        receivedString = null;

        extras = getIntent().getExtras();

        if (extras != null) {
            receivedString = extras.getString("sentString");
        }


        switch (receivedString) {
            case "about":
                navi_text.setText("Turn To Allah");
                Typeface english = Typeface.createFromAsset(getAssets(), "fonts/Panton-Bold.ttf");
                navi_text.setTypeface(english);

                break;
            case "licenses":
                navi_text.setText("LICENSES");
                setUpLibraries();
                break;
        }


        FABsetup();
        customizeActionBar();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void customizeActionBar() {

        final ActionBar abar = getSupportActionBar();

        View viewActionBar = getLayoutInflater().inflate(R.layout.actionbar_titletext_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        if (receivedString.equalsIgnoreCase("about")) {
            textviewTitle.setText("About");
            textviewTitle.setTextColor(getResources().getColor(R.color.white));
        } else {
            textviewTitle.setText("Licenses");
            textviewTitle.setTextColor(getResources().getColor(R.color.white));
        }

        //textviewTitle.setTextSize(14);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setIcon(R.color.transparent);
        abar.setHomeButtonEnabled(true);

        textviewTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textviewTitle.setFocusable(true);
        textviewTitle.setFocusableInTouchMode(true);
        textviewTitle.requestFocus();
        textviewTitle.setSingleLine(true);
        textviewTitle.setSelected(true);
        textviewTitle.setMarqueeRepeatLimit(-1);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    private void FABsetup() {


        mainFloat.getMenuIconView().setImageDrawable(new IconicsDrawable(getApplicationContext(), FontAwesome.Icon.faw_heart2).color(Color.WHITE).sizeDp(30));
        secFloat1.setImageDrawable(new IconicsDrawable(getApplicationContext(), FontAwesome.Icon.faw_youtube).color(Color.WHITE).sizeDp(16));
        secFloat2.setImageDrawable(new IconicsDrawable(getApplicationContext(), FontAwesome.Icon.faw_facebook_f).color(Color.WHITE).sizeDp(16));
        secFloat3.setImageDrawable(new IconicsDrawable(getApplicationContext(), FontAwesome.Icon.faw_envelope).color(Color.WHITE).sizeDp(16));


        secFloat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://www.youtube.com/aadhibaat";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        secFloat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://www.facebook.com/novamlineam";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        secFloat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = "novamlineam@gmail.com";
                String subject = "Suggestions";
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                startActivity(Intent.createChooser(emailIntent, "Contact Us"));

            }
        });

    }


    private void setUpLibraries() {

        listView = findViewById(R.id.licenses);


        navi_text.setVisibility(View.GONE);
        version.setVisibility(View.GONE);
        made.setVisibility(View.GONE);
        mainFloat.setVisibility(View.GONE);
        logoImage.setVisibility(View.GONE);
        app_icon.setVisibility(View.GONE);
        project.setVisibility(View.GONE);
        novam_lineam_logo.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);


        final String[] bookTitles = new String[]{
                "WheelPicker",
                "android-sqlite-asset-helper",
                "screenshott",
                "TedPermission",
                "NativeStackBlur",
                "AnimatedGradientTextView",
                "FloatingActionButton",
                "AnimationEasingFunctions",
                "AndroidViewAnimations",
                "MaterialDrawer",
                "sdp",
                "ssp"

        };
        final String[] authors = new String[]{

                "Version 1.1.2",
                "Version 2.0.1",
                "Version 2.2.0",
                "Version 2.2.0",
                "Version 1.0.5",
                "Version 0.0.6",
                "Version 1.6.4",
                "Version 2.0",
                "Version 2.3",
                "Version 6.0.8",
                "Version 1.0.5",
                "Version 1.0.5"


        };
        final String[] bookPages = new String[]{
                "Copyright 2015-2017 AigeStudio",
                "Copyright (C) 2011 readyState Software Ltd\n" + "Copyright (C) 2007 The Android Open Source Project",
                "Copyright 2016 Nishant Srivastava",
                "Copyright 2017 Ted Park",
                "Copyright 2015 Commit 451",
                "Copyright 2017 Mursaat",
                "Copyright 2015 Dmytro Tarianyk",
                "Copyright (c) 2017 daimajia",
                "Copyright (c) 2014 daimajia",
                "Copyright 2018 Mike Penz",
                "Copyright (c) 2013 - 2015 Intuit Inc.",
                "Copyright (c) 2013 - 2015 Intuit Inc."


        };
        ArrayList<HashMap<String, String>> authorList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {

            HashMap<String, String> data = new HashMap<>();
            data.put("title", bookTitles[i]);
            data.put("pages", bookPages[i]);
            data.put("author", authors[i]);


            authorList.add(data);

        }
        // listView = (ListView) findViewById(R.id.licenses);

        //Setup adapter
        customListViewAdapter = new CustomListViewAdapter(getApplicationContext(), authorList);
        listView.setAdapter(customListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });


    }
}




