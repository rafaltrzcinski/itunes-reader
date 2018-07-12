package com.rafaltrzcinski.itunesreader.utils

import com.rafaltrzcinski.itunesreader.domain.model.Track


fun MutableList<Track>.sortByArtistName() {
    this.sortBy {
        when (it) {
            is Track.LocalTrack -> it.artistName
            is Track.RemoteTrack -> it.artistName
        }
    }
}