package com.rafaltrzcinski.itunesreader.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rafaltrzcinski.itunesreader.domain.state.DataSource
import com.rafaltrzcinski.itunesreader.domain.state.DataSource.LOCAL
import com.rafaltrzcinski.itunesreader.domain.state.TrackListState
import com.rafaltrzcinski.itunesreader.usecase.GetTrackList
import javax.inject.Inject


class TrackListViewModel @Inject constructor(private val getTrackList: GetTrackList) : ViewModel() {

    val stateLiveData = MutableLiveData<TrackListState>()

    init {
        reloadTracks()
    }

    fun reloadTracks(query: String = getCurrentStateQuery(), dataSources: List<DataSource> = getCurrentStateDataSources()) {
        dataSources.forEach {
            getTrackList.execute(query, it)
                    .subscribe(
                            { stateLiveData.value = TrackListState.Loaded(query, dataSources, it) },
                            { stateLiveData.value = TrackListState.Error(it) }
                    )
        }
    }

    private fun getCurrentStateQuery(): String = stateLiveData.value?.query ?: ""

    private fun getCurrentStateDataSources(): List<DataSource> =
            stateLiveData.value?.dataSources ?: listOf(LOCAL)

}