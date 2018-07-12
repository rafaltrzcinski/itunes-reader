package com.rafaltrzcinski.itunesreader.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.rafaltrzcinski.itunesreader.controller.ResourceController
import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.domain.model.Track.LocalTrack
import com.rafaltrzcinski.itunesreader.view.StringToReleaseYearTypeAdapter
import io.reactivex.Observable
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileReader


class LocalRepository(private val resourceController: ResourceController) : DataRepository {

    private var itemsList: List<LocalTrack>
    private val result: MutableLiveData<List<LocalTrack>> = MutableLiveData()

    init {
        itemsList = prepareLocalItems()
    }

    private fun prepareLocalItems(): List<LocalTrack> {
        val source = resourceController.getAssets().open("songs-list.json")

        val file = File.createTempFile("temp-", "-songs")
        FileUtils.copyInputStreamToFile(source, file)

        val json = JsonParser().parse(FileReader(file))
        val gson = GsonBuilder()
                .registerTypeAdapter(Int::class.java, StringToReleaseYearTypeAdapter())
                .create()

        return gson.fromJson<Array<LocalTrack>>(json, Array<LocalTrack>::class.java).toList()
    }


    @Suppress("UNCHECKED_CAST")
    override fun getTrackList(query: String): LiveData<List<Track>> {
        if (query.isNotBlank()) {
            Observable.fromIterable(itemsList)
                    .filter { it.artistName.contains(query) }
                    .toList()
                    .subscribe { items -> result.value = items }
        } else {
            result.value = itemsList
        }

        return result as MutableLiveData<List<Track>>
    }
}