package com.rafaltrzcinski.itunesreader.utils

import org.joda.time.format.DateTimeFormat


fun String.getYearFromDateString(): Int {
    val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val dateTime = formatter.parseDateTime(this)
    return dateTime.year
}