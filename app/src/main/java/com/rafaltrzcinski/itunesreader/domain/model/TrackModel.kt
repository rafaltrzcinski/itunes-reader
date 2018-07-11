package com.rafaltrzcinski.itunesreader.domain.model

import com.google.gson.annotations.SerializedName


sealed class Track {

    data class LocalTrack(
            @SerializedName("ARTIST CLEAN") val artistName: String = "",
            @SerializedName("Song Clean") val songTitle: String = "",
            @SerializedName("Release Year") val releaseYear: Int = 0): Track()

    data class RemoteTrack(
            val artistName: String = "",
            val trackName: String = "",
            val releaseDate: String = ""): Track()
}



