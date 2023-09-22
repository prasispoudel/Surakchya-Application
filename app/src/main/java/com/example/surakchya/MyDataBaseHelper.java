package com.example.surakchya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static  final String dataBaseName="ContactRecord.db";
    private static final int dataBaseVersion = 1;
    private static final String tableName = "Contact";
    private static final String columnID = "_id";
    private static final String columnName = "Contact_name";
    private static final String columnNumber = "Contact_number";
    public MyDataBaseHelper(@Nullable Context context) {
        super(context, dataBaseName, null, dataBaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String query = "CREATE TABLE "+ tableName +
               " (" + columnID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
               columnName + " TEXT, "+
               columnNumber + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          db.execSQL("DROP TABLE IF EXISTS "+ tableName);
          onCreate(db);
    }

    void addEmergencyContact(String ContactName, String ContactNumber){
        // here this keyword refers to the SQLiteHelper class
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName,ContactName);
        contentValues.put(columnNumber,ContactNumber);

        //using the SQLite database object to insert data into the database table
        long result = database.insert(tableName,null,contentValues);
        if(result == -1){
            Toast.makeText(this.context, this.context.getString(R.string.adding_contact_not_sucess),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this.context, this.context.getString(R.string.adding_conact),Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getAllEmeregncyContacts(){
        String query = "SELECT * FROM "+ tableName;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if(sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;

    }
    void removeEmergencyContact(String columnID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(tableName,"_id=?",new String[]{columnID});
    }
    void updateContact(String contact_id, String contact_name,String contact_number){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnID,contact_id);
        contentValues.put(columnName,contact_name);
        contentValues.put(columnNumber,contact_number);
        sqLiteDatabase.update(tableName,contentValues,"_id=?", new String[]{contact_id});
    }
}
