package com.rafaltrzcinski.itunesreader.data

import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.remote.RemoteClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RemoteRepository @Inject constructor() : DataRepository {

    private val remote = RemoteClient.getRemoteService()

    override fun getTrackList(query: String): Single<List<Track>> =
        remote.searchTracks(if (query.isBlank()) "a" else query)
                .map { it.results.map { it as Track } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5, TimeUnit.SECONDS)
}