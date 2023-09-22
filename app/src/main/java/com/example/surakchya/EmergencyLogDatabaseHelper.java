package com.example.surakchya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class EmergencyLogDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static  final String dataBaseName="EmergencyLog.db";
    private static final int dataBaseVersion = 1;
    private static final String tableName = "EmergencyLog";
    private static final String columnID = "_id";
    private static final String columnContent = "Message_content";
    private static final String columnContact = "Contact";
    private static final String columnLattitude = "Lattitude";
    private static final String columnLongitude = "Longitude";
    public EmergencyLogDatabaseHelper(@Nullable Context context) {
        super(context, dataBaseName, null, dataBaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ tableName +
                " (" + columnID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                columnContact + " TEXT, "+
                columnContent + " TEXT, "+
                columnLattitude + " TEXT, "+
                columnLongitude + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS "+ tableName);
      onCreate(db);
    }
    void addEmergencyLog(String emergencyContact, String emergencyContent, String emergencyLattitude, String emergencyLongitude){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnContact, emergencyContact);
        contentValues.put(columnContent,emergencyContent);
        contentValues.put(columnLattitude,emergencyLattitude);
        contentValues.put(columnLongitude,emergencyLongitude);
        // inserting new set of values into the database
        long result = sqLiteDatabase.insert(tableName,null,contentValues);
        if(result == -1){
            Toast.makeText(this.context, this.context.getString(R.string.adding_contact_not_sucess),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this.context, this.context.getString(R.string.adding_conact),Toast.LENGTH_SHORT).show();
        }
    }
    Cursor getAllEmergencyLog(){
        String query = "SELECT * FROM "+ tableName;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if(sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }
    void truncateEmergencyLog(String rowid){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
       // sqLiteDatabase.execSQL("TRUNCATE TABLE");
        sqLiteDatabase.delete(tableName,"_id=?", new String[]{rowid});
    }

}
