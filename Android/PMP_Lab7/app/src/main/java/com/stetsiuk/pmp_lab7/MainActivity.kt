package com.stetsiuk.pmp_lab7

import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var menuItems = mutableListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textView = findViewById<TextView>(R.id.textView)
        val listView = findViewById<ListView>(R.id.listView)

        registerForContextMenu(textView)
        listView.setMultiChoiceModeListener(ListSelectionActionModeCallback(listView))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        if (menuItems.isEmpty())
            for (i in 0 ..< menu.size())
                menuItems.add(menu.getItem(i))
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        for (item in menuItems) {
            menu?.add(item.groupId, item.itemId, item.order, item.title)
                ?.setIcon(item.icon)
                ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.reorderMenu) {
            showPopupMenu()
            return true
        }
        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }

    private fun showPopupMenu() {
        val actionBarView = findViewById<View>(androidx.appcompat.R.id.action_bar)
        val popup = PopupMenu(this, actionBarView)
        popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sort_asc -> menuItems.sortBy { it.title.toString() }
                R.id.sort_desc -> menuItems.sortByDescending { it.title.toString() }
            }
            invalidateOptionsMenu()
            true
        }
        popup.show()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        findViewById<TextView>(R.id.textView).setTextColor( when (item.itemId) {
            R.id.red -> Color.RED
            R.id.blue -> Color.BLUE
            R.id.green -> Color.GREEN
            R.id.yellow -> Color.YELLOW
            else -> Color.BLACK
        })
        return super.onContextItemSelected(item)
    }
}