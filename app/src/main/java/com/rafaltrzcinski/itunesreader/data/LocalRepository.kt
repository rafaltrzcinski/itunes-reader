package com.rafaltrzcinski.itunesreader.data

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.rafaltrzcinski.itunesreader.controller.ResourceController
import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.domain.model.Track.LocalTrack
import com.rafaltrzcinski.itunesreader.view.StringToReleaseYearTypeAdapter
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileReader


class LocalRepository(private val resourceController: ResourceController) : DataRepository {

    private val result: MutableLiveData<List<LocalTrack>> = MutableLiveData()

    @Suppress("UNCHECKED_CAST")
    override fun getTrackList(query: String): LiveData<List<Track>> {
        val source = resourceController.getAssets().open("songs-list.json")

        val file = File.createTempFile("temp-", "-songs")
        FileUtils.copyInputStreamToFile(source, file)

        val json = JsonParser().parse(FileReader(file))
        val gson = GsonBuilder()
                .registerTypeAdapter(Int::class.java, StringToReleaseYearTypeAdapter())
                .create()

        val itemsArray = gson.fromJson<Array<LocalTrack>>(json, Array<LocalTrack>::class.java)
        result.value = itemsArray.toList()
        return result as MutableLiveData<List<Track>>
    }
}