package com.example.surakchya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SelfDefenceDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static  final String dataBaseName="SelfDefenceGuide.db";
    private static final int dataBaseVersion = 1;
    private static final String tableName = "SelfDefenceGuide";
    private static final String columnID = "_id";
    private static final String columnHeading = "selfDefenceGuide_Heading";
    private static final String columnContent = "selfDefenceGuide_content";

    public SelfDefenceDataBaseHelper(@Nullable Context context) {
        super(context, dataBaseName, null, dataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ tableName +
                " (" + columnID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                columnHeading + " TEXT, "+
                columnContent + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ tableName);
        onCreate(db);
    }
    void AddSelfDefenceGuideContent(String SelfDefenceHeading,String SelfDefenceContent){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnHeading,SelfDefenceHeading);
        contentValues.put(columnContent,SelfDefenceContent);

        //using the SQLite database object to insert data into the database table
        long result = database.insert(tableName,null,contentValues);
        if(result == -1){
            Toast.makeText(this.context, this.context.getString(R.string.adding_contact_not_sucess),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this.context, this.context.getString(R.string.adding_conact),Toast.LENGTH_SHORT).show();
        }
    }
    Cursor getAllSelfDefenceGuideContent(){
        String query = "SELECT * FROM "+ tableName;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if(sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }
}
