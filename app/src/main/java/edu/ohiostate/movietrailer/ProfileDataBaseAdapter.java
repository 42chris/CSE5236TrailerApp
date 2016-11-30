package edu.ohiostate.movietrailer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProfileDataBaseAdapter
{
    private static final String TAG = "ProfileDatabaseAdapter";

    static final String DATABASE_NAME = "profiles.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database

    // Variable to hold the database instance
    public  SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private ProfileDatabaseHelper dbHelper;
    public ProfileDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new ProfileDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public ProfileDataBaseAdapter open() throws SQLException
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

    public void createTable(String username){
        db.execSQL("create table "+username+
                "( " +"ID"+" integer primary key autoincrement,"+ "NAME  text,PATH text); ");
    }

    public void insertEntry(String username, String name,String path)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("NAME", name);
        newValues.put("PATH",path);

        // Insert the row into your table
        db.insert(username, null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserName, String name)
    {
        //String id=String.valueOf(ID);
        String where="NAME=?";
        int numberOFEntriesDeleted= db.delete(UserName, where, new String[]{name}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getSingleEntry(String userName, String name)
    {
        Cursor cursor=db.query(userName, null, " NAME=?", new String[]{name}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String path= cursor.getString(cursor.getColumnIndex("PATH"));
        cursor.close();
        return path;
    }
    public void  updateEntry(String userName, String name, String path)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("NAME", name);
        updatedValues.put("PATH",path);

        String where="NAME = ?";
        db.update(userName,updatedValues, where, new String[]{name});
    }

}