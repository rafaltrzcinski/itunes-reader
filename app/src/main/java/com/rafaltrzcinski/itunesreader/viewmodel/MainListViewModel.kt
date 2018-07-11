package com.rafaltrzcinski.itunesreader.viewmodel

import android.arch.lifecycle.*
import com.rafaltrzcinski.itunesreader.controller.ResourceController
import com.rafaltrzcinski.itunesreader.data.DataRepository
import com.rafaltrzcinski.itunesreader.data.LocalRepository
import com.rafaltrzcinski.itunesreader.data.RemoteRepository
import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.domain.state.DataSource
import com.rafaltrzcinski.itunesreader.domain.state.DataSource.LOCAL


class MainListViewModel(resourceController: ResourceController) : ViewModel() {

    private lateinit var repository: DataRepository

    private val dataSourceType = MutableLiveData<DataSource>()
    private val trackList = MediatorLiveData<List<Track>>()

    init {
        val liveTrackList = Transformations.switchMap(dataSourceType) {
            repository = when (it) {
                LOCAL -> LocalRepository(resourceController)
                else -> RemoteRepository()
            }
            repository.getTrackList("a")
        }
        trackList.addSource(liveTrackList, trackList::setValue)
    }

    fun setDataSource(source: DataSource) {
        dataSourceType.value = source
    }

    fun getTrackList() = trackList
}