package com.stetsiuk.pmp_lab6

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listView = findViewById<ListView>(R.id.listView);
        val listElementView = findViewById<TextView>(R.id.listElementView);
        val gridView = findViewById<GridView>(R.id.gridView)
        val initialEdit = findViewById<EditText>(R.id.initial)
        val conditionEdit = findViewById<EditText>(R.id.condition)
        val incrementEdit = findViewById<EditText>(R.id.increment)

        listView.setOnItemClickListener { parent, view, position, id ->
            val itemText = (view as TextView).text.toString()
            listElementView.text = itemText
            Toast.makeText(this, itemText, Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.calculateBtn).setOnClickListener { view ->
            val initial = initialEdit.text.toString().toDoubleOrNull()
            val condition = conditionEdit.text.toString().toDoubleOrNull()
            val increment = incrementEdit.text.toString().toDoubleOrNull()

            if (initial == null || condition == null || increment == null) return@setOnClickListener
            if (initial < condition && increment <= 0) return@setOnClickListener
            if (initial > condition && increment >= 0) return@setOnClickListener

            val results = mutableListOf<FuncResult>()
            var x = initial
            while (x <= condition) {
                val y = 3 * x - 4
                results.add(FuncResult(x, y))
                x += increment
            }

            gridView.adapter = GridAdapter(this, results)
        }
    }
}