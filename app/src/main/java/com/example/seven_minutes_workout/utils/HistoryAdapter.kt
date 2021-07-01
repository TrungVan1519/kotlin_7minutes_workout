package com.example.seven_minutes_workout.utils

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seven_minutes_workout.R
import kotlinx.android.synthetic.main.item_history_row.view.*

class HistoryAdapter(val context: Context, private val items: ArrayList<String>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_history_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = if (position % 2 == 0) "#EBEBEB" else "#FFFFFF"
        holder.llHistoryItemMain.setBackgroundColor(Color.parseColor(color))
        holder.txtPosition.text = (position + 1).toString()
        holder.txtItem.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val llHistoryItemMain = view.ll_history_item_main!!
        val txtItem = view.txtItem!!
        val txtPosition = view.txtPosition!!
    }
}