package com.rafaltrzcinski.itunesreader.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.rafaltrzcinski.itunesreader.data.LocalRepository
import com.rafaltrzcinski.itunesreader.data.RemoteRepository
import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.domain.model.Track.LocalTrack
import com.rafaltrzcinski.itunesreader.domain.state.DataSource
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class MainListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val observerMock = mock<Observer<List<Track>>>()

    private val liveDataMock = mock<LiveData<List<Track>>> {
        on { value } doReturn listOf(LocalTrack("Artist1", "Song1"))
    }

    private val localRepositoryMock = mock<LocalRepository> {
        on { prepareLocalItems() } doReturn listOf(LocalTrack("Artist1", "Song1"))
        on { getTrackList(any()) } doReturn liveDataMock
    }
    private val remoteRepositoryMock = mock<RemoteRepository>()

    private val viewModel = MainListViewModel(localRepositoryMock, remoteRepositoryMock)

    @Test
    fun `should invoke loading items from local repository when set local data source on view model`() {
        val query = ""
        viewModel.getTrackList().observeForever(observerMock)

        viewModel.setDataSource(DataSource.LOCAL, query)

        verify(localRepositoryMock).getTrackList(query)
    }
}