package com.stetsiuk.pmp_lab7

import android.graphics.Color
import android.graphics.Typeface
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView
import android.widget.ListView
import android.widget.TextView

class ListSelectionActionModeCallback(private val listView: ListView) : AbsListView.MultiChoiceModeListener {

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.contextual_action_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onItemCheckedStateChanged(mode: ActionMode?, position: Int, id: Long, checked: Boolean) {
        val tv = listView.getChildAt(position) as TextView
        tv.setBackgroundColor(if (checked) Color.LTGRAY else Color.TRANSPARENT)
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        updateItemTextStyle(when (item?.itemId) {
            R.id.action_bold -> Typeface.BOLD
            R.id.action_italic -> Typeface.ITALIC
            R.id.action_bolditalic -> Typeface.BOLD_ITALIC
            else -> Typeface.NORMAL
        })
        mode?.finish()
        return true
    }

    private fun updateItemTextStyle(style: Int) {
        for (i in 0 ..< listView.childCount) {
            if (listView.isItemChecked(i)) {
                val tv = listView.getChildAt(i) as TextView
                tv.setTypeface(null, style)
            }
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        for (i in 0 ..< listView.childCount) {
            val tv = listView.getChildAt(i) as TextView
            tv.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}
