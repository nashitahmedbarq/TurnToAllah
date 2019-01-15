package com.novamlineam.turntoallah;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;
    private Context context;
    private int identification_number;

    private DatabaseAccess(Context context) {

        this.openHelper = new DatabaseOpenHelper(context);



    }


    public static DatabaseAccess getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }


    // open database connection
    public void open() {
        this.db  = openHelper.getWritableDatabase();

    }

    // close the database connection

    public void close() {
        if(db != null) {
            this.db.close();
        }

    }






    public ArrayList<HashMap<String,String>> get_emotion(String id, Context context) {

        ArrayList<HashMap<String, String>> authorList = new ArrayList<>();

        //Toast.makeText(context, Integer.toString(db.getVersion()), Toast.LENGTH_SHORT).show();


        c = db.rawQuery("SELECT * FROM mydata where emotion_id = '"+id+"' AND visited = 0 ORDER BY _id ASC LIMIT 1 ", null);


        if(c != null) {
            if(c.getCount() > 0) {
                authorList = process_emotion(id);
            } else {

                db.execSQL("UPDATE mydata SET visited = 0 where emotion_id = '" +id+ "' ");

                c = db.rawQuery("SELECT * FROM mydata where emotion_id = '"+id+"' AND visited = 0 ORDER BY _id ASC LIMIT 1 ", null);


                if(c.moveToFirst()) {

                    int arabicIndex = c.getColumnIndex("arabic");
                    int englishIndex = c.getColumnIndex("english");
                    int reference = c.getColumnIndex("reference");
                    identification_number = c.getColumnIndex("_id");



                    for(int i = 0; i<3; i++) {


                        HashMap<String, String> data = new HashMap<>();
                        data.put("arabic", c.getString(arabicIndex));
                        data.put("english", c.getString(englishIndex));
                        data.put("reference", c.getString(reference));
                        data.put("id", c.getString(identification_number));

                        authorList.add(data);



                    }


                    db.execSQL("UPDATE mydata SET visited = 1 where _id = " + Integer.parseInt(c.getString(identification_number)));
                    db.close();

                }

            }

        }
            return authorList;

    }

    public ArrayList<HashMap<String,String>> process_emotion(String id) {

        ArrayList<HashMap<String, String>> authorList = new ArrayList<>();


                if(c.moveToFirst()) {

                    int arabicIndex = c.getColumnIndex("arabic");
                    int englishIndex = c.getColumnIndex("english");
                    int reference = c.getColumnIndex("reference");
                    identification_number = c.getColumnIndex("_id");



                    for(int i = 0; i<4; i++) {


                        HashMap<String, String> data = new HashMap<>();
                        data.put("arabic", c.getString(arabicIndex));
                        data.put("english", c.getString(englishIndex));
                        data.put("reference", c.getString(reference));
                        data.put("id", c.getString(identification_number));

                        authorList.add(data);



                    }


                    db.execSQL("UPDATE mydata SET visited = 1 where _id = " + Integer.parseInt(c.getString(identification_number)));
                    db.close();

            }


        return authorList;

    }




}
