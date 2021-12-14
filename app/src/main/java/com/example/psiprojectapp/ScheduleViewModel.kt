package com.example.psiprojectapp

import android.app.PendingIntent
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ScheduleViewModel(val listSchedule: ListSchedule, val itemOnClickListener: itemOnClickListener) : ViewModel() {
    private val liveDataSchedule = MutableLiveData<List<Schedule>>()
    private lateinit var day : String

    fun getData() : LiveData<List<Schedule>> {
        liveDataSchedule.value = listSchedule.getSchedule()
        return liveDataSchedule
    }

    fun getDatabyDay(day : String) : LiveData<List<Schedule>> {
        this.day = day
        liveDataSchedule.value = listSchedule.getSchedulebyDay(day)
        return liveDataSchedule
    }

    fun getDataById(id : Int) : List<Schedule> {
        return listSchedule.getSchedulebyID(id)
    }

    fun getDataByWords(day: String, words: String) : LiveData<List<Schedule>> {
        liveDataSchedule.value = listSchedule.getSchedulebyWords(this.day, words)
        return liveDataSchedule
    }

    fun addData(newData : Schedule) {
        listSchedule.addSchedule(newData)
        itemOnClickListener.createNotificationChannel(newData)
        itemOnClickListener.scheduleNotification(newData)
        liveDataSchedule.value = listSchedule.getSchedulebyDay(day)
    }

    fun updateData(data : Schedule) {
        listSchedule.updateSchedule(data)
        itemOnClickListener.createNotificationChannel(data)
        itemOnClickListener.scheduleNotification(data)
        liveDataSchedule.value = listSchedule.getSchedulebyDay(day)
    }

    fun deleteData(data : Schedule) {
        listSchedule.deleteSchedule(data)
        liveDataSchedule.value = listSchedule.getSchedulebyDay(day)
    }
}