package com.rafaltrzcinski.itunesreader.domain.state

import com.rafaltrzcinski.itunesreader.domain.model.Track


sealed class TrackListState(open val query: String = "",
                            open val dataSources: List<DataSource> = listOf(DataSource.LOCAL),
                            open val trackList: List<Track>? = emptyList()) {

    data class Loaded(override val query: String,
                      override val dataSources: List<DataSource>,
                      override val trackList: List<Track>): TrackListState(query, dataSources, trackList)
    class Error(val error: Throwable?): TrackListState()
}

enum class DataSource {
    REMOTE, LOCAL
}