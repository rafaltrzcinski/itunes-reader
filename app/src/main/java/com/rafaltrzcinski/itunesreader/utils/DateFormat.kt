package com.rafaltrzcinski.itunesreader.utils

import org.joda.time.format.DateTimeFormat


object DateFormat {

    fun getYearFromDateString(date: String): Int {
        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val dateTime = formatter.parseDateTime(date)
        return dateTime.year
    }
}