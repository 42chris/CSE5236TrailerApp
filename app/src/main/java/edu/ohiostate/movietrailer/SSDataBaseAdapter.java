package edu.ohiostate.movietrailer;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SSDataBaseAdapter
{
    private static final String TAG = "SSDatabaseAdapter";

    static final String DATABASE_NAME = "template.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String TEMPLATE_CREATE = "create table "+"TEMPLATE"+
            "( " +"ID"+" integer primary key autoincrement,"+ "GENRE  text,PROMPT1 text,PROMPT2 text, PROMPT3 text, CLIPS text); ";

    static final String PROMPT_TEST_CREATE = "create table "+"test"+
            "( " +"ID"+" integer primary key autoincrement,"+ "TYPE  text,QUESTION text,CHOICES text, LENGTH real); ";
    static final String CLIPS_TEST_CREATE = "create table "+"TESTC"+
            "( " +"ID"+" integer primary key autoincrement,"+ "CREATED  integer,FILEPATH text); ";

    // Variable to hold the database instance
    public  SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private SSDatabaseHelper dbHelper;
    public SSDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new SSDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public SSDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }



    //================================================================================
    // Load Data
    //================================================================================

    public void populateTemplates(){
        ContentValues newValues = new ContentValues();
        // Assign values for each row.

        StringBuilder text = new StringBuilder();
        String line = "";
        BufferedReader br = null;
        int index =0;
        ArrayList<String> lines = new ArrayList<String>();
        try {
            br = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("templates/templateTable.txt")));

            while ((line = br.readLine()) != null) {
                lines.add(index,line);
                Log.d(TAG,line);
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            Log.d(TAG,"Error Reading file");
        }
        for (String s:lines) {
            String arrayString[] = s.split("\\s+");
            newValues.put("GENRE", arrayString[0]);
            newValues.put("PROMPT1",arrayString[1]);
            newValues.put("PROMPT2",arrayString[2]);
            newValues.put("PROMPT3",arrayString[3]);
            newValues.put("CLIPS",arrayString[4]);

            // Insert the row into your table
            db.insert("TEMPLATE", null, newValues);

        }

        //newValues.put("GENRE", "Action");
        //newValues.put("PROMPT1",password);
        // Insert the row into your table
        //db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public void populatePrompts(String[] promptFileNames){
        ContentValues newValues = new ContentValues();
        // Assign values for each row.

        StringBuilder text = new StringBuilder();
        String line = "";
        BufferedReader br = null;
        int index =0;
        ArrayList<String> lines = new ArrayList<String>();
        try {
            br = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("prompts/"+promptFileNames[0]+".txt")));

            while ((line = br.readLine()) != null) {
                lines.add(index,line);
                Log.d(TAG,line);
                index++;
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            Log.d(TAG,"Error Reading file");
        }
        for (String s:lines) {
            String arrayString[] = s.split("\\s+");
            newValues.put("TYPE", arrayString[0]);
            Log.d(TAG,arrayString[0]);
            //Fix for quotes
            newValues.put("QUESTION",arrayString[1]);
            Log.d(TAG,arrayString[1]);
            newValues.put("CHOICES",arrayString[2]);
            Log.d(TAG,arrayString[2]);
            try {
                newValues.put("LENGTH", Float.parseFloat(arrayString[3]));
                Log.d(TAG,arrayString[3]);
            }
            catch (NumberFormatException nfe){
                nfe.printStackTrace();
                Log.d(TAG,"Error parsing float");
            }

            // Insert the row into your table
            db.insert(promptFileNames[0], null, newValues);

        }

        //newValues.put("GENRE", "Action");
        //newValues.put("PROMPT1",password);
        // Insert the row into your table
        //db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public void populateClips(String[] clipFileNames){
        ContentValues newValues = new ContentValues();
        // Assign values for each row.

        StringBuilder text = new StringBuilder();
        String line = "";
        BufferedReader br = null;
        int index =0;
        ArrayList<String> lines = new ArrayList<String>();
        try {
            br = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("clips/"+clipFileNames[0]+".txt")));

            while ((line = br.readLine()) != null) {
                lines.add(index,line);
                Log.d(TAG,line);
                index++;
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            Log.d(TAG,"Error Reading file");
        }
        for (String s:lines) {
            newValues = new ContentValues();
            String arrayString[] = s.split("\\s+");
            try {
                newValues.put("CREATED", Integer.parseInt(arrayString[0]));
                Log.d(TAG,arrayString[0]);
            }
            catch (NumberFormatException nfe){
                nfe.printStackTrace();
                Log.d(TAG,"Error parsing int");
            }

            //Fix for quotes
            newValues.put("FILEPATH",arrayString[1]);
            Log.d(TAG,arrayString[1]);


            // Insert the row into your table
            db.insert(clipFileNames[0], null, newValues);
            Log.d(TAG, "Clip inserted into table");


        }

        //newValues.put("GENRE", "Action");
        //newValues.put("PROMPT1",password);
        // Insert the row into your table
        //db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    //================================================================================
    // Change Data
    //================================================================================

    public void insertEntry(String userName,String password)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD",password);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        String where="USERNAME=?";
        String[] usernameArray = {UserName};
        int numberOFEntriesDeleted= db.delete("LOGIN", where, usernameArray);
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public void  updateEntry(String userName,String password)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD",password);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});
    }


    //================================================================================
    // Get Data
    //================================================================================
    public Queue<Prompt> getPromptArray(String genre, int numActors)
    {
        Queue<Prompt> promptArray = new ConcurrentLinkedQueue<Prompt>();
        Cursor cursor=db.query("TEMPLATE", null, " GENRE=?", new String[]{genre}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
        }
        cursor.moveToFirst();
        String promptName = "";
        if (numActors ==1) {
            promptName = cursor.getString(cursor.getColumnIndex("PROMPT1"));
        }else if(numActors ==2){
            promptName =cursor.getString(cursor.getColumnIndex("PROMPT2"));
        }else{
            promptName =cursor.getString(cursor.getColumnIndex("PROMPT3"));
        }
        cursor.close();

        Cursor cursor2 = db.query(promptName,null,null,null,null,null,null);
        if(cursor2.getCount()<1) // UserName Not Exist
        {
            cursor2.close();
        }
        cursor2.moveToFirst();
        while (!cursor2.isAfterLast()){

            String type= cursor2.getString(cursor2.getColumnIndex("TYPE"));
            String question= cursor2.getString(cursor2.getColumnIndex("QUESTION"));
            float length = cursor2.getFloat(cursor2.getColumnIndex("LENGTH"));
            Prompt promptEntry = new Prompt(question,type,length,null);

            promptArray.add(promptEntry);
            cursor2.moveToNext();
        }

        cursor2.close();
        return promptArray;
    }

    public ArrayList<Clip> getClipArray(String genre)
    {
        ArrayList<Clip> clipArray = new ArrayList<Clip>();
        Cursor cursor=db.query("TEMPLATE", null, " GENRE=?", new String[]{genre}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
        }
        cursor.moveToFirst();
        String clipArrayName = "";
        clipArrayName =cursor.getString(cursor.getColumnIndex("CLIPS"));
        cursor.close();
        Cursor cursor2 = db.query(clipArrayName,null,null,null,null,null,null);
        if(cursor2.getCount()<1) // UserName Not Exist
        {
            cursor2.close();
        }
        cursor2.moveToFirst();
        while (!cursor2.isAfterLast()){
            Log.d(TAG, "cursor2 did a thing");
            Clip clipEntry = new Clip(context);
            int created = cursor2.getInt(cursor2.getColumnIndex("CREATED"));
            if (created == 1){
                String filepath= cursor2.getString(cursor2.getColumnIndex("FILEPATH"));
                clipEntry = new Clip(filepath,context);
            }
            clipArray.add(clipEntry);
            cursor2.moveToNext();
        }
        cursor2.close();
        return clipArray;
    }


}