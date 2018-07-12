package com.rafaltrzcinski.itunesreader.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.remote.RemoteClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class RemoteRepository : DataRepository {

    private val remote = RemoteClient.getRemoteService()

    override fun getTrackList(query: String): LiveData<List<Track>> {

        val liveData = MutableLiveData<List<Track>>()

        remote.searchTracks(if (query.isBlank()) "a" else query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5, TimeUnit.SECONDS)
                .subscribe(
                        { response -> liveData.value = response.results },
                        { liveData.value = null }
                )
        return liveData
    }
}