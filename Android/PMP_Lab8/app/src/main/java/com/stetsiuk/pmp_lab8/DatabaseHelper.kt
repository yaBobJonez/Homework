package com.stetsiuk.pmp_lab8

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "books.db"
        private const val DB_VERSION = 1
        const val TABLE_NAME = "Book"
        const val COLUMN_TITLE = "title"
        const val COLUMN_ARC = "arc"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_YEAR = "year"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_NAME (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_ARC TEXT,
                $COLUMN_YEAR INTEGER,
                $COLUMN_AUTHOR TEXT
            );
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
