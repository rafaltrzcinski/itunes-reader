package com.rafaltrzcinski.itunesreader.domain.model

import com.rafaltrzcinski.itunesreader.domain.model.Track.RemoteTrack


data class TrackListResponse(
        val resultCount: Int = 0,
        val results: List<RemoteTrack> = emptyList()
)