package com.stetsiuk.pmp_lab4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

        findViewById<Button>(R.id.relative_btn).setOnClickListener {
            startActivity(Intent(this, RelativeActivity::class.java))
        }
        findViewById<Button>(R.id.constraint_btn).setOnClickListener {
            startActivity(Intent(this, ConstraintActivity::class.java))
        }
        findViewById<Button>(R.id.set_btn).setOnClickListener {
            startActivity(Intent(this, SetActivity::class.java))
        }
    }
}