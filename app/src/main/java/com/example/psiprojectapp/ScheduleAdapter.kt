package com.example.psiprojectapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_schedule.view.*

class ScheduleAdapter(var schedule: LiveData<List<Schedule>>, val itemOnClickListener: itemOnClickListener) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {
    inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemContainer : LinearLayoutCompat = itemView.findViewById(R.id.item_container)
        val tvDesc : TextView = itemView.findViewById(R.id.tv_schedule)
        val tvTime : TextView = itemView.findViewById(R.id.tv_time)
        val buttonEdit : ImageButton = itemView.findViewById(R.id.button_edit)
        val buttonDelete : ImageButton = itemView.findViewById(R.id.button_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.itemView.apply {

            itemOnClickListener.onThemeApply(holder)

            holder.tvDesc.text = schedule.value?.get(position)?.desc?: ""
            val startTime = schedule.value?.get(position)?.timeStart?: ""
            val endTime = schedule.value?.get(position)?.timeEnd?: ""
            holder.tvTime.text = "$startTime - $endTime"
            button_delete.setOnClickListener() {
                itemOnClickListener.onDeleteClickListener(schedule.value!!.get(position)!!.id)
            }
            button_edit.setOnClickListener() {
                itemOnClickListener.onEditClickListener(schedule.value!!.get(position)!!.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return schedule.value?.size?: 0
    }
}