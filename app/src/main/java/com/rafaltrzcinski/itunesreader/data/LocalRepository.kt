package com.rafaltrzcinski.itunesreader.data

import android.content.res.AssetManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.domain.model.Track.LocalTrack
import com.rafaltrzcinski.itunesreader.view.StringToReleaseYearTypeAdapter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileReader
import javax.inject.Inject


class LocalRepository @Inject constructor(private val assetsManager: AssetManager) : DataRepository {

    private val itemsList: List<LocalTrack>

    init {
        itemsList = prepareLocalItems()
    }

    fun prepareLocalItems(): List<LocalTrack> {
        val source = assetsManager.open("songs-list.json")

        val file = File.createTempFile("temp-", "-songs")
        FileUtils.copyInputStreamToFile(source, file)

        val json = JsonParser().parse(FileReader(file))
        val gson = GsonBuilder()
                .registerTypeAdapter(Int::class.java, StringToReleaseYearTypeAdapter())
                .create()

        return gson.fromJson<Array<LocalTrack>>(json, Array<LocalTrack>::class.java).toList()
    }


    override fun getTrackList(query: String): Single<List<Track>> =
            Observable.fromIterable(itemsList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter {
                        if (query.isNotBlank()) it.artistName.contains(query)
                        else true
                    }
                    .ofType(Track::class.java)
                    .toList()
}