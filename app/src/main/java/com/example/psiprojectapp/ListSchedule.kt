package com.example.psiprojectapp

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.view.textservice.SpellCheckerInfo

class ListSchedule(context: Context) {
    private val listSchedule = mutableListOf<Schedule>()
    private val database = ScheduleRoomDatabase.getDatabase(context)
    private val dao = database.getScheduleDao()

    fun getSchedule() : MutableList<Schedule> {
        listSchedule.clear()
        listSchedule.addAll(dao.getAll())
        return listSchedule
    }

    fun getSchedulebyDay(day: String) : List<Schedule> {
        val schedule = dao.getByDay(day)
        return schedule
    }

    fun getSchedulebyID(id: Int) : List<Schedule> {
        val schedule = dao.getById(id)
        return schedule
    }

    fun getSchedulebyWords(day: String, words : String) : List<Schedule> {
        val keywords = "%" + words + "%"
        val schedule = dao.getByWords(day, keywords)
        return schedule
    }

    fun addSchedule(schedule: Schedule) {
        dao.insert(schedule)
    }

    fun updateSchedule(schedule: Schedule) {
        dao.update(schedule)
    }

    fun deleteSchedule(schedule: Schedule) {
        dao.delete(schedule)
    }
}