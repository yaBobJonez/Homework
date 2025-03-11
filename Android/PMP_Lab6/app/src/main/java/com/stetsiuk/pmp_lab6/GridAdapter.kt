package com.stetsiuk.pmp_lab6

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.Locale

class GridAdapter(private val context: Context, private val dataItems: List<FuncResult>) : BaseAdapter() {
    override fun getCount(): Int {
        return dataItems.size
    }
    override fun getItem(position: Int): Any {
        return dataItems[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        }
        val text1: TextView = convertView!!.findViewById(android.R.id.text1)
        val text2: TextView = convertView.findViewById(android.R.id.text2)

        val item = dataItems[position]
        text1.text = String.format(Locale.getDefault(), "y = %.2f", item.y)
        text2.text = String.format(Locale.getDefault(), "x = %.2f", item.x)

        return convertView
    }
}
