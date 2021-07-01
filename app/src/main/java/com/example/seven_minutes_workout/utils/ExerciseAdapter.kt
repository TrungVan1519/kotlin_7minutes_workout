package com.example.seven_minutes_workout.utils

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.seven_minutes_workout.R
import com.example.seven_minutes_workout.models.Exercise
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseAdapter(private val context: Context, private val items: ArrayList<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exercise_status, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtItem.text = items[position].id.toString()
        when {
            items[position].isSelected -> {
                holder.txtItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_thin_color_accent_border)
                holder.txtItem.setTextColor(Color.parseColor("#212121"))
            }
            items[position].isCompleted -> {
                holder.txtItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_accent_background)
                holder.txtItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {
                holder.txtItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_gray_background)
                holder.txtItem.setTextColor(Color.parseColor("#212121"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtItem = view.txtItem!!
    }
}