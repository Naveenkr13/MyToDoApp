package com.asndeveloper.mytodolist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
private static final String Name="MyToDoListDatabse";
private static final String Table_Mytodo="ToDo";
private static final String ID="id";
private static final String TASK="task";
private static final String STATUS="status";
private static final int version=3;
private static final String Create_MyToDo_Table="CREATE TABLE "+ Table_Mytodo +
        " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
        + TASK + " TEXT," + STATUS
        +" INTEGER)";

private SQLiteDatabase db;

    //constructor
    public DBHelper(Context context){
        super(context,Name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL(Create_MyToDo_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
   db.execSQL("DROP TABLE IF EXISTS "+ Table_Mytodo);
   onCreate(db);
    }

    public void OpenDatabase(){
        db=this.getWritableDatabase();
    }

    //Insert Task
    public void insertTask(Model_for_todo task){
        ContentValues cv=new ContentValues();
        cv.put(TASK,task.getTasked());
        cv.put(STATUS,0);
        db.insert(Table_Mytodo,null,cv);
    }
    //Get all task and Store in ArrayList

    @SuppressLint("Range")
    public List<Model_for_todo> getAllTask(){
        List<Model_for_todo> taskList=new ArrayList<>();

        Cursor cursor=null;             //one by one
        db.beginTransaction();
        //store data (task) while closing app
        try {
            cursor=db.query(Table_Mytodo,null,null,null,null
                    ,null,null,null);
            if (cursor!=null){
                if (cursor.moveToFirst()){
                    do{
                        Model_for_todo task=new Model_for_todo();
                        task.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        task.setTasked(cursor.getString(cursor.getColumnIndex(TASK)));
                        task.setCheck_status(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        taskList.add(task);

                    }while (cursor.moveToNext());
                }
            }
        }
        // end transaction // close
        finally {
            db.endTransaction();
            assert cursor!=null;
            cursor.close();
        }
        return taskList;

    }


    // Update Status means check or unchecked
      public void UpdateStatus(int id,int status){
        ContentValues cv=new ContentValues();
        cv.put(STATUS,status);
        db.update(Table_Mytodo,cv,ID+"=?",new String[]{String.valueOf(id)});

      }

    // Update Task
    public void UpdateTask(int id,String task){
        ContentValues cv=new ContentValues();
        cv.put(TASK,task);
        db.update(Table_Mytodo,cv,ID+"=?",new String[]{String.valueOf(id)});

    }

    //Delete Task
    public void DeleteTask(int id){
        db.delete(Table_Mytodo,ID+"=?",new String[]{String.valueOf(id)});
    }




    //

}
