package com.rafaltrzcinski.itunesreader.data

import android.arch.lifecycle.LiveData
import com.rafaltrzcinski.itunesreader.domain.model.Track


interface DataRepository {
    fun getTrackList(query: String): LiveData<List<Track>>
}