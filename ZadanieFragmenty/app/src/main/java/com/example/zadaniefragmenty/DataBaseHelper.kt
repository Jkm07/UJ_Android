package com.example.zadaniefragmenty

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) :  SQLiteOpenHelper(context, "local.db", null, 1){
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTableStatment = "CREATE TABLE TASKS (name TEXT, description TEXT, date TEXT, status Boolean)"
        p0?.execSQL(createTableStatment)
        val insert1 = "INSERT INTO TASKS (name, description, date, status) VALUES" +
                "(\"Wynieść śmieci\", \"śmieci\", \"2.08.2021\", 1), " +
                "(\"Pozmywać naczynia\", \"naczynia\", \"1.08.2021\", 0), " +
                "(\"Coś tam\", \"shdjahka\", \"23.08.2021\", 0)," +
                "(\"Zrobić zadania na androida\", \"śmieci\", \"11.08.2021\", 1)"
        p0?.execSQL(insert1)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    public fun getAll(): ArrayList<Task>{
        val returnList = ArrayList<Task>();
        val db = readableDatabase;
        val cursor = db.query("TASKS", null, null, null, null, null, null)
        if(cursor.moveToFirst()) {
            do {
                val name = cursor.getString(0);
                val desc = cursor.getString(1);
                val date = cursor.getString(2);
                val status = cursor.getInt(3);
                returnList.add(Task(name, desc, date, status == 1));
            }while(cursor.moveToNext());
        }
        return returnList;
    }
}