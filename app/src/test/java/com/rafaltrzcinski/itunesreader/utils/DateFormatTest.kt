package com.rafaltrzcinski.itunesreader.utils

import junit.framework.Assert.assertEquals
import org.junit.Test


class DateFormatTest {

    @Test
    fun `should return number of year from correct date string`() {
        val date = "1997-01-01T08:00:00Z"

        val result = date.getYearFromDateString()

        assertEquals(1997, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw IllegalArgumentException from incorrect date string`() {
        val date = "abcd"

        date.getYearFromDateString()
    }
}