package com.rafaltrzcinski.itunesreader.utils

import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.domain.model.Track.LocalTrack
import org.junit.Assert.assertEquals
import org.junit.Test


class ListSortingTest {

    @Test
    fun `should return sorted list by artist name`() {
        val list = mutableListOf<Track>(
                LocalTrack("Artist D"),
                LocalTrack("Artist C"),
                LocalTrack("Artist A")
        )

        list.sortByArtistName()

        assertEquals((list.first() as LocalTrack).artistName, "Artist A")
    }
}