package com.stetsiuk.pmp_lab4

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Barrier
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_set)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val layout = findViewById<ConstraintLayout>(R.id.main)

        val set = ConstraintSet()
        set.clone(layout)
        // Привʼязки
        set.connect(R.id.sq2, ConstraintSet.LEFT, R.id.sq1, ConstraintSet.RIGHT, 8)
        set.connect(R.id.sq2, ConstraintSet.TOP, R.id.sq1, ConstraintSet.TOP)
        // Ланцюжок
        set.createHorizontalChain(
            ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
            intArrayOf(R.id.sq3, R.id.sq4, R.id.sq5),
            floatArrayOf(1f, 2f, 3f),
            ConstraintSet.CHAIN_SPREAD
        )
        // Барʼєр
        val barrier = Barrier(this)
        barrier.id = R.id.barrier
        barrier.referencedIds = intArrayOf(R.id.sq6, R.id.sq7)
        barrier.type = Barrier.END
        layout.addView(barrier)
        set.connect(R.id.sq8, ConstraintSet.START, barrier.id, ConstraintSet.END, 8)
        // Колове розміщення
        set.constrainCircle(R.id.sq10, R.id.sq9, 200, 15f)
        set.constrainCircle(R.id.sq11, R.id.sq9, 200, 70f)
        set.constrainCircle(R.id.sq12, R.id.sq9, 200, 148f)
        set.applyTo(layout)
    }
}