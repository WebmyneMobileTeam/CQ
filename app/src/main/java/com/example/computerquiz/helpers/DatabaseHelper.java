package com.example.computerquiz.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.computerquiz.model.Category;
import com.example.computerquiz.model.Level;
import com.example.computerquiz.model.Question;
import com.example.computerquiz.model.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by xitij on 06-03-2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static String TAG = "DATABASE_WRAPPER";
    private  String DB_PATH = "/data/data/com.example.computerquiz/databases/";
    private static String DB_NAME = "quiz.db";
    private SQLiteDatabase myDataBase = null;
    private Context myContext;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME,null,1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        if(dbExist){
//      Log.v("log_tag", "database does exist");
        }else{
//            Log.v("log_tag", "database does not exist");
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error copying database");
            }
        }
    }

    public ArrayList<Category> getAllCategories(){
        ArrayList<Category> categories = new ArrayList<>();
        myDataBase = this.getWritableDatabase();

      //  String wher = "role_name_" + " = ? AND " + "main_line_type_" + " = ?";
       // String[] FIELDS = { "cast_matches_" };
        Cursor cursor =   myDataBase.query("Category", null, null, null, null, null, null);
        String castMatchesString = null;
        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {

                Category category = new Category();
                category._id = cursor.getInt(cursor.getColumnIndex("_id"));
                category.category_name = cursor.getString(cursor.getColumnIndex("name"));
                categories.add(category);

            } while (cursor.moveToNext());
        }

        return categories;

    }

    public ArrayList<Level> getLevels(int category_id){
        ArrayList<Level> levels = new ArrayList<>();
        myDataBase = this.getWritableDatabase();

       // String wher = "category_id" + " = ? AND " + "main_line_type_" + " = ?";
        String wher = "category_id" + " = ?";
        // String[] FIELDS = { "cast_matches_" };
        Cursor cursor =   myDataBase.query("Level", null, wher, new String[]{""+category_id}, null, null, null);

        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {

                Level level = new Level();
                level._id = cursor.getInt(cursor.getColumnIndex("_id"));
                level.level_name = cursor.getString(cursor.getColumnIndex("name"));
                level.category_id = cursor.getInt(cursor.getColumnIndex("category_id"));
                levels.add(level);

            } while (cursor.moveToNext());
        }

        return levels;

    }

    public ArrayList<Question> getQuestions(int category_id,int level_id){
        ArrayList<Question> questions = new ArrayList<>();
        myDataBase = this.getWritableDatabase();

         String wher = "category_id" + " = ? AND " + "level_id" + " = ?";
       // String wher = "category_id" + " = ?";
        // String[] FIELDS = { "cast_matches_" };
        Cursor cursor =   myDataBase.query("Question", null, wher, new String[]{""+category_id,""+level_id}, null, null, null);
        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {

                Question question = new Question();
                question._id = cursor.getInt(cursor.getColumnIndex("_id"));
                question.category_id = cursor.getInt(cursor.getColumnIndex("category_id"));
                question.correct_answer = cursor.getInt(cursor.getColumnIndex("correct_answer"));
                question.level_id = cursor.getInt(cursor.getColumnIndex("level_id"));
                question.description = cursor.getString(cursor.getColumnIndex("description"));
                question.option1 = cursor.getString(cursor.getColumnIndex("option_1"));
                question.option2 = cursor.getString(cursor.getColumnIndex("option_2"));
                question.option3 = cursor.getString(cursor.getColumnIndex("option_3"));
                question.option4 = cursor.getString(cursor.getColumnIndex("option_4"));
                question.option5 = cursor.getString(cursor.getColumnIndex("option_5"));

                questions.add(question);

            } while (cursor.moveToNext());
        }

        return questions;

    }

    public void insertTest(Test test){

        ContentValues cv = new ContentValues();
        cv.put("level_id",test.level_id);
        cv.put("category_id",test.category_id);
        cv.put("total_questions",test.total_questions);
        cv.put("correct_questions",test.correct_questions);
        cv.put("isPassed",test.isPassed);

        myDataBase = this.getWritableDatabase();
        myDataBase.insert("Test",null,cv);


    }


    private void copyDataBase() throws IOException {

        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private boolean checkDataBase(){

        File folder = new File(DB_PATH);
        if(!folder.exists()){
            folder.mkdir();
        }
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        myDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return myDataBase != null;

    }
    @Override
    public synchronized void close()
    {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
