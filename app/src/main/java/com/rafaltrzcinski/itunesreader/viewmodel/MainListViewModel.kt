package com.rafaltrzcinski.itunesreader.viewmodel

import android.arch.lifecycle.*
import com.rafaltrzcinski.itunesreader.data.LocalRepository
import com.rafaltrzcinski.itunesreader.data.RemoteRepository
import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.domain.state.DataSource
import com.rafaltrzcinski.itunesreader.domain.state.DataSource.LOCAL
import javax.inject.Inject


class MainListViewModel @Inject constructor(
        localRepository: LocalRepository,
        remoteRepository: RemoteRepository) : ViewModel() {

    private val dataSourceType = MutableLiveData<DataSource>()
    private val trackList = MediatorLiveData<List<Track>>()
    private var queryList: String = ""

    init {
        val liveTrackList = Transformations.switchMap(dataSourceType) {
            val repository = when (it) {
                LOCAL -> localRepository
                else -> remoteRepository
            }
            repository.getTrackList(queryList)
        }
        trackList.addSource(liveTrackList, trackList::setValue)
    }

    fun setDataSource(source: DataSource, query: String = "") {
        queryList = query
        dataSourceType.value = source
    }

    fun getTrackList() = trackList
}