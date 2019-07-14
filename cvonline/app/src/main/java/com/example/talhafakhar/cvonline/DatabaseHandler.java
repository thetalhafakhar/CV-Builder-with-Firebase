package com.example.talhafakhar.cvonline;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;
    // Database Name
    private static final String DATABASE_NAME = "cvbuilder";
    // table name
    private static final String TABLE_CONTACTS = "Register";
    private static final String TABLE_CV_DATA = "CVS";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";
    //CV COLUMNS
    private static final String KEY_UID = "User_id";
    private static final String KEY_CNAME = "NAME";
    private static final String KEY_FNAME = "FNAME";
    private static final String KEY_AGE = "AGE";
    private static final String KEY_DOB = "DOB";
    private static final String KEY_SCHOOL = "SCHOOL";
    private static final String KEY_CLG = "COLLEGE";
    private static final String KEY_UNI = "UNI";
    private static final String KEY_EDU = "EDUCATION";
    private static final String KEY_PJOB = "PREVIOUS_JOB";
    private static final String KEY_EXPY = "YEARS_OF_EXP";
    private static final String KEY_JOB = "JOB";
    private static final String KEY_FIELD = "FIELD";
    private static final String KEY_ACHIEVMENTS = "ACHIEVMENTS";
    private static final String KEY_AWARDS = "AWARDS";
    private static final String KEY_CERTFIFICATES = "CERTIFICATES";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_PASS + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        String CREATE_CV_TABLE = "CREATE TABLE " + TABLE_CV_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_UID + " TEXT," + KEY_CNAME + " TEXT,"
                + KEY_FNAME + " TEXT," + KEY_AGE + " TEXT," + KEY_DOB + " TEXT," + KEY_SCHOOL + " TEXT," + KEY_CLG + " TEXT,"
                + KEY_UNI + " TEXT," + KEY_EDU + " TEXT," + KEY_PJOB + " TEXT," + KEY_EXPY + " TEXT," + KEY_JOB + " TEXT,"
                + KEY_FIELD + " TEXT," + KEY_ACHIEVMENTS + " TEXT," + KEY_AWARDS + " TEXT," + KEY_CERTFIFICATES + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CV_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CV_DATA);
        // Create tables again
        onCreate(sqLiteDatabase);
    }
    // Adding new contact
    Long addContact(String name, String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // email
        values.put(KEY_PASS, pass); // pass
        // Inserting Row
        long id = db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
        return id;
    }
    //adding cv
    Long add_cv(String uid, String name, String fname, String age, String dob, String school, String clg, String uni, String edu, String pjob, String expy, String job, String field, String achi, String award, String certificate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_UID, uid);
        values.put(KEY_CNAME, name);
        values.put(KEY_FNAME, fname);
        values.put(KEY_AGE, age);
        values.put(KEY_DOB, dob);
        values.put(KEY_SCHOOL, school);
        values.put(KEY_CLG, clg);
        values.put(KEY_UNI, uni);
        values.put(KEY_EDU, edu);
        values.put(KEY_PJOB, pjob);
        values.put(KEY_EXPY, expy);
        values.put(KEY_JOB, job);
        values.put(KEY_FIELD, field);
        values.put(KEY_ACHIEVMENTS, achi);
        values.put(KEY_AWARDS, award);
        values.put(KEY_CERTFIFICATES, certificate);
        long id = db.insert(TABLE_CV_DATA, null, values);
        db.close();
        return id;
    }
    int check(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor query = db.query(TABLE_CONTACTS, new String[]{KEY_ID, KEY_NAME}, "email=? and password=?", new String[]{email, password}, null, null, null, null);
        int id = -1;
        if (query.getCount() > 0) {
            if (query.moveToFirst()) {
                do {
                    id = query.getInt(query.getColumnIndex(KEY_ID));
                    db.close();
                    return id;
                } while (query.moveToNext());
            } else {
                db.close();
                id = -2;
                return id;
            }
        } else
            return id;
    }
    public List<CV_attributes> get_cvdata(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  id FROM " + TABLE_CV_DATA + " WHERE "
                + KEY_UID + " = " + id;
        Cursor ex = db.rawQuery(query, null);
        if (ex.moveToFirst()) {
            List<CV_attributes> cvs = new ArrayList<CV_attributes>();
            do {
                CV_attributes cv;
                cv = new CV_attributes();
                cv.set_cv_id(ex.getString((ex.getColumnIndex(KEY_ID))).toString());
                cvs.add(cv);
            } while (ex.moveToNext());
            return cvs;
        } else
            return null;
    }
    public CV_attributes get_cv(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + TABLE_CV_DATA + " WHERE "
                + KEY_ID + " = " + id;
        Cursor ex = db.rawQuery(query, null);
        if (ex.moveToFirst()) {
            CV_attributes obj;
            do {
                obj = new CV_attributes(ex.getString((ex.getColumnIndex(KEY_CNAME))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_FNAME))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_AGE))).toString(), ex.getString((ex.getColumnIndex(KEY_DOB))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_SCHOOL))).toString(), ex.getString((ex.getColumnIndex(KEY_CLG))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_UNI))).toString(), ex.getString((ex.getColumnIndex(KEY_EDU))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_PJOB))).toString(), ex.getString((ex.getColumnIndex(KEY_EXPY))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_JOB))).toString(), ex.getString((ex.getColumnIndex(KEY_FIELD))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_ACHIEVMENTS))).toString(), ex.getString((ex.getColumnIndex(KEY_AWARDS))).toString(),
                        ex.getString((ex.getColumnIndex(KEY_CERTFIFICATES))).toString());
            } while (ex.moveToNext());
            return obj;
        } else
            return null;
    }
    public void delete_cv(String id) {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_CV_DATA+" WHERE "+KEY_ID+"='"+id+"'");
        db.close();
    }
}





