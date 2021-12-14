package com.example.psiprojectapp

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//Entity annotation to specify the table's name
@Entity(tableName = "schedules")
//Parcelable annotation to make parcelable object
@Parcelize
data class Schedule(
    //PrimaryKey annotation to declare primary key
    //ColumnInfo annotation to specify the column's name
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "day") var day : String = "",
    @ColumnInfo(name = "desc") var desc : String = "",
    @ColumnInfo(name = "time_start") var timeStart : String = "",
    @ColumnInfo(name = "time_end") var timeEnd : String = ""
) : Parcelable {
}