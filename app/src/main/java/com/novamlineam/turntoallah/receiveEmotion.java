package com.novamlineam.turntoallah;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.commit451.nativestackblur.NativeStackBlur;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import github.nisrulz.screenshott.ScreenShott;

import static com.novamlineam.turntoallah.functions.getDefaults;
import static com.novamlineam.turntoallah.functions.setDefaults;


public class receiveEmotion extends AppCompatActivity {
    private Bundle extras;
    private String emotionString;
    private TextView arabic_text, english_text, reference_text;
    Cursor c = null;
    private SQLiteDatabase db;
    private String arabic, english, reference, referenced_text_modified, identifier;
    private ArrayList<HashMap<String, String>> authorList;
    private ImageButton takeScreenshot, randomButton;
    private View view;
    private ImageView image;
    private Context context;
    private AnimationDrawable animationDrawable;
    private Animation left_to_right, right_to_left;
    private Dialog d;
    private Bitmap bitmap_rootview;
    private String bitmapFilePath;
    private CardView animateCardView;
    private Button switchLanguageButton;
    private String languageDefaults;
    private PopupMenu popup;
    private Drawer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_receive_emotion);


        languageDefaults = "";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        languageDefaults = preferences.getString("language", "english");

        initialize();



 ;

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.dismissPopupMenus();


        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("About").withTypeface(Typeface.createFromAsset(getAssets(), "fonts/Panton-Bold.ttf")).withSelectedTextColor(getResources().getColor(R.color.md_deep_purple_800)).withSelectedIconColor(getResources().getColor(R.color.md_deep_purple_600)).withIcon(FontAwesome.Icon.faw_quote_left);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("Licenses").withTypeface(Typeface.createFromAsset(getAssets(), "fonts/Panton-Bold.ttf")).withSelectedTextColor(getResources().getColor(R.color.md_deep_purple_800)).withSelectedIconColor(getResources().getColor(R.color.md_deep_purple_600)).withIcon(FontAwesome.Icon.faw_certificate);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName("Rate This App").withTypeface(Typeface.createFromAsset(getAssets(), "fonts/Panton-Bold.ttf")).withSelectedTextColor(getResources().getColor(R.color.md_deep_purple_800)).withSelectedIconColor(getResources().getColor(R.color.md_deep_purple_600)).withIcon(FontAwesome.Icon.faw_star);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withName("Contact Us").withTypeface(Typeface.createFromAsset(getAssets(), "fonts/Panton-Bold.ttf")).withSelectedTextColor(getResources().getColor(R.color.md_deep_purple_800)).withSelectedIconColor(getResources().getColor(R.color.md_deep_purple_600)).withIcon(FontAwesome.Icon.faw_envelope);


        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .addDrawerItems(

                        item1,
                        item2,
                        item3,
                        item4

                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        final Intent mainIntent;

                        switch (position) {
                            case 0:
                                mainIntent = new Intent(receiveEmotion.this, navi_info.class);
                                mainIntent.putExtra("sentString", "about");
                                receiveEmotion.this.startActivity(mainIntent);
                                break;
                            case 1:
                                mainIntent = new Intent(receiveEmotion.this, navi_info.class);
                                mainIntent.putExtra("sentString", "licenses");
                                receiveEmotion.this.startActivity(mainIntent);
                                break;
                            case 2:
                                mainIntent = new Intent(Intent.ACTION_VIEW);
                                mainIntent.setData(Uri.parse ("market://details?id=com.novamlineam.turntoallah"));
                                startActivity(mainIntent);
                                break;
                            case 3:
                                String email = "novamlineam@gmail.com";
                                String subject = "Suggestions - TurnToAllah App";
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                                startActivity(Intent.createChooser(emailIntent,"Contact Us"));
                                break;

                        }

                        return false;
                    }
                }).build();

        result.addStickyFooterItem(new PrimaryDrawerItem().withName(R.string.app_name_version));
        //result.(DrawerLayout.LOCK_MODE_LOCKED_OPEN);





        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.actionbar_titletext_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        Typeface english = Typeface.createFromAsset(getAssets(), "fonts/Panton-Bold.ttf");
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Turn To Allah");
        textviewTitle.setTextColor(getResources().getColor(R.color.white));
        textviewTitle.setTypeface(english);

        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowHomeEnabled(true);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setIcon(R.color.transparent);
        abar.setHomeButtonEnabled(true);





    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.switchToArabic:
                setDefaults("language", "arabic", receiveEmotion.this);
                //Toast.makeText(receiveEmotion.this, "switched to arabic", Toast.LENGTH_SHORT).show();
                switchLanguageButton.setText("العربیہ ▼");
                english_text.setText(arabic);
                Typeface arabic = Typeface.createFromAsset(getAssets(), "fonts/noorehuda.ttf");
                english_text.setTypeface(arabic);

                return true;

            case R.id.switchToEnglish:
                setDefaults("language", "english", receiveEmotion.this);
                //Toast.makeText(receiveEmotion.this, "switched to english", Toast.LENGTH_SHORT).show();
                switchLanguageButton.setText("▼ English");
                english_text.setText(english);
                Typeface englishTypeFace = Typeface.createFromAsset(getAssets(), "fonts/Exo-Bold-Italic.otf");
                english_text.setTypeface(englishTypeFace);
                return true;
        }
        return true;
    }


    private void setFonts() {
        Typeface arabic = Typeface.createFromAsset(getAssets(), "fonts/noorehuda.ttf");
        Typeface english = Typeface.createFromAsset(getAssets(), "fonts/Panton-Bold.ttf");
        Typeface reference = Typeface.createFromAsset(getAssets(), "fonts/Panton-ExtraBold.otf");
        arabic_text.setTypeface(arabic);
        english_text.setTypeface(english);
        reference_text.setTypeface(reference);

    }

    public File saveScreenshotToPicturesFolder(Context context, Bitmap image, String filename)
            throws Exception {
        File bitmapFile = getOutputMediaFile(filename);
        if (bitmapFile == null) {
            throw new NullPointerException("Error creating media file, check storage permissions!");
        }
        FileOutputStream fos = new FileOutputStream(bitmapFile);
        image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();

        // Initiate media scanning to make the image available in gallery apps
        MediaScannerConnection.scanFile(context, new String[] { bitmapFile.getPath() },
                new String[] { "image/png" }, null);
        return bitmapFile;
    }

    private File getOutputMediaFile(String filename) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDirectory = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDirectory.exists()) {
            if (!mediaStorageDirectory.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName = filename + timeStamp + ".png";
        mediaFile = new File(mediaStorageDirectory.getPath() + File.separator + mImageName);
        return mediaFile;
    }


    public void takeScreenshot(View view) {

        View v1 = findViewById(R.id.cont);
        View buttonView = findViewById(R.id.screenshotButton);


        YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(0)
                .playOn(buttonView);

        bitmap_rootview = ScreenShott.getInstance().takeScreenShotOfView(v1);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap_rootview);


        Bitmap c = drawText("/ Turn To Allah  ", bitmap_rootview.getWidth(), bitmap_rootview.getHeight(), R.color.android_studio_green, bitmap_rootview);

        bitmapFilePath = null;

        //Toast.makeText(receiveEmotion.this, "Saved to gallery", Toast.LENGTH_SHORT).show();
        try {
            File file = saveScreenshotToPicturesFolder(receiveEmotion.this, bitmap_rootview, "turntoallah_");
            bitmapFilePath = file.getAbsolutePath();


        } catch (Exception e) {

        }


        showAlert(drawable);
    }
    public void generate_random_emotion(String id){


        setAnimation();


        View card_view = findViewById(R.id.card_view);
        YoYo.with(Techniques.ZoomIn)
                .duration(1000)
                .repeat(0)
                .playOn(card_view);


        languageDefaults = getDefaults("language", receiveEmotion.this);
        if(languageDefaults == null) {
            languageDefaults = "english";
        }

     firstQuery();



            }
    public void generate(View view) {

        View buttonView = findViewById(R.id.randomButton);
        YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(0)
                .playOn(buttonView);

        //languageDefaults = getDefaults("language", this);

        generate_random_emotion(emotionString);
    }
    public void showAlert(Drawable drawable) {

        d = new Dialog(receiveEmotion.this);
        d.setContentView(R.layout.dialog);
        d.setCancelable(false);

        ImageView iv = (ImageView) d.findViewById(R.id.imageView1);
        ImageView blurImg = (ImageView) d.findViewById(R.id.screenshot_blur_image);
        Button shareBtn = (Button) d.findViewById(R.id.shareBtnDialog);
        final Button cancelBtn = (Button) d.findViewById(R.id.cancelBtnDialog);
        TextView app_name = (TextView) d.findViewById(R.id.shareScreenshotHeading);

        Bitmap bm = NativeStackBlur.process(bitmap_rootview, 10);
        Drawable BlurDrawable = new BitmapDrawable(getResources(), bm);

        iv.setImageDrawable(drawable);
        blurImg.setImageDrawable(BlurDrawable);

        Typeface heading_typeface = Typeface.createFromAsset(getAssets(), "fonts/Exo-Bold-Italic.otf");
        app_name.setTypeface(heading_typeface);


        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.cancel();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/jepg");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+bitmapFilePath));
                startActivity(Intent.createChooser(shareIntent, "Share with the ones you care about"));
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(bitmapFilePath);
                boolean deleted = file.delete();
                if(deleted) {
                    d.cancel();
                }

            }
        });

        d.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
       // d.getWindow().getAttributes().height = WindowManager.LayoutParams.MATCH_PARENT;



        d.show();






    }
    public void setAnimation() {
        View arabic_1 = findViewById(R.id.arabic);
        View english_2 = findViewById(R.id.english);
        View reference_3 = findViewById(R.id.reference);


        YoYo.with(Techniques.SlideInLeft)
                .duration(1500)
                .repeat(0)
                .playOn(arabic_1);

        YoYo.with(Techniques.SlideInRight)
                .duration(1500)
                .repeat(0)
                .playOn(english_2);

        YoYo.with(Techniques.SlideInLeft)
                .duration(1500)
                .repeat(0)
                .playOn(reference_3);
    }
    public Bitmap drawText(String text, int imageWidth, int imageHeight, int color, Bitmap bitmap) {

        // Get text dimensions
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(color);

        Typeface english = Typeface.createFromAsset(getAssets(), "fonts/Exo-Bold-Italic.otf");
        textPaint.setTypeface(english);
        textPaint.setTextSize(getResources().getDimension(R.dimen._12ssp));
        textPaint.setColor(color);
        // textPaint.setTextAlign(Paint.Align.CENTER);


        int myColor= getResources().getColor(R.color.android_studio_green);
        int myColor2 = getResources().getColor(R.color.black);
        textPaint.setColor(myColor);

        // Get text dimensions
        TextPaint textPaint2 = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaint2.setStyle(Paint.Style.FILL);
        textPaint2.setColor(myColor2);

        textPaint2.setTypeface(english);
        textPaint2.setTextSize(getResources().getDimension(R.dimen._6ssp));


        StaticLayout mTextLayout = new StaticLayout(text, textPaint, imageWidth, Layout.Alignment.ALIGN_OPPOSITE, 1.0f, 0.0f, false);

       StaticLayout mTextLayout2 = new StaticLayout("A DUA/VERSE FOR EVERY EMOTION YOU FEEL \n Download From Play Store        ", textPaint2, imageWidth, Layout.Alignment.ALIGN_OPPOSITE, 1.0f, 0.0f, false);

        // Create bitmap and canvas to draw to
        Bitmap b = Bitmap.createBitmap(imageWidth, mTextLayout.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(b);
        c.setBitmap(bitmap);


        // Draw text
        c.save();
        int dr = imageHeight-mTextLayout.getHeight();
        int summed = (int) (getResources().getDimension(R.dimen._20sdp));
        int sum = dr - summed;
        c.translate(0, sum);
        mTextLayout.draw(c);
        int trans = (int) (getResources().getDimension(R.dimen._14sdp));
        c.translate(0, trans);
        mTextLayout2.draw(c);
        c.restore();

        return b;
    }

    public void firstQuery() {



        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        authorList = databaseAccess.get_emotion(emotionString, this);

        if(authorList != null && authorList.size() > 0) {

            for(int i = 1; i < authorList.size(); i++) {


                arabic = authorList.get(i).get("arabic");
                english = authorList.get(i).get("english");
                reference = authorList.get(i).get("reference").toUpperCase();

                identifier = authorList.get(i).get("id");

                referenced_text_modified = "-- " + reference;


                if(languageDefaults.equalsIgnoreCase("english")) {
                    english_text.setText(english);
                    Typeface english = Typeface.createFromAsset(getAssets(), "fonts/Exo-Bold-Italic.otf");
                    english_text.setTypeface(english);
                } else if(languageDefaults.equalsIgnoreCase("arabic")) {
                    english_text.setText(arabic);
                    Typeface arabic = Typeface.createFromAsset(getAssets(), "fonts/noorehuda.ttf");
                    english_text.setTypeface(arabic);
                } else {
                    english_text.setText(english);
                    Typeface english = Typeface.createFromAsset(getAssets(), "fonts/Exo-Bold-Italic.otf");
                    english_text.setTypeface(english);
                }


                reference_text.setText(referenced_text_modified);

            }



        }
    }
    public void initialSetup() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        arabic_text = findViewById(R.id.arabic);
        english_text = findViewById(R.id.english);
        reference_text = findViewById(R.id.reference);




        switchLanguageButton = findViewById(R.id.button);



        takeScreenshot = findViewById(R.id.screenshotButton);
        randomButton = findViewById(R.id.randomButton);


        extras = getIntent().getExtras();
        if (extras != null) {
            emotionString = extras.getString("emotion_id");
        }


        switchLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = new PopupMenu(receiveEmotion.this, v);
                //popup.setOnMenuItemClickListener(receiveEmotion.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {


                        switch (menuItem.getItemId()) {
                            case R.id.switchToArabic:
                                setDefaults("language", "arabic", receiveEmotion.this);
                                //Toast.makeText(receiveEmotion.this, "switched to arabic", Toast.LENGTH_SHORT).show();
                                switchLanguageButton.setText("العربیہ ▼");
                                english_text.setText(arabic);
                                Typeface arabic = Typeface.createFromAsset(getAssets(), "fonts/noorehuda.ttf");
                                english_text.setTypeface(arabic);

                                return  true;

                            case R.id.switchToEnglish:
                                setDefaults("language", "english", receiveEmotion.this);
                                //Toast.makeText(receiveEmotion.this, "switched to english", Toast.LENGTH_SHORT).show();
                                switchLanguageButton.setText("▼ English");
                                english_text.setText(english);
                                Typeface englishTypeFace = Typeface.createFromAsset(getAssets(), "fonts/Panton-Bold.ttf");
                                english_text.setTypeface(englishTypeFace);

                                return  true;
                        }



                        return true;

                    }
                });

            }



        });


    }
    public void initialize() {
        initialSetup();
        firstQuery();
    }




}
