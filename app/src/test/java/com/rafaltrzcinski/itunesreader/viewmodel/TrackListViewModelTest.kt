package com.rafaltrzcinski.itunesreader.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.domain.model.Track.LocalTrack
import com.rafaltrzcinski.itunesreader.domain.state.DataSource
import com.rafaltrzcinski.itunesreader.usecase.GetTrackList
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.TimeUnit


class TrackListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val localTrackList = listOf(
            LocalTrack("Artist 1", "Song 1", 1991),
            LocalTrack("Artist 2", "Song 2", 1992)
    )


    private val trackListUseCaseMock = mock<GetTrackList> {
        on { execute(any(), any()) } doReturn
                Single.just<List<Track>>(localTrackList)
                        .delay(200, TimeUnit.MILLISECONDS)
    }

    private val viewModel = TrackListViewModel(trackListUseCaseMock)


    @Test
    fun `should invoke loading items from local repository when view model starts`() {
        trackListUseCaseMock.execute("", DataSource.LOCAL).test()
                .awaitCount(1)
                .assertValue(localTrackList)
    }
}