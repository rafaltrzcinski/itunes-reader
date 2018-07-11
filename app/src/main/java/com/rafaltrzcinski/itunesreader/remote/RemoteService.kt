package com.rafaltrzcinski.itunesreader.remote

import com.rafaltrzcinski.itunesreader.domain.model.TrackListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface RemoteService {

    @GET("search/")
    fun searchTracks(@Query("term") term: String): Single<TrackListResponse>
}