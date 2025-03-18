package com.stetsiuk.pmp_lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class BookAdapter(val books: MutableList<Book>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val arc: TextView = view.findViewById(R.id.arc)
        val year: TextView = view.findViewById(R.id.year)
        val author: TextView = view.findViewById(R.id.author)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.book_item, viewGroup, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.title.text = book.title
        holder.arc.text = book.arc
        holder.year.text = String.format(Locale.getDefault(), "%d", book.year)
        holder.author.text = book.author
    }

    override fun getItemCount(): Int = books.size

    fun addBook(id: Long, book: String) {
        val fields = book.split(";")
        if (fields.size != 4) return
        books.add(Book(id, fields[0], fields[1], fields[2].toInt(), fields[3]))
        notifyItemInserted(books.size - 1)
    }

    fun removeBook(index: Int) {
        books.removeAt(index)
        notifyItemRemoved(index)
    }

    fun removeAllBooks() {
        val size = books.size
        books.clear()
        notifyItemRangeRemoved(0, size)
    }
}
