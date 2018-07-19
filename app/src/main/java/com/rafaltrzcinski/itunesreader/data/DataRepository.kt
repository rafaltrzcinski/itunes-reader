package com.rafaltrzcinski.itunesreader.data

import com.rafaltrzcinski.itunesreader.domain.model.Track
import io.reactivex.Single


interface DataRepository {
    fun getTrackList(query: String): Single<List<Track>>
}