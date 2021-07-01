package com.example.seven_minutes_workout.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
) : SQLiteOpenHelper(
    context, DATABASE_NAME,
    factory, DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SevenMinutesWorkout.db"
        private const val TABLE_NAME = "history"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_COMPLETED_DATE = "completed_date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_COMPLETED_DATE TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAllCompletedDatesList(): ArrayList<String> {
        val list = ArrayList<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        while (cursor.moveToNext()) {
            // Returns the zero-based index for the given column name, or -1 if the column doesn't exist.
            list.add(cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE)))
        }
        cursor.close()
        db.close()

        return list
    }

    fun addDate(date: String) {
        val values = ContentValues()
        values.put(COLUMN_COMPLETED_DATE, date)

        val db = writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
}