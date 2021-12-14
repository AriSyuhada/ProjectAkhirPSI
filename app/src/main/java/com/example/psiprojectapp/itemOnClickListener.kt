package com.example.psiprojectapp

interface itemOnClickListener {
    fun onDeleteClickListener (id : Int)

    fun onEditClickListener (id: Int)

    fun onThemeApply(holder: ScheduleAdapter.ScheduleViewHolder)

    fun createNotificationChannel (data : Schedule)

    fun scheduleNotification (data : Schedule)

    fun deleteScheduleNotification (id : Int)
}