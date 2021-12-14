package com.example.psiprojectapp

import androidx.room.*

@Dao
interface ScheduleDao {

    @Insert
    fun insert(schedule: Schedule)

    @Update
    fun update(schedule: Schedule)

    @Delete
    fun delete(schedule: Schedule)

    @Query("SELECT * FROM schedules")
    fun getAll() : List<Schedule>

    @Query("SELECT * FROM schedules WHERE id = :id")
    fun getById(id: Int) : List<Schedule>

    @Query("SELECT * FROM schedules WHERE day = :day")
    fun getByDay(day: String) : List<Schedule>

    @Query("SELECT * FROM schedules WHERE `desc` LIKE :words AND day = :day")
    fun getByWords(day: String, words: String) : List<Schedule>

}