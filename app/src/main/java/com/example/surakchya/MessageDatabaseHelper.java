package com.example.surakchya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MessageDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static  final String dataBaseName="MessageRecord.db";
    private static final int dataBaseVersion = 1;
    private static final String tableName = "Message";
    private static final String columnID = "_id";
    private static final String columnContent = "Message_content";
    public MessageDatabaseHelper(@Nullable Context context) {
        super(context, dataBaseName, null, dataBaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ tableName +
                " (" + columnID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                columnContent + " TEXT);";
        db.execSQL(query);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ tableName);
        onCreate(db);
    }

    void addEmergencymessage(String ContactContent){
        // here this keyword refers to the SQLiteHelper class
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnContent,ContactContent);

        //using the SQLite database object to insert data into the database table
        long result = database.insert(tableName,null,contentValues);
        if(result == -1){
            Toast.makeText(this.context, this.context.getString(R.string.adding_contact_not_sucess),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this.context, this.context.getString(R.string.adding_conact),Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getEmergencyMessage(){
        String query = "SELECT * FROM "+ tableName;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if(sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }
    void truncateMessage(String rowid){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(tableName,"_id=?", new String[]{rowid});
    }

    void updateMessage(String message_id, String message_content){
         SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
         ContentValues contentValues = new ContentValues();
         contentValues.put(columnID,message_id);
         contentValues.put(columnContent,message_content);
         sqLiteDatabase.update(tableName,contentValues,"_id=?", new String[]{message_id});
    }
}
