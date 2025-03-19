package com.stetsiuk.pmp_lab8

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var dbManager: DatabaseManager
    private lateinit var allBooks: Array<String>
    private lateinit var currentBooks: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        dbManager = DatabaseManager(this)
        dbManager.open()
        allBooks = resources.getStringArray(R.array.books)
        currentBooks = BookAdapter(dbManager.queryAll())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = currentBooks
    }

    fun addBook(view: View) {
        val book = allBooks.random()
        val id = dbManager.insert(book)
        currentBooks.addBook(id, book)
    }

    fun removeBook(view: View) {
        if (currentBooks.books.isEmpty()) return
        val index = currentBooks.books.indices.random()
        dbManager.remove(currentBooks.books[index].id)
        currentBooks.removeBook(index)
    }

    fun dropTable(view: View) {
        dbManager.dropTable()
        currentBooks.removeAllBooks()
    }

    override fun onDestroy() {
        dbManager.close()
        super.onDestroy()
    }
}