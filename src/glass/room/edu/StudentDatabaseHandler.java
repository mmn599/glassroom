package glass.room.edu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDatabaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;
 
    // Database Name
    private static final String DATABASE_NAME = "studentsDatabase";
 
    // Contacts table name
    private static final String TABLE_STUDENTS = "students";
    
    //subjects used
    private ArrayList<String> subjects;
    private ArrayList<String> subjectcorrectkeys;
    private ArrayList<String> subjecttotalkeys;
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PROF_URI = "profuri";
 
    public StudentDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        subjects = Student.defaultSubjects;
        subjecttotalkeys = createSubjectKeys(subjects, "TOTAL");
    	subjectcorrectkeys = createSubjectKeys(subjects, "CORRECT");
    }
    
    public StudentDatabaseHandler(Context context, ArrayList<String> subjects) {
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	this.subjects = subjects;
    	subjecttotalkeys = createSubjectKeys(subjects, "TOTAL");
    	subjectcorrectkeys = createSubjectKeys(subjects, "CORRECT");
    }
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + KEY_PROF_URI + " TEXT";
        for(String sub : subjecttotalkeys) {
        	CREATE_CONTACTS_TABLE += ","+sub;
        }
        for(String sub : subjectcorrectkeys) {
        	CREATE_CONTACTS_TABLE += ","+sub;
        }
        CREATE_CONTACTS_TABLE += ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    
    public ArrayList<String> createSubjectKeys(ArrayList<String> subjects, String info) {
    	ArrayList<String> toReturn = new ArrayList<String>();
    	for(String sub : subjects) {
    		String KEY = sub + info;
    		toReturn.add(KEY+" INTEGER");
    	}
    	return toReturn;
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        // Create tables again
        onCreate(db);
    }
    
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName()); // Contact Name
        for(String sub : subjects) {
        	values.put(sub+"CORRECT",student.getPerformance(sub).getCorrect());
        }
        for(String sub : subjects) {
        	values.put(sub+"TOTAL",student.getPerformance(sub).getTotal());
        }
        // Inserting Row
        db.insert(TABLE_STUDENTS, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single contact
    public Student getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        String[] commons = {KEY_ID, KEY_NAME};
        String[] correctKeys = subjectcorrectkeys.toArray(new String[subjectcorrectkeys.size()]);
        String[] totalKeys = subjecttotalkeys.toArray(new String[subjecttotalkeys.size()]);
        String[] total = new String[commons.length + correctKeys.length + totalKeys.length];
        System.arraycopy(commons, 0, total, 0, commons.length);
        System.arraycopy(correctKeys, 0, total, commons.length, correctKeys.length);
        System.arraycopy(totalKeys, 0, total, commons.length+correctKeys.length, totalKeys.length);
        
        Cursor cursor = db.query(TABLE_STUDENTS, total, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Student student = new Student(null, subjects);
        int x = 0;
        student.setId(Integer.parseInt(cursor.getString(x++)));
        student.setName(cursor.getString(x++));
        for(String sub : subjects) {
        	student.getPerformance(sub).setCorrect(Integer.parseInt(cursor.getString(x++)));
        }
        for(String sub : subjects) {
        	student.getPerformance(sub).setTotal(Integer.parseInt(cursor.getString(x++)));
        }

        // return contact
        return student;
    }
     
    // Getting All Students
    public List<Student> getAllContacts() {
        List<Student> contactList = new ArrayList<Student>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Student student = new Student(null, subjects);
                int x = 0;
                student.setId(Integer.parseInt(cursor.getString(x++)));
                student.setName(cursor.getString(x++));
        		student.setProfilePictureUri("drawable/" + student.getName().toLowerCase(Locale.getDefault()));
                for(String sub : subjects) {
                	student.getPerformance(sub).setCorrect(Integer.parseInt(cursor.getString(x++)));
                }
                for(String sub : subjects) {
                	student.getPerformance(sub).setTotal(Integer.parseInt(cursor.getString(x++)));
                }
                contactList.add(student);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
 
    // Updating single contact
    public int updateContact(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName()); // Contact Name
        for(String sub : subjects) {
        	values.put(sub+"CORRECT", student.getPerformance(sub).getCorrect());
        }
        for(String sub : subjects) {
        	values.put(sub+"TOTAL", student.getPerformance(sub).getTotal());
        }

        // updating row
        return db.update(TABLE_STUDENTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(student.getId()) });
    }
 
    // Deleting single contact
    public void deleteContact(Student contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    
}
