package com.rafaltrzcinski.itunesreader.usecase

import com.rafaltrzcinski.itunesreader.data.LocalRepository
import com.rafaltrzcinski.itunesreader.data.RemoteRepository
import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.domain.state.DataSource
import com.rafaltrzcinski.itunesreader.domain.state.DataSource.LOCAL
import com.rafaltrzcinski.itunesreader.domain.state.DataSource.REMOTE
import io.reactivex.Single
import javax.inject.Inject


class GetTrackList @Inject constructor(
        private val localRepository: LocalRepository,
        private val remoteRepository: RemoteRepository) {

    fun execute(query: String, dataSource: DataSource): Single<List<Track>> =
            when (dataSource) {
                LOCAL -> localRepository.getTrackList(query)
                REMOTE -> remoteRepository.getTrackList(query)
            }
}