package com.anonymous.notes.data

import androidx.room.TypeConverter
import java.util.*

// example converter for java.util.Date
//
object Converters {
    @TypeConverter
    fun fromTimestampToDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}