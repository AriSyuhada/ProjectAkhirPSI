package com.example.psiprojectapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Schedule::class], version = 1, exportSchema = false)
abstract class ScheduleRoomDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: ScheduleRoomDatabase? =  null

        fun getDatabase(context: Context): ScheduleRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScheduleRoomDatabase::class.java,
                    "schedule_db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getScheduleDao() : ScheduleDao
}