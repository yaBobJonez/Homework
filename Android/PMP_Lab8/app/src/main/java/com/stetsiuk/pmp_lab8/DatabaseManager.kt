package com.stetsiuk.pmp_lab8

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class DatabaseManager(context: Context) {
    private val dbHelper = DatabaseHelper(context)
    private var db: SQLiteDatabase? = null

    fun open() { db = dbHelper.writableDatabase }
    fun close() { dbHelper.close() }

    fun insert(row: String): Long {
        if (db == null) return -1
        val fields = row.split(";")
        if (fields.size != 4) return -1
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_TITLE, fields[0])
            put(DatabaseHelper.COLUMN_ARC, fields[1])
            put(DatabaseHelper.COLUMN_YEAR, fields[2].toIntOrNull() ?: return -1)
            put(DatabaseHelper.COLUMN_AUTHOR, fields[3])
        }
        return db!!.insert(DatabaseHelper.TABLE_NAME, null, values)
    }

    fun remove(id: Long): Int {
        if (db == null) return 0
        return db!!.delete(
            DatabaseHelper.TABLE_NAME,
            "${BaseColumns._ID} = ?",
            arrayOf(id.toString())
        )
    }

    fun queryAll(): MutableList<Book> {
        val books = mutableListOf<Book>()
        if (db == null) return books
        val cursor = db!!.query(
            DatabaseHelper.TABLE_NAME,
            null, null, null, null, null,
            "${DatabaseHelper.COLUMN_YEAR} ASC"
        )
        with(cursor) {
            while (moveToNext()) {
                books.add( Book(
                    id = getLong(getColumnIndexOrThrow(BaseColumns._ID)),
                    title = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE)),
                    arc = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_ARC)),
                    year = getInt(getColumnIndexOrThrow(DatabaseHelper.COLUMN_YEAR)),
                    author = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR))
                ) )
            }
        }
        cursor.close()
        return books
    }

    fun dropTable(): Boolean {
        if (db == null) return false
        db!!.delete(DatabaseHelper.TABLE_NAME, null, null)
        return true
    }
}
